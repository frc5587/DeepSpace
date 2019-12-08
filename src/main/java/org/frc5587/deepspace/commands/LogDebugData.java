package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * LogDebugData calls the debug data on various subsystems every 20ms.
 */
public class LogDebugData extends Command {
    public LogDebugData() {
        setRunWhenDisabled(true);
    }

    @Override
    protected void execute() {
        // Robot.DRIVETRAIN.sendDebugInfo();
        // Robot.ELEVATOR.logDebugData();
        Robot.HATCH.logDebugData();
        // Robot.LIFT.logDebugData();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}