/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.subsystems;

import edu.wpi.first.wpilibj.SPI.Port;

import org.frc5587.lib.pathfinder.AbstractDrive;
import org.frc5587.lib.pathfinder.GyroCompMPRunner;
import org.frc5587.lib.pathfinder.Pathgen;
import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class Drive extends AbstractDrive {
	public static final Pathgen SLOW_PATHGEN = new Pathgen(30, 0.010, 36, 60, 120);
	public static final Pathgen MED_PATHGEN = new Pathgen(30, 0.010, 60, 80, 160);
	public static final Pathgen FAST_PATHGEN = new Pathgen(30, 0.010, 84, 80, 160);

	public GyroCompMPRunner currentMpRunner;

	public Drive() {
		super(new TalonSRX(RobotMap.Drive.leftMaster), new TalonSRX(RobotMap.Drive.rightMaster),
				new VictorSPX(RobotMap.Drive.leftSlave), new VictorSPX(RobotMap.Drive.rightSlave));

		setAHRS(new AHRS(Port.kMXP));
		setConstants(Constants.Drive.kMaxVelocity, Constants.Drive.kTimeoutMs, Constants.Drive.stuPerRev,
				Constants.Drive.stuPerInch, Constants.Drive.wheelDiameter, Constants.Drive.minBufferCount);

		currentMpRunner = null;
	}

	@Override
	public void configPID(int slot) {
		leftMaster.config_kF(slot, Constants.Drive.leftPIDs.kF, 0);
		leftMaster.config_kP(slot, Constants.Drive.leftPIDs.kP, 0);
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

	public void initDefaultCommand() {
	}
}
