/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.frc5587.deepspace.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Drive;

/**
 * An example command.  You can replace me with your own command.
 */
public class ArcadeDrive extends Command {
	private XboxController xb;
	private Drive kDrive;

	public ArcadeDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DRIVETRAIN);
		this.kDrive = Robot.DRIVETRAIN;
		xb = OI.xb;
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
		double throttle = -OI.xb.getY(Hand.kLeft);
		double curve = OI.xb.getX(Hand.kLeft);

		// Joystick configuration
		// double throttle = -OI.joystick.getY(Hand.kLeft);
		// double curve = OI.joystick.getX(Hand.kLeft);

		kDrive.vbusArcade(throttle, curve);
		kDrive.sendDebugInfo();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
