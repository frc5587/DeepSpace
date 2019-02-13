package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ControlHatch extends InstantCommand {
    private static boolean down = false;
    private static boolean closed = false;

    public ControlHatch() {

    }

    @Override
    protected void initialize() {
        // Moved to Manager.java - uncomment if shift back to reg. Command
        // Robot.HATCH.hatchClosed();
        // Robot.HATCH.hatchOut();
    }

    @Override
    protected void execute() {

        if (OI.xb.getBumperPressed(Hand.kRight) || Robot.HATCH.limitControl()) {
            if (closed) {
                Robot.HATCH.hatchOpen();
                closed = false;
            } else {
                Robot.HATCH.hatchClosed();
                closed = true;
            }
            System.out.println("Right: " + closed);
        }
        if (OI.xb.getBumperPressed(Hand.kLeft)) {
            if(down) {
                Robot.HATCH.hatchOut();
                down = false;
            } else {
                Robot.HATCH.hatchIn();
                down = true;
            }
            System.out.println("Left: " + down);
        }
    }
}