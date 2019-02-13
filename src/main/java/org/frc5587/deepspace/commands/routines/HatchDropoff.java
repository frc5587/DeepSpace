package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * HatchDropoff
 */
public class HatchDropoff extends CommandGroup {
    public HatchDropoff(ElevatorHeights height) {
        addParallel(new SetElevator(height));
        addSequential(new UltrasonicThreshold(10));
        addSequential(new DisableTurningPID());
        addSequential(new SetHatch(Value.kForward));
    }

    @Override
    protected void initialize() {
        Robot.tcpServer.startPiping();
    }
    
    @Override
    protected void end() {
        Robot.tcpServer.stopPiping();
    }

    @Override
    protected void interrupted() {
        end();
    }
}