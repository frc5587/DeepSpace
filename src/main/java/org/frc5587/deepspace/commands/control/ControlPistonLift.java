package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlPistonLift extends Command {

    private boolean up;

    public ControlPistonLift() {
        up = true;
    }

    @Override
    protected void execute() {
        if(OI.xb.getStartButtonPressed()) {
            if(up = true) {
                Robot.PISTON_LIFT.pistonsDown();
                up = false;
            } else if (up = false) {
                Robot.PISTON_LIFT.pistonsUp();
                up = true;
            } 
        }
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}