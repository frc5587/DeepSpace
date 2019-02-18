package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.commands.control.ArcadeDrive;
import org.frc5587.deepspace.commands.routines.AutoCenter;

import edu.wpi.first.wpilibj.command.Command;

public class Manager extends Command {
    private Command currentRoutine;

    public Manager() {

    }

    @Override
    protected void initialize() {
        Robot.HATCH.drop();
        Robot.HATCH.stow();
    }

    @Override
    protected void execute() {
        if (currentRoutine == null) {
            if (OI.joy.getRawButton(12)) {
                currentRoutine = new AutoCenter();
                currentRoutine.start();
            } else {
                new ArcadeDrive().start();
            }
        } else if (OI.xb.getRawButtonReleased(12)) {
            currentRoutine.cancel();
            currentRoutine = null;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
