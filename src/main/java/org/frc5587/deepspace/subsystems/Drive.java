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

	private static LimitedHashMap<Double, Double> gyroHistory;
	private static LimitedHashMap<Double, Double> whileReadingHistory;
	public static boolean readingHistory = false;
	private static boolean lastReadingState = false;

	private static ArrayList<Double> visionTimeDeltas;
	private static Double visionTimeDelta;
	private static PIDController turnController;
	private static boolean turnEnabledFirstTime;

	public Drive() {
		super(new TalonSRX(RobotMap.Drive.LEFT_MASTER), new TalonSRX(RobotMap.Drive.RIGHT_MASTER),
				new VictorSPX(RobotMap.Drive.LEFT_SLAVE), new VictorSPX(RobotMap.Drive.RIGHT_SLAVE), true);

		setAHRS(new AHRS(Port.kMXP));
		setConstants(Constants.Drive.kMaxVelocity, Constants.Drive.kTimeoutMs, Constants.Drive.stuPerRev,
				Constants.Drive.stuPerInch, Constants.Drive.wheelDiameter, Constants.Drive.minBufferCount);

		gyroHistory = new LimitedHashMap<>(Constants.Drive.GYRO_HISTORY_LENGTH);
		visionTimeDeltas = new ArrayList<>();

		var fpid = Constants.Drive.TURN_FPID;
		turnController = new PIDController(fpid.kP, fpid.kI, fpid.kD, fpid.kF, ahrs, this);
		turnController.setInputRange(-180.0, 180.0);
		turnController.setOutputRange(-1.0, 1.0);
		turnController.setAbsoluteTolerance(Constants.Drive.TOLERANCE_DEGREES);
		turnController.setContinuous(true);
		turnController.disable();

		// Add PID Controller to dashboard for testing
		turnController.setName("DriveSystem", "RotateController");
		SmartDashboard.putData(turnController);
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

	public void setVisionTimeDelta(double rpiTime, double systemTime) {
		// The pi script starts after the rio boots (time delay in script)
		System.out.println("Delta: " + (systemTime - rpiTime));
		visionTimeDeltas.add(systemTime - rpiTime);

		double sum = 0;
		for (var delta : visionTimeDeltas) {
			sum += delta;
		}

		this.visionTimeDelta = sum / visionTimeDeltas.size();
	}

	public void updateGyroHistory() {
		// System.out.println("Trying to update");
		if (visionTimeDelta != null) {
			var currentTime = Timer.getFPGATimestamp() - visionTimeDelta;
			// System.out.println("Updating history...");
			if (readingHistory) {
				if (!lastReadingState) {
					// Not reading before, but now reading so clear old data
					whileReadingHistory.clear();
				}
				// Reading now, so store data temporarily
				whileReadingHistory.put(currentTime, getHeading(180.0));
			} else {
				if (lastReadingState) {
					// Not reading now but reading before, so copy to gyroHistory map
					gyroHistory.putAll(whileReadingHistory);
				}
				// Not reading now, so update old data
				gyroHistory.put(currentTime, getHeading(180.0));
			}
		}
		lastReadingState = readingHistory;
	}

	public double getAngleAtClosestTime(double time) {
		readingHistory = true;

		double closestVal = 0;
		double minDistance = Double.MAX_VALUE;
		Double lastSign = null;

		time += visionTimeDelta;

		var historyCopy = new LinkedHashMap<>(gyroHistory);
		System.out.println(historyCopy.toString());

		for (var entry : historyCopy.keySet()) {
			var delta = time - entry;
			var sign = Math.signum(delta);
			var distance = Math.abs(delta);

			System.out.println(distance);

			if (distance < minDistance) {
				System.out.println("Hello");
				closestVal = entry;
				minDistance = distance;
			}

			// Check if we can end early
			if (lastSign != null && lastSign != sign) {
				break;
			}
			lastSign = sign;
		}

		System.out.println("TARGET TIME: " + time);
		System.out.println("CLOSEST VAL: " + closestVal);

		readingHistory = false;
		return historyCopy.get(closestVal);
	}

	@Override
	public void pidWrite(double output) {
		if (turnEnabledFirstTime) {
			vbusArcade(0, Constants.Drive.LPF_PERCENT * output);	
		}
	}

	public void initDefaultCommand() {
	}
}
