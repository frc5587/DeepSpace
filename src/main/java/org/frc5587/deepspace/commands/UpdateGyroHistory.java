package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * UpdateGyroHistory
 */
public class UpdateGyroHistory extends Command {
    public UpdateGyroHistory() {
        setRunWhenDisabled(true);
    }

    @Override
    protected void execute() {
        Robot.DRIVETRAIN.updateGyroHistory();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
