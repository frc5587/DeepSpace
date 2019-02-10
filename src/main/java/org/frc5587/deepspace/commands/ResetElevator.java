package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetElevator extends InstantCommand {
    private boolean closed = true;

    public ResetElevator() {

    }

    @Override
    protected void initialize() {
        Robot.e.resetEncoder();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}