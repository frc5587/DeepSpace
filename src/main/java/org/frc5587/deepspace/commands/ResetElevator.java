package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Basic Command to reset the elevator's encoder.
 */
public class ResetElevator extends InstantCommand {
    public ResetElevator() {

    }

    @Override
    protected void initialize() {
        Robot.ELEVATOR.resetEncoder();
    }
}