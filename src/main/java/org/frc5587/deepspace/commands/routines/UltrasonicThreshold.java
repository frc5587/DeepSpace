package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.OI;

import edu.wpi.first.wpilibj.command.Command;

/**
 * UltrasonicThreshold
 */
public class UltrasonicThreshold extends Command {
    private double thresholdCM;

    /**
     * Standard constructor for UltrasonicThreshold, using a centimeter threshhold
     * to dictate whether the command is finished executing
     * 
     * @param threshhold threshold to require before returning true. If this number
     *                   is negative, it will be interpreted as a threshold behind
     *                   the current position
     */
    public UltrasonicThreshold(double thresholdCm) {
        this.thresholdCM = thresholdCm;
    }

    @Override
    protected void execute() {
        // System.out.println("Running Ultrasonic Threshold");
    }

    @Override
    protected boolean isFinished() {
        // TODO: Use real ultrasonic sensors
        return OI.joy.getRawButton(10);
    }

    @Override
    protected void end() {
        System.out.println("Threshold reached");
    }

    public enum Direction {
        FORWARDS, BACKWARDS;
    }
}