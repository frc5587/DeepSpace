package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hatch extends Subsystem {
    private static DoubleSolenoid bob;

    public Hatch() {
        bob = new DoubleSolenoid(RobotMap.Hatch.HATCH_PISTON[0], RobotMap.Hatch.HATCH_PISTON[1]);
    }

    public void hatchOpen() {
        bob.set(Value.kForward);
    }

    public void hatchClosed() {
        bob.set(Value.kReverse);
    }

   

    @Override
    protected void initDefaultCommand() {
        
    }

}