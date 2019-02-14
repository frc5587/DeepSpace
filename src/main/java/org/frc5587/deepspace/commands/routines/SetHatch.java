package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Hatch;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetHatch extends InstantCommand {
    private Hatch.HatchGrabState grab;
    private Hatch.HatchStowedState stow;
    
    public SetHatch(Hatch.HatchGrabState grab) {
        this(grab, null);
    }

    public SetHatch(Hatch.HatchStowedState stow) {
        this(null, stow);
    }

    public SetHatch(Hatch.HatchGrabState grab, Hatch.HatchStowedState stow) {
        requires(Robot.HATCH);
        this.grab = grab;
        this.stow = stow;
    }

    @Override
    protected void initialize() {
        if (grab != null) {
            Robot.HATCH.setGrab(grab);
        }
        if (stow != null) {
            Robot.HATCH.setStow(stow);
        }
    }
}