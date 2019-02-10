package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetEncoders extends InstantCommand {
    public ResetEncoders() {
        // Use requires() here to declare subsystem dependencies
        // requires(Robot.exampleSubsystem);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.DRIVETRAIN.resetEncoders();
        Robot.TURRET.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }
}