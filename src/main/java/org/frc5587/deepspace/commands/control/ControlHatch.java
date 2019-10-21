package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Hatch;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlHatch extends Command {
    private Hatch hatch;
    private static boolean down = true;
    private static boolean limitControlEngaged = false;

    public ControlHatch() {
        this.hatch = Robot.HATCH;
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
                if (hatch.limitControl()) {
                    System.out.println("Running limit control");
                    hatch.grab();
                    limitControlEngaged = true;
                }
            } else {
                if (!hatch.limitControl()) {
                    limitControlEngaged = false;
                }
            }
        }

        if (OI.xb.getBumper(Hand.kRight)) {
            hatch.grab();
        } else {
            hatch.drop();
        }

        if (OI.xb.getBackButtonPressed()) {
            if (down) {
                hatch.out();
                down = false;
            } else {
                hatch.stow();
                down = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}