package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for the piston lift.
 * 
 * <p>The piston lift consists of two pistons that expand to push up the back half
 * of the robot after ramping partway onto the platform so the robot can drive
 * fully on.
 */
public class PistonLift extends Subsystem {
    private DoubleSolenoid liftPistons;

    public PistonLift() {
        liftPistons = new DoubleSolenoid(RobotMap.PistonLift.LIFT_PISTON[0], RobotMap.PistonLift.LIFT_PISTON[1]);
    }

    /**
     * Expand the piston lift to prop up the robot.
     */
    public void pistonsDown() {
        liftPistons.set(Value.kForward);
    }

    /**
     * Constract the piston lift so the pistons to not make contact with the ground.
     */
    public void pistonsUp() {
        liftPistons.set(Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {

    }
}