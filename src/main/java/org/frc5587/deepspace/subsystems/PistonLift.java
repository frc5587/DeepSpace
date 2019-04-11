package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PistonLift extends Subsystem {

    private DoubleSolenoid liftPistons;

    public PistonLift() {
        liftPistons = new DoubleSolenoid(RobotMap.PistonLift.LIFT_PISTON[0], RobotMap.PistonLift.LIFT_PISTON[1]);
    }

    public void pistonsDown() {
        liftPistons.set(Value.kForward);
    }

    public void pistonsUp() {
        liftPistons.set(Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}