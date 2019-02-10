/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.InstantCommand;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Drive;

/**
 * An example command.  You can replace me with your own command.
 */
public class ArcadeDrive extends InstantCommand {
	private Drive kDrive;

	public ArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DRIVETRAIN);
		this.kDrive = Robot.DRIVETRAIN;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		kDrive.enableBrakeMode(false);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Xbox configuration
		// double throttle = -OI.xb.getY(Hand.kLeft);
		// double curve = OI.xb.getX(Hand.kLeft);

		// Joystick configuration
		var throttle = -OI.joy.getY();
		var curve = OI.joy.getX();

		kDrive.vbusArcade(throttle, curve);

		kDrive.sendDebugInfo();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}