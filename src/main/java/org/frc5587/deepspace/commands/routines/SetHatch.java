package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetHatch extends InstantCommand {
    DoubleSolenoid.Value value;
    
    public SetHatch(DoubleSolenoid.Value value) {
        requires(Robot.HATCH);
        this.value = value;
    }

    @Override
    protected void initialize() {
        Robot.HATCH.setGrab(value);
    }
}