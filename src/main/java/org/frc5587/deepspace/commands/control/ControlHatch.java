package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlHatch extends Command {
    private static boolean down = true;
    private static boolean limitControlEngaged = false;

    public ControlHatch() {

    }

    @Override
    protected void initialize() {
        Robot.HATCH.grab();
        Robot.HATCH.out();
    }

    @Override
    protected void execute() {
        if (Constants.Hatch.LIMIT_CONTROL_ON) {
            if (!limitControlEngaged) {
                if (Robot.HATCH.limitControl()) {
                    System.out.println("Running limit control");
                    Robot.HATCH.grab();
                    limitControlEngaged = true;
                }
            } else {
                if (!Robot.HATCH.limitControl()) {
                    limitControlEngaged = false;
                }
            }
        }

        if (OI.xb.getBumperPressed(Hand.kRight)) {
                Robot.HATCH.grab();
        } else if (OI.xb.getBumperPressed(Hand.kLeft)) {
                Robot.HATCH.drop();
        }

        if (OI.xb.getBackButtonPressed()) {
            if (down) {
                Robot.HATCH.out();
                down = false;
            } else {
                Robot.HATCH.stow();
                down = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}