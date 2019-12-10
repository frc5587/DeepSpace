/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;
import org.frc5587.lib.pathfinder.AbstractDrive;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The subsystem for the drivetrain.
 * 
 * <p>
 * The drivetrain consists of six wheels, driven as a right and left side by
 * CIMs. Each side is controlled by a TalonSRX and VictorSPX motor controller
 * pair.
 * 
 * <p>
 * Drive implements PIDOutput purely for providing auto-centring functionality
 * with the Limelight. The Limelight finds the angle between the centre of the
 * pieces of tape and the centre of the robot, then tells Drive to turn to an
 * angle with PID based on that data (although Drive does not calculate that
 * angle itself).
 * 
 * @see org.frc5587.deepspace.commands.control.Limelight
 * @see org.frc5587.deepspace.commands.Manager
 */
public class Drive extends AbstractDrive implements PIDOutput {
	// Controller used to set drivetrain angle based on gyro
	private PIDController turnController;
	// Used to stop the turning PID loop from controlling the drivetrain at startup
	private boolean turnEnabledFirstTime;

	public Drive() {
		super(new TalonSRX(RobotMap.Drive.LEFT_MASTER), new TalonSRX(RobotMap.Drive.RIGHT_MASTER),
				new VictorSPX(RobotMap.Drive.LEFT_SLAVE), new VictorSPX(RobotMap.Drive.RIGHT_SLAVE), true);

		setAHRS(new AHRS(Port.kMXP));
		setConstants(Constants.Drive.MAX_VELOCITY, Constants.TIMEOUT_MS, Constants.Drive.STU_PER_REV,
				Constants.Drive.STU_PER_INCH, Constants.Drive.WHEEL_DIAMETER, Constants.Drive.MIN_BUFFER_COUNT);

		var fpid = Constants.Drive.TURN_FPID;
		// Create new controller with the AHRS/navX's gyro as input
		turnController = new PIDController(fpid.kP, fpid.kI, fpid.kD, fpid.kF, ahrs, this, 0.010);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(Constants.Drive.TOLERANCE_DEGREES);
		turnController.setContinuous(true);
		turnController.disable();

		// Add PID Controller to dashboard for testing
		// turnController.setName("DriveSystem", "RotateController");
		// SmartDashboard.putData(turnController);
		turnEnabledFirstTime = false;
	}

	@Override
	public void configPID(int slot) {
		leftMaster.config_kF(slot, Constants.Drive.LEFT_PIDS.kF, 0);
		leftMaster.config_kP(slot, Constants.Drive.LEFT_PIDS.kP, 0);
		leftMaster.config_IntegralZone(slot, 50);
		leftMaster.config_kI(slot, Constants.Drive.LEFT_PIDS.kI, 0);
		leftMaster.config_kD(slot, Constants.Drive.LEFT_PIDS.kD, 0);

		rightMaster.config_kF(slot, Constants.Drive.RIGHT_PIDS.kF, 0);
		rightMaster.config_kP(slot, Constants.Drive.RIGHT_PIDS.kP, 0);
		rightMaster.config_kI(slot, Constants.Drive.RIGHT_PIDS.kI, 0);
		rightMaster.config_kD(slot, Constants.Drive.RIGHT_PIDS.kD, 0);
	}

	@Override
	public void configSettings() {
		var timeoutMs = Constants.TIMEOUT_MS;
		rightMaster.configVoltageCompSaturation(Constants.V_COMP_SATURATION, timeoutMs);
		rightMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(Constants.V_COMP_SATURATION, timeoutMs);
		leftMaster.enableVoltageCompensation(true);

		rightMaster.configPeakCurrentLimit(35, timeoutMs);
		leftMaster.configPeakCurrentLimit(35, timeoutMs);
		rightMaster.configPeakCurrentDuration(20, timeoutMs);
		leftMaster.configPeakCurrentDuration(20, timeoutMs);
		rightMaster.configContinuousCurrentLimit(35, timeoutMs);
		leftMaster.configContinuousCurrentLimit(35, timeoutMs);
		rightMaster.enableCurrentLimit(true);
		leftMaster.enableCurrentLimit(true);

		leftMaster.configPeakOutputForward(Constants.Drive.MAX_PERCENT_FW, timeoutMs);
		leftMaster.configPeakOutputReverse(-Constants.Drive.MAX_PERCENT_BW, timeoutMs);
		rightMaster.configPeakOutputForward(Constants.Drive.MAX_PERCENT_FW, timeoutMs);
		rightMaster.configPeakOutputReverse(-Constants.Drive.MAX_PERCENT_BW, timeoutMs);
	}

	/**
	 * Prepare for refreshing the PID constants and target position of this
	 * subsystem by posting cells to SmartDashboard.
	 */
	public void startRefresh() {
		SmartDashboard.putNumber("Left P", Constants.Drive.LEFT_PIDS.kP);
		SmartDashboard.putNumber("Left I", Constants.Drive.LEFT_PIDS.kI);
		SmartDashboard.putNumber("Left D", Constants.Drive.LEFT_PIDS.kD);
		SmartDashboard.putNumber("Goto Position L", 0.0);

		SmartDashboard.putNumber("Right P", Constants.Drive.RIGHT_PIDS.kP);
		SmartDashboard.putNumber("Right I", Constants.Drive.RIGHT_PIDS.kI);
		SmartDashboard.putNumber("Right D", Constants.Drive.RIGHT_PIDS.kD);
		SmartDashboard.putNumber("Goto Position R", 0.0);
	}

	/**
	 * Update the PID constants and target position of this subsystem with data from
	 * SmartDashboard
	 *
	 * <p>
	 * Note that the {@link #startRefresh()} method should be called before first
	 * calling this method for the first time.
	 */
	public void refreshPID() {
		leftMaster.config_kP(0, SmartDashboard.getNumber("Left P", 0.0), 20);
		leftMaster.config_kI(0, SmartDashboard.getNumber("Left I", 0.0), 20);
		leftMaster.config_kD(0, SmartDashboard.getNumber("Left D", 0.0), 20);
		leftMaster.set(ControlMode.Position, SmartDashboard.getNumber("Goto Position L", 0.0));

		rightMaster.config_kP(0, SmartDashboard.getNumber("Right P", 0.0), 20);
		rightMaster.config_kI(0, SmartDashboard.getNumber("Right I", 0.0), 20);
		rightMaster.config_kD(0, SmartDashboard.getNumber("Right D", 0.0), 20);
		rightMaster.set(ControlMode.Position, SmartDashboard.getNumber("Goto Position R", 0.0));
	}

	/**
	 * Enable or disable the turn PID controller based on the desired value.
	 * 
	 * @param enabled whether to enable or disable the turn PID controller
	 */
	public void enableTurnPID(boolean enabled) {
		if (enabled) {
			// Allow PID controller to ouput values in pidWrite now that it has been enabled
			turnEnabledFirstTime = true;
		}

		turnController.setEnabled(enabled);
	}

	/**
	 * Set the target angle of the turn PID controller to the provided angle in
	 * degrees.
	 * 
	 * @param targetAngleDeg the target angle in degrees
	 */
	public void setAngleTarget(double targetAngleDeg) {
		turnController.setSetpoint(targetAngleDeg);
	}

	/**
	 * Get whether the turn PID controller is currently controlling the drivetrain.
	 * 
	 * @return whether the turn PID controller is enabled
	 */
	public boolean turnPIDEnabled() {
		return turnController.isEnabled();
	}

	@Override
	public void pidWrite(double output) {
		// Only write ouput after being enabled to prevent jitters when starting robot
		if (turnEnabledFirstTime) {
			// Move forward at a constant speed and only adjust turning
			// Sensisitivity is adjusted by LPF_PERCENT constant
			vbusArcade(Constants.Drive.CONSTANT_FORWARD_PERCENT, Constants.Drive.LPF_PERCENT * output);
		}
	}

	public void initDefaultCommand() {

	}
}
