package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Hatch;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * ControlHatch uses the Xbox controller's right bumper and back button for
 * controlling the hatch intake with the Hatch subsystem, defaulting to the
 * intake being down and not grabbing hatches.
 * 
 * <p>
 * The right bumper grabs hatches when held but defaults to not grabbing by
 * contracting the hatch intake's fingers. The back button then toggles between
 * the down and contracted/up states of the intake.
 * 
 * <p>
 * Automatic control of the hatch intake is also offered if
 * {@link Constants.Hatch#LIMIT_CONTROL_ON} is enabled. If enabled, this command
 * uses a limit switch to determine if a hatch is in the intake and should thus
 * be picked up.
 * 
 * @see Hatch
 */
public class ControlHatch extends Command {
    private static boolean down = true;
    private static boolean limitControlEngaged = false;
    private Hatch hatch;

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
            // Use limitControlEngaged as a toggle so grab is only issued once
            if (!limitControlEngaged && hatch.limitControl()) {
                hatch.grab();
                limitControlEngaged = true;
            } else if (!hatch.limitControl()) {
                limitControlEngaged = false;
            }
        }

        if (OI.xb.getBumper(Hand.kRight)) {
            hatch.grab();
        } else {
            hatch.drop();
        }

        // Use back button for manually contract or lower the intake
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