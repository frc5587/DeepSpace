package org.frc5587.robot;

import org.frc5587.lib.MathHelper;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class AnalogInputs extends Subsystem {

    private AnalogInput right;
    private AnalogInput left;

    public AnalogInputs() {
        right = new AnalogInput(0);
        left = new AnalogInput(1);
    }

    
    public double getRight() {
        return MathHelper.map(right.getAverageVoltage(), 0, 5, 0, 1023) / 2;
    }

    public double getLeft() {
        return MathHelper.map(left.getAverageVoltage(), 0, 5, 0, 1023) / 2;
    }

    public double getAverage() {
        return (getRight() + getLeft()) / 2;
    }

    @Override
    protected void initDefaultCommand() {

    }
}