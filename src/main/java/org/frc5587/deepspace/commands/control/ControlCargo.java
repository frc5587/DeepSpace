package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlCargo extends Command {

    @Override
    protected void execute() {
        if(OI.xb.getY(Hand.kLeft) > 0) {
            Robot.CARGO.cargoIn();
        } else if(OI.xb.getY(Hand.kLeft) < 0) {
            Robot.CARGO.cargoOut();
        } else {
            Robot.CARGO.cargoStop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
