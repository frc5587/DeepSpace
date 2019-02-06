package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlHatch extends Command {
    private boolean closed = true;

    public ControlHatch() {

    }

    @Override
    protected void initialize() {
        Robot.HATCH.hatchClosed();
        Robot.HATCH.hatchOut();
    }

    @Override
    protected void execute() {
        if (OI.xb.getBumperPressed(Hand.kRight)) {
            if (closed) {
                Robot.HATCH.hatchOpen();
                closed = false;
            } else {
                Robot.HATCH.hatchClosed();
                closed = true;
            }
        }
        if (OI.xb.getBumperPressed(Hand.kLeft)) {
            if(closed) {
                Robot.HATCH.hatchOut();
                closed = false;
            } else {
                Robot.HATCH.hatchIn();
                closed = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}