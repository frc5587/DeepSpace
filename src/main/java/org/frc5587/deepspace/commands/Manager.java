package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.commands.control.ArcadeDrive;
import org.frc5587.deepspace.commands.routines.Limelight;

import edu.wpi.first.wpilibj.command.Command;

public class Manager extends Command {
    private Command currentRoutine;

    public Manager() {

    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (currentRoutine == null) {
            if (OI.joy.getRawButton(12)) {
                currentRoutine = new Limelight();
                currentRoutine.start();
            } else {
                new ArcadeDrive().start();
            }
        } else if (OI.joy.getRawButtonReleased(12)) {
            currentRoutine.cancel();
            currentRoutine = null;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        if (currentRoutine != null) {
            currentRoutine.cancel();
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
