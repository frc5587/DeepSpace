/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.subsystems;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;
import org.frc5587.lib.LimitedHashMap;
import org.frc5587.lib.pathfinder.AbstractDrive;
import org.frc5587.lib.pathfinder.Pathgen;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Drive extends AbstractDrive implements PIDOutput {
	public static final Pathgen SLOW_PATHGEN = new Pathgen(30, 0.010, 36, 60, 120);
	public static final Pathgen MED_PATHGEN = new Pathgen(30, 0.010, 60, 80, 160);
	public static final Pathgen FAST_PATHGEN = new Pathgen(30, 0.010, 84, 80, 160);

	private LimitedHashMap<Double, Double> gyroHistory;
	private ArrayList<Double> visionTimeDeltas;
	private Double visionTimeDelta;
	private PIDController turnController;
	private boolean turnEnabledFirstTime;

	public Drive() {
		super(new TalonSRX(RobotMap.Drive.LEFT_MASTER), new TalonSRX(RobotMap.Drive.RIGHT_MASTER),
				new VictorSPX(RobotMap.Drive.LEFT_SLAVE), new VictorSPX(RobotMap.Drive.RIGHT_SLAVE), true);

		setAHRS(new AHRS(Port.kMXP));
		setConstants(Constants.Drive.kMaxVelocity, Constants.Drive.kTimeoutMs, Constants.Drive.stuPerRev,
				Constants.Drive.stuPerInch, Constants.Drive.wheelDiameter, Constants.Drive.minBufferCount);

		gyroHistory = new LimitedHashMap<>(Constants.Drive.GYRO_HISTORY_LENGTH);
		visionTimeDeltas = new ArrayList<>();

		var fpid = Constants.Drive.TURN_FPID;
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
		var timeoutMs =  Constants.Drive.kTimeoutMs;
		rightMaster.configVoltageCompSaturation(Constants.kVCompSaturation, timeoutMs);
		rightMaster.enableVoltageCompensation(true);
		leftMaster.configVoltageCompSaturation(Constants.kVCompSaturation, timeoutMs);
		leftMaster.enableVoltageCompensation(true);

		rightMaster.configPeakCurrentLimit(35, timeoutMs);
		leftMaster.configPeakCurrentLimit(35, timeoutMs);
		rightMaster.configPeakCurrentDuration(2000);
		leftMaster.configPeakCurrentDuration(2000);
		rightMaster.configContinuousCurrentLimit(35, timeoutMs);
		leftMaster.configContinuousCurrentLimit(35, timeoutMs);

		leftMaster.configPeakOutputForward(Constants.Drive.maxPercentFw, timeoutMs);
		leftMaster.configPeakOutputReverse(-Constants.Drive.maxPercentBw, timeoutMs);
		rightMaster.configPeakOutputForward(Constants.Drive.maxPercentFw, timeoutMs);
		rightMaster.configPeakOutputReverse(-Constants.Drive.maxPercentBw, timeoutMs);
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
		System.out.println("Enabling PID...");
		if (enabled) {
			turnEnabledFirstTime = true;
		}

		turnController.setEnabled(enabled);
	}

	public void setTurnPID(double setpoint) {
		turnController.setSetpoint(setpoint);
	}

	public boolean turnPIDEnabled() {
		return turnController.isEnabled();
	}

	public void setVisionTimeDelta(double visionTimeDelta) {
		visionTimeDeltas.add(visionTimeDelta);

		double sum = 0;
		for (var delta : visionTimeDeltas) {
			sum += delta;
		}

		this.visionTimeDelta = sum / visionTimeDeltas.size();
	}

	public void updateGyroHistory() {
		if (visionTimeDelta != null) {
			gyroHistory.put(Timer.getFPGATimestamp() + visionTimeDelta, getHeading(180.0));
		}
	}

	public double getAngleAtClosestTime(double time) {
		double lastVal = Double.NaN;
		double lastDeltaSign = Double.NaN;

		time += visionTimeDelta;

		var currentCopy = new LinkedHashMap<>(gyroHistory);

		// gyroHistory array is sorted, given that it's made up of times
		for (var entry : currentCopy.keySet()) {
			// When sign of delta changes, we know we have overshot, so use last (closest) value
			var delta = time - entry;
			var sign = Math.signum(delta);
			if (lastDeltaSign != Double.NaN && lastDeltaSign != sign) {
				break;
			} else {
				lastVal = entry;
				lastDeltaSign = sign;
			}
		}

		System.out.println("TARGET TIME: " + time);
		System.out.println("LAST VAL: " + lastVal);

		return gyroHistory.get(lastVal);
	}

	@Override
	public void pidWrite(double output) {
		if (turnEnabledFirstTime) {
			vbusArcade(0.3, Constants.Drive.LPF_PERCENT * output);	
		}
	}

	public void initDefaultCommand() {
	}
}
