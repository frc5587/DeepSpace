/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.frc5587.lib.pathfinder.AbstractDrive;
import org.frc5587.lib.pathfinder.Pathgen;
import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Drive extends AbstractDrive implements PIDOutput {
	public static final Pathgen SLOW_PATHGEN = new Pathgen(30, 0.010, 36, 60, 120);
	public static final Pathgen MED_PATHGEN = new Pathgen(30, 0.010, 60, 80, 160);
	public static final Pathgen FAST_PATHGEN = new Pathgen(30, 0.010, 84, 80, 160);

	private PIDController turnController;

	public Drive() {
		super(new TalonSRX(RobotMap.Drive.LEFT_MASTER), new TalonSRX(RobotMap.Drive.RIGHT_MASTER),
				new VictorSPX(RobotMap.Drive.LEFT_SLAVE), new VictorSPX(RobotMap.Drive.RIGHT_SLAVE), true);

		setAHRS(new AHRS(Port.kMXP));
		setConstants(Constants.Drive.kMaxVelocity, Constants.Drive.kTimeoutMs, Constants.Drive.stuPerRev,
				Constants.Drive.stuPerInch, Constants.Drive.wheelDiameter, Constants.Drive.minBufferCount);

		turnController = new PIDController(Constants.Drive.TURN_FPID.kP, Constants.Drive.TURN_FPID.kI,
				Constants.Drive.TURN_FPID.kD, Constants.Drive.TURN_FPID.kF, ahrs, this);
		
		turnController.disable();
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(Constants.Drive.TOLERANCE_DEGREES);
		turnController.setContinuous(true);

		// Add PID Controller to dashboard for testing
		turnController.setName("DriveSystem", "RotateController");
		SmartDashboard.putData(turnController);
	}

	@Override
	public void configPID(int slot) {
		leftMaster.config_kF(slot, Constants.Drive.leftPIDs.kF, 0);
		leftMaster.config_kP(slot, Constants.Drive.leftPIDs.kP, 0);
		leftMaster.config_IntegralZone(slot, 50);
		leftMaster.config_kI(slot, Constants.Drive.leftPIDs.kI, 0);
		leftMaster.config_kD(slot, Constants.Drive.leftPIDs.kD, 0);

		rightMaster.config_kF(slot, Constants.Drive.rightPIDs.kF, 0);
		rightMaster.config_kP(slot, Constants.Drive.rightPIDs.kP, 0);
		rightMaster.config_kI(slot, Constants.Drive.rightPIDs.kI, 0);
		rightMaster.config_kD(slot, Constants.Drive.rightPIDs.kD, 0);
	}

	@Override
	public void configSettings() {
		rightMaster.configVoltageCompSaturation(Constants.kVCompSaturation, Constants.Drive.kTimeoutMs);
		rightMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(Constants.kVCompSaturation, Constants.Drive.kTimeoutMs);
		leftMaster.enableVoltageCompensation(true);

		leftMaster.configPeakOutputForward(Constants.Drive.maxPercentFw, Constants.Drive.kTimeoutMs);
		leftMaster.configPeakOutputReverse(-Constants.Drive.maxPercentBw, Constants.Drive.kTimeoutMs);
		rightMaster.configPeakOutputForward(Constants.Drive.maxPercentFw, Constants.Drive.kTimeoutMs);
		rightMaster.configPeakOutputReverse(-Constants.Drive.maxPercentBw, Constants.Drive.kTimeoutMs);
	}

	public void startRefresh() {
		SmartDashboard.putNumber("Left P", Constants.Drive.leftPIDs.kP);
		SmartDashboard.putNumber("Left I", Constants.Drive.leftPIDs.kI);
		SmartDashboard.putNumber("Left D", Constants.Drive.leftPIDs.kD);
		SmartDashboard.putNumber("Goto Position L", 0.0);

		SmartDashboard.putNumber("Right P", Constants.Drive.rightPIDs.kP);
		SmartDashboard.putNumber("Right I", Constants.Drive.rightPIDs.kI);
		SmartDashboard.putNumber("Right D", Constants.Drive.rightPIDs.kD);
		SmartDashboard.putNumber("Goto Position R", 0.0);
	}

	public void refreshPID() {
		leftMaster.config_kP(0, SmartDashboard.getNumber("Left P", 0.0), 20);
		leftMaster.config_kI(0, SmartDashboard.getNumber("Left I", 0.0), 20);
		leftMaster.config_kD(0, SmartDashboard.getNumber("Left D", 0.0), 20);
		rightMaster.config_kP(0, SmartDashboard.getNumber("Right P", 0.0), 20);
		rightMaster.config_kI(0, SmartDashboard.getNumber("Right I", 0.0), 20);
		rightMaster.config_kD(0, SmartDashboard.getNumber("Right D", 0.0), 20);

		leftMaster.set(ControlMode.Position, SmartDashboard.getNumber("Goto Position L", 0.0));
		rightMaster.set(ControlMode.Position, SmartDashboard.getNumber("Goto Position R", 0.0));
	}

	public void enableTurnPID(boolean enabled) {
		turnController.setEnabled(enabled);
	}

	public void setTurnPID(double setpoint) {
		turnController.setSetpoint(setpoint);
	}

	public boolean turnPIDEnabled() {
		return turnController.isEnabled();
	}

	@Override
	public void pidWrite(double output) {
		vbusArcade(0.4, output);
	}

	public void initDefaultCommand() {
	}
}
