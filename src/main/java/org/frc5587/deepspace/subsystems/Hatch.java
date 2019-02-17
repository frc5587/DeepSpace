package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Hatch extends Subsystem {
    private HashMap<HatchGrabState, DoubleSolenoid.Value> stateMap;
    private HashMap<HatchStowedState, DoubleSolenoid.Value> stowedMap;
    private DoubleSolenoid hatchPistons, slicerPistons;
    private DigitalInput limitSwitchOne, limitSwitchTwo;

    public Hatch() {
        hatchPistons = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.Hatch.HATCH_PISTONS[0],
                RobotMap.Hatch.HATCH_PISTONS[1]);
        slicerPistons = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.Hatch.SLICER_PISTONS[0],
                RobotMap.Hatch.SLICER_PISTONS[1]);
        limitSwitchOne = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_ONE);
        limitSwitchTwo = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_TWO);

        stateMap = new HashMap<>();
        stateMap.put(HatchGrabState.DROP, Value.kReverse);
        stateMap.put(HatchGrabState.GRAB, Value.kForward);

        stowedMap = new HashMap<>();
        stowedMap.put(HatchStowedState.OUT, Value.kReverse);
        stowedMap.put(HatchStowedState.STOWED, Value.kForward);
    }

    private DoubleSolenoid.Value getVal(HatchGrabState state)  {
        return stateMap.get(state);
    }

    private DoubleSolenoid.Value getVal(HatchStowedState state) {
        return stowedMap.get(state);
    }

    public void grab() {
        hatchPistons.set(getVal(HatchGrabState.DROP));
    }

    public void drop() {
        hatchPistons.set(getVal(HatchGrabState.GRAB));
    }

    public void out() {
        slicerPistons.set(getVal(HatchStowedState.OUT));
    }

    public void stow() {
        slicerPistons.set(getVal(HatchStowedState.STOWED));
    }

    public void setGrab(HatchGrabState state) {
        hatchPistons.set(getVal(state));
    }

    public void setStow(HatchStowedState state) {
        slicerPistons.set(getVal(state));
    }

    public boolean limitControl() {
        if (Constants.Hatch.REQUIRE_BOTH) {
            return !limitSwitchOne.get() && !limitSwitchTwo.get();
        }
        else {
            return !limitSwitchOne.get() || !limitSwitchTwo.get(); 
        } 
    }

    @Override
    protected void initDefaultCommand() {

    }

    public enum HatchGrabState {
        GRAB, DROP;
    }

    public enum HatchStowedState {
        STOWED, OUT;
    }
}