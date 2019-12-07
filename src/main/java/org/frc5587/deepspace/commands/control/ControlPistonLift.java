package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.PistonLift;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ControlPistonLift uses the Xbox controller's start button to expand the
 * piston lift.
 * 
 * <p>
 * The command defaults to contracting the piston, meaning the lift/pistons are
 * up by default, but expands the piston while the start button is held.
 * 
 * @see PistonLift
 */
public class ControlPistonLift extends Command {
    private PistonLift lift;

    public ControlPistonLift() {
        lift = Robot.PISTON_LIFT;
    }

    @Override
    protected void execute() {
        if (OI.xb.getStartButtonPressed()) {
            lift.pistonsDown();
        } else if (OI.xb.getStartButtonReleased()) {
            lift.pistonsUp();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}