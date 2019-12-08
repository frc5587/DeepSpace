package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.Command;

/**
 * LimitResetElevator resets the elevator's encoders whenever the elevator's
 * limit switch is hit.
 * 
 * @see Elevator
 */
public class LimitResetElevator extends Command {
    private Elevator elevator;

    public LimitResetElevator() {
        this.elevator = Robot.ELEVATOR;
        setRunWhenDisabled(true);
    }

    @Override
    protected void execute() {
        // Reset the elevator when the limit switch is triggered
        if (elevator.bottomSwitchValue()) {
            elevator.resetEncoder();
            // elevator.elevatorHold();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
