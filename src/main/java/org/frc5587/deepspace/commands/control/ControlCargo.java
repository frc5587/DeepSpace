package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * ControlCargo controls the wheels of the Cargo subsystem with the right
 * joystick of the Xbox controller.
 * 
 * @see Cargo
 */
public class ControlCargo extends Command {

    @Override
    protected void execute() {
        Robot.CARGO.cargoSet(OI.xb.getY(Hand.kRight));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
