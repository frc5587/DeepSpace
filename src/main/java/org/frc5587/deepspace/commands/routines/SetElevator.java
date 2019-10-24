package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * SetElevator
 */
public class SetElevator extends Command {
    private Elevator elevator;
    private ElevatorHeights toHeight;
    private boolean runningCheck;
    private Timer emergencyStopTimer;
    private boolean emergencyStop;

    public SetElevator(ElevatorHeights height) {
        this.elevator = Robot.ELEVATOR;
        requires(elevator);
        this.toHeight = height;
        this.emergencyStopTimer = new Timer();
    }

    @Override
    protected void initialize() {
        elevator.setElevator(toHeight);
    }

    @Override
    protected void execute() {
        if (elevator.getVelocity() < Constants.Elevator.MIN_UNCHECKED_VELOCITY
                && elevator.getCurrent() > Constants.Elevator.UNSAFE_IDLE_CURRENT_DRAW) {
            if (!runningCheck) {
                emergencyStopTimer.reset();
                emergencyStopTimer.start();
                runningCheck = true;
            } else if (emergencyStopTimer.hasPeriodPassed(Constants.Elevator.UNSAFE_TIME_BEFORE_STOP)) {
                elevator.elevatorHold();
                emergencyStop = true;
                System.out.println("STOPPED ELEVATOR DUE TO EXCESSIVE IDLE ACTIVITY");
            }
        } else if (runningCheck) {
            runningCheck = false;
            emergencyStop = false;
            emergencyStopTimer.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return emergencyStop;
        // return elevator.isMPFinished() || emergencyStop;
    }
}