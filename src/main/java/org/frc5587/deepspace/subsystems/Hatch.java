package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hatch extends Subsystem {
    private static DoubleSolenoid hatchPistons;
    private static DoubleSolenoid slicerPistons;

    public Hatch() {
        hatchPistons = new DoubleSolenoid(RobotMap.Hatch.HATCH_PISTONS[0], RobotMap.Hatch.HATCH_PISTONS[1]);
        slicerPistons = new DoubleSolenoid(RobotMap.Hatch.SLICER_PISTONS[0], RobotMap.Hatch.SLICER_PISTONS[1]);
    }

    public void hatchOpen() {
        hatchPistons.set(Value.kForward);
    }

    public void hatchClosed() {
        hatchPistons.set(Value.kReverse);
    }

    public void hatchOut() {
        slicerPistons.set(Value.kForward);
    }

    public void hatchIn() {
        slicerPistons.set(Value.kReverse);
    }
   

    @Override
    protected void initDefaultCommand() {
        
    }

}