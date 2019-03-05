package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlCargo extends Command {

    @Override
    protected void execute() {
        Robot.CARGO.cargoSet(OI.xb.getY(Hand.kLeft));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
