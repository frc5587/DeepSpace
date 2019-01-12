package org.frc5587.robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlAnalogInputs extends Command {

    public ControlAnalogInputs() {
        requires(Robot.inputs);
    }

    public void Print() {
        System.out.println("Right equals: " + Robot.inputs.rightSensor());
        System.out.println("Left equals: " + Robot.inputs.leftSensor());
    }

    @Override
    protected void execute() {
        Print();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}