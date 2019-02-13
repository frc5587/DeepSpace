package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.command.Command;

/**
 * SetElevator
 */
public class SetElevator extends Command {
    private ElevatorHeights toHeight;

    public SetElevator(ElevatorHeights height) {
        requires(Robot.ELEVATOR);
        this.toHeight = height;
    }

    @Override
    protected void initialize() {
        Robot.ELEVATOR.setElevator(toHeight);
    }

    @Override
    protected boolean isFinished() {
        return Robot.ELEVATOR.isMPFinished();
    }
}