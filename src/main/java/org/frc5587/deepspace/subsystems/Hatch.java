package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the hatch intake.
 * 
 * <p>
 * The hatch intake consists of pistons that either grab or do not grab
 * (referred to as "drop") hatches along with pistons that either vertically
 * stow the hatch intake to be in frame perimeter or lower the hatch intake so
 * that is horizontal and can pick up hatches.
 */
public class Hatch extends Subsystem {
    private HashMap<HatchGrabState, DoubleSolenoid.Value> stateMap;
    private HashMap<HatchStowedState, DoubleSolenoid.Value> stowedMap;
    private DoubleSolenoid hatchPistons, slicerPistons;
    private DigitalInput limitSwitchOne, limitSwitchTwo;

    public Hatch() {
        hatchPistons = new DoubleSolenoid(RobotMap.Hatch.HATCH_PISTONS[0], RobotMap.Hatch.HATCH_PISTONS[1]);
        slicerPistons = new DoubleSolenoid(RobotMap.Hatch.SLICER_PISTONS[0], RobotMap.Hatch.SLICER_PISTONS[1]);

        // Limit switches for detecting whether a hatch is currently in the intake
        limitSwitchOne = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_ONE);
        limitSwitchTwo = new DigitalInput(RobotMap.Hatch.LIMIT_SWITCH_TWO);

        // Associates enum values with solenoid values - change this if solenoids
        // directions are swapped
        stateMap = new HashMap<>();
        stateMap.put(HatchGrabState.DROP, DoubleSolenoid.Value.kForward);
        stateMap.put(HatchGrabState.GRAB, DoubleSolenoid.Value.kReverse);
        stowedMap = new HashMap<>();
        stowedMap.put(HatchStowedState.OUT, DoubleSolenoid.Value.kReverse);
        stowedMap.put(HatchStowedState.STOWED, DoubleSolenoid.Value.kForward);
    }

    private DoubleSolenoid.Value getVal(HatchGrabState state) {
        return stateMap.get(state);
    }

    private DoubleSolenoid.Value getVal(HatchStowedState state) {
        return stowedMap.get(state);
    }

    /**
     * Grab a hatch that is currently in the hatch intake.
     */
    public void grab() {
        setGrab(HatchGrabState.DROP);
    }

    /**
     * Drop a hatch that is currently in the hatch intake.
     */
    public void drop() {
        setGrab(HatchGrabState.GRAB);
    }

    /**
     * Lower the hatch intake so that it is horizontal and can pick up hatches
     * instead of being stowed away vertically.
     */
    public void lower() {
        setStow(HatchStowedState.OUT);
    }

    /**
     * Stow the hatch intake vertically so that it is no longer horizontal and
     * cannot pick up hatches.
     */
    public void stow() {
        setStow(HatchStowedState.STOWED);
    }

    /**
     * Set the grab state of the hatch intake to the given state.
     */
    private void setGrab(HatchGrabState state) {
        hatchPistons.set(getVal(state));
    }

    /**
     * Set the stowed state of the hatch intake to the given state.
     * 
     * @param state the state to set the hatch to
     */
    private void setStow(HatchStowedState state) {
        slicerPistons.set(getVal(state));
    }

    /**
     * Uses either one or two limit switches to determine whether a hatch is present
     * in the hatch intake.
     * 
     * <p>
     * Whether to use one or two limit switches is set by the constant
     * {@link Constants.Hatch#REQUIRE_BOTH}
     * 
     * @return whether a hatch is currently in the hatch intake
     */
    public boolean hatchPresent() {
        if (Constants.Hatch.REQUIRE_BOTH) {
            return !limitSwitchOne.get() && !limitSwitchTwo.get();
        } else {
            return !limitSwitchOne.get() || !limitSwitchTwo.get();
        }
    }

    /**
     * Log subsystem-specific debug data to SmartDashboard.
     */
    public void logDebugData() {
        SmartDashboard.putBoolean("Hatch Held", hatchPresent());
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * Possible states of the hatch's grabbing system.
     */
    public enum HatchGrabState {
        /**
         * When a hatch is potentially grabbed and will not be let go by the intake.
         */
        GRAB,

        /**
         * when hatches are able to move freely on the intake without being kept.
         */
        DROP
    }

    /**
     * Possible states of the hatch's stowing system.
     */
    public enum HatchStowedState {
        /**
         * When the intake is pulled up vertically and cannot pick up hatches.
         */
        STOWED,

        /**
         * hWen the intake is lowered so that it is horizontal and can pick up hatches.
         */
        OUT
    }
}