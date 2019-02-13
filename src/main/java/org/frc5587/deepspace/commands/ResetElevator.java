package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

    public class ResetElevator extends InstantCommand {
        public ResetElevator() {

    }

    @Override
    protected void initialize() {
        Robot.ELEVATOR.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}