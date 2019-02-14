package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ControlHatch extends InstantCommand {
    private static boolean down = true;
    private static boolean closed = true;

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
        if (Robot.HATCH.limitControl()) {
            System.out.println("Running limit control");
            Robot.HATCH.grab();
            closed = false;
        }

        if (OI.xb.getBumperPressed(Hand.kRight)) {
            if (closed) {
                Robot.HATCH.grab();
                closed = false;
            } else {
                Robot.HATCH.drop();
                closed = true;
            }
            System.out.println("Right: " + closed);
        }
        if (OI.xb.getBumperPressed(Hand.kLeft)) {
            if(down) {
                Robot.HATCH.out();
                down = false;
            } else {
                Robot.HATCH.stow();
                down = true;
            }
            System.out.println("Left: " + down);
        }
    }
}