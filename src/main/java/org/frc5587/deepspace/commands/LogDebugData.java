package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * LogDebugData
 */
public class LogDebugData extends Command {
    public LogDebugData() {
        setRunWhenDisabled(true);
    }

    @Override
    protected void execute() {
        Robot.DRIVETRAIN.sendDebugInfo();
        Robot.ELEVATOR.sendDebugData();
        Robot.HATCH.sendDebugInfo();
        Robot.LIFT.sendDebugData();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}