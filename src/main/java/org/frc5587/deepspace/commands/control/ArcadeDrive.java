/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Drive;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * ArcadeDrive controls the motors of the Drive subsystem with the joystick.
 * 
 * @see Drive
 */
public class ArcadeDrive extends InstantCommand {
	private Drive drivetrain;

	// private static double maxVelocity = Double.MIN_VALUE;

	public ArcadeDrive() {
		this.drivetrain = Robot.DRIVETRAIN;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		drivetrain.enableBrakeMode(true);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Code for finding max velocity of a given motor
		// var currentVel = kDrive.getLeftVelocity();
		// if (currentVel > maxVelocity) {
		// maxVelocity = currentVel;
		// System.out.println(maxVelocity);
		// }

		// Joystick configuration
		var throttle = -OI.joy.getY();
		var curve = OI.joy.getX() * 0.85;

		drivetrain.vbusArcade(throttle, curve);

		drivetrain.sendDebugInfo();
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