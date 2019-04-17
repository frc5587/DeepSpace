package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlPistonLift extends Command {
    public ControlPistonLift() {
    }

    @Override
    protected void execute() {
        if (OI.xb.getStartButtonPressed()) {
            Robot.PISTON_LIFT.pistonsDown();
        } else if (OI.xb.getStartButtonReleased()) {
            Robot.PISTON_LIFT.pistonsUp();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}