package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hatch extends Subsystem {
    private DoubleSolenoid hatchPistons;
    private DoubleSolenoid slicerPistons;
    private DigitalInput limitSwitchOne;
    private DigitalInput limitSwitchTwo;

    public Hatch() {
        hatchPistons = new DoubleSolenoid(RobotMap.Hatch.HATCH_PISTONS[0], RobotMap.Hatch.HATCH_PISTONS[1]);
        slicerPistons = new DoubleSolenoid(RobotMap.Hatch.SLICER_PISTONS[0], RobotMap.Hatch.SLICER_PISTONS[1]);
        limitSwitchOne = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_ONE);
        limitSwitchTwo = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_TWO);
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

    public boolean limitControl() {
        if (Constants.Hatch.REQUIRE_BOTH) {
            return limitSwitchOne.get() && limitSwitchTwo.get();
        }
        else {
            return limitSwitchOne.get() || limitSwitchTwo.get(); 
        } 
    }

    public DoubleSolenoid returnHatchPistons() {
        return hatchPistons;
    }

    @Override
    protected void initDefaultCommand() {

    }

}