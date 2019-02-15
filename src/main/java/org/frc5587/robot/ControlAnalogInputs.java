package org.frc5587.robot;

import edu.wpi.first.wpilibj.command.Command;

public class ControlAnalogInputs extends Command {

    public ControlAnalogInputs() {
        requires(Robot.inputs);
    }

    @Override
    protected void execute() {
        var left = Robot.inputs.getLeft();
        var right = Robot.inputs.getRight();
        System.out.println("Left equals: " + left);
        System.out.println("Right equals: " + right);
        System.out.println("Average: " + (left + right) / 2);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}