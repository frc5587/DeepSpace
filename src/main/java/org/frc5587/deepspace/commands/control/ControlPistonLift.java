package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlPistonLift extends Command {
    private boolean up;

    public ControlPistonLift() {
        this.up = true;
    }

    @Override
    protected void initialize() {
        Robot.PISTON_LIFT.pistonsDown();
    }

    @Override
    protected void execute() {
        if (OI.xb.getStartButtonPressed()) {
            if (up == true) {
                Robot.PISTON_LIFT.pistonsDown();
            } else {
                Robot.PISTON_LIFT.pistonsUp();
            }
            up = !up;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}