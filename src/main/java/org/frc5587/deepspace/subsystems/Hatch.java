package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hatch extends Subsystem {
    private HashMap<HatchGrabState, DoubleSolenoid.Value> stateMap;
    private HashMap<HatchStowedState, DoubleSolenoid.Value> stowedMap;
    private DoubleSolenoid hatchPistons, slicerPistons;
    private DigitalInput limitSwitchOne, limitSwitchTwo;
    // private HatchStowedState stowedState;
    // private HatchGrabState grabState;

    public Hatch() {
        hatchPistons = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.Hatch.HATCH_PISTONS[0],
                RobotMap.Hatch.HATCH_PISTONS[1]);
        slicerPistons = new DoubleSolenoid(RobotMap.PCM_ID, RobotMap.Hatch.SLICER_PISTONS[0],
                RobotMap.Hatch.SLICER_PISTONS[1]);
        limitSwitchOne = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_ONE);
        limitSwitchTwo = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_TWO);

        stateMap = new HashMap<>();
        stateMap.put(HatchGrabState.DROP, DoubleSolenoid.Value.kForward);
        stateMap.put(HatchGrabState.GRAB, DoubleSolenoid.Value.kReverse);

        stowedMap = new HashMap<>();
        stowedMap.put(HatchStowedState.OUT, DoubleSolenoid.Value.kReverse);
        stowedMap.put(HatchStowedState.STOWED, DoubleSolenoid.Value.kForward);

        // stowedState = getStowedState(slicerPistons.get());
        // grabState = getGrabState(hatchPistons.get());
    }

    private HatchStowedState getStowedState(DoubleSolenoid.Value val) {
        for (var entryPair : stowedMap.entrySet()) {
            if (entryPair.getValue() == val) {
                return entryPair.getKey();
            }
        }
        return null;
    }

    private HatchGrabState getGrabState(DoubleSolenoid.Value val) {
        for (var entryPair : stateMap.entrySet()) {
            if (entryPair.getValue() == val) {
                return entryPair.getKey();
            }
        }
        return null;
    }

    private DoubleSolenoid.Value getVal(HatchGrabState state)  {
        return stateMap.get(state);
    }

    private DoubleSolenoid.Value getVal(HatchStowedState state) {
        return stowedMap.get(state);
    }

    public void grab() {
        setGrab(HatchGrabState.DROP);
    }

    public void drop() {
        setGrab(HatchGrabState.GRAB);
    }

    public void out() {
        setStow(HatchStowedState.OUT);
    }

    public void stow() {
        setStow(HatchStowedState.STOWED);
    }

    public void setGrab(HatchGrabState state) {
        // grabState = state;
        hatchPistons.set(getVal(state));
    }

    public void setStow(HatchStowedState state) {
        // stowedState = state;
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

    public void sendDebugInfo() {
        SmartDashboard.putBoolean("Hatch Held", limitControl());
        // SmartDashboard.putString("Stowed", stowedState == null ? "null" : stowedState.toString());
        // SmartDashboard.putString("Grab", grabState == null ? "null" : grabState.toString());
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