package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.ProcessTCPData;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;
import org.frc5587.deepspace.subsystems.Hatch.HatchGrabState;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * HatchDropoff
 */
public class HatchDropoff extends CommandGroup {
    public HatchDropoff(ElevatorHeights height) {
        addParallel(new SetElevator(height));
        addSequential(new UltrasonicThreshold(10));
        addSequential(new DisableTurningPID());
        addSequential(new SetHatch(HatchGrabState.DROP));
    }

    @Override
    protected void initialize() {
        ProcessTCPData.startPiping();
    }
    
    @Override
    protected void end() {
        ProcessTCPData.stopPiping();
    }

    @Override
    protected void interrupted() {
        end();
    }
}