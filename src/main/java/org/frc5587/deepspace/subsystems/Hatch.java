package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hatch extends Subsystem {
    private static DoubleSolenoid hatchPiston;

    public Hatch() {
        hatchPiston = new DoubleSolenoid(RobotMap.Hatch.HATCH_PISTON[0], RobotMap.Hatch.HATCH_PISTON[1]);
    }

    public void hatchOpen() {
        hatchPiston.set(Value.kForward);
    }

    public void hatchClosed() {
        hatchPiston.set(Value.kReverse);
    }

   

    @Override
    protected void initDefaultCommand() {
        
    }

}