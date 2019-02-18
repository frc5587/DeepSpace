package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlLift extends Command {
    public ControlLift() {

    }

    @Override
    protected void execute() {
        var throttle = OI.xb.getY(Hand.kRight);
        Robot.LIFT.setLift(throttle);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}