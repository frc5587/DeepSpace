package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlPistonLift extends Command {

    private int status;

    public ControlPistonLift() {
        status = 0;
    }

    @Override
    protected void execute() {
        if(OI.xb.getStartButtonPressed()) {
            if(status == 0) {
                Robot.PISTON_LIFT.pistonOneDown();
                Robot.PISTON_LIFT.pistonTwoDown();
                status ++;
            } else if (status == 1) {
                Robot.PISTON_LIFT.pistonOneUp();
                status ++;
            } else if (status == 2) {
                Robot.PISTON_LIFT.pistonTwoUp();
                status = 0;
            }
        }
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}