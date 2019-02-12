package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetHatch extends InstantCommand {

    private boolean position;

    public SetHatch(boolean position) {
        this.position = position;
        hatchPosition();
    }

    public void hatchPosition() {
        if(position) {
            Robot.HATCH.returnHatchPistons().set(Value.kForward);
        } else {
            Robot.HATCH.returnHatchPistons().set(Value.kReverse);
        }
    }
}
