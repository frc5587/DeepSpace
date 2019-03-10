package org.frc5587.deepspace.subsystems;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PistonLift extends Subsystem {

    private DoubleSolenoid pistonOne, pistonTwo;

    public PistonLift() {
        pistonOne = new DoubleSolenoid(RobotMap.PistonLift.PISTON_ONE[0], RobotMap.PistonLift.PISTON_ONE[1]);
        pistonTwo = new DoubleSolenoid(RobotMap.PistonLift.PISTON_TWO[0], RobotMap.PistonLift.PISTON_TWO[1]);
    }

    public void pistonOneDown() {
        pistonOne.set(Value.kForward);
    }

    public void pistonTwoDown() {
        pistonTwo.set(Value.kForward);
    }
    
    public void pistonOneUp() {
        pistonOne.set(Value.kReverse);
    }

    public void pistonTwoUp() {
        pistonTwo.set(Value.kReverse);
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}