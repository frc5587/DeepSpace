package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlElevator extends Command {
    private static Elevator elevator;
    private static boolean manualControl;
    
    // private static double maxVelocity = Double.MIN_VALUE;

    public ControlElevator() {
        elevator = Robot.ELEVATOR;
        requires(elevator);

        manualControl = false;
    }

    @Override
    protected void execute() {

        // Code for finding max velocity of elevator motors
        // var vel = elevator.getVelocity();
        // if (vel > maxVelocity) {
        //     maxVelocity = vel;
        //     System.out.println(vel);
        // }

        var throttle = -OI.xb.getY(Hand.kLeft);    
        if (!OI.xb.getTrigger(Hand.kLeft)) {
            if (manualControl) {
                // B/c coming from manual, override last input
                elevator.elevatorHold();
                manualControl = false;
            }

            if (OI.xb.getAButtonPressed()) {
                elevator.setElevator(ElevatorHeights.BOTTOM_LEVEL);
            } else if (OI.xb.getBButtonPressed()) {
                elevator.setElevator(ElevatorHeights.MIDDLE_LEVEL);
            } else if (OI.xb.getYButtonPressed()) {
                elevator.setElevator(ElevatorHeights.TOP_LEVEL);
            }
        } else {
            // elevator.setElevator(SmartDashboard.getNumber("Ele Set", 0));
            elevator.elevatorMove(throttle);
            manualControl = true;
        }

        SmartDashboard.putNumber("Elevator Pos", elevator.getPosition());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}