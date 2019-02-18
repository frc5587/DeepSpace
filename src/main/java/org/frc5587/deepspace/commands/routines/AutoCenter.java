package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.ProcessTCPData;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * AutoCenter
 */
public class AutoCenter extends CommandGroup {
    public AutoCenter() {
        addSequential(new UltrasonicThreshold(10));
        addSequential(new DisableTurningPID());
    }

    @Override
    protected void initialize() {
        ProcessTCPData.startPiping();
    }

    @Override
    protected void end() {
        Robot.DRIVETRAIN.stop();
        ProcessTCPData.stopPiping();
    }

    @Override
    protected void interrupted() {
        end();
    }
}