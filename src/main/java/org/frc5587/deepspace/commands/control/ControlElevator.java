package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator;
import org.frc5587.deepspace.subsystems.Elevator.CargoHeights;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * ControlElevator controls the position of the elevator through the Elevator
 * subsystem with the Xbox controller.
 * 
 * <p>
 * The command checks that the left trigger is pressed to use either manual
 * control (Xbox's left joystick) or setpoint control (Xbox's buttons) to
 * control the elevator. Setpoint control will use the cargo setpoints if the
 * left trigger is pressed, otherwise it will default to hatch setpoints.
 * Regardless, the setpoints are bound to X, Y, and A.
 * 
 * @see Elevator
 */
public class ControlElevator extends Command {
    private static Elevator elevator;
    private static boolean manualControl;

    // private static double maxVelocity = Double.MIN_VALUE;

    public ControlElevator() {
        elevator = Robot.ELEVATOR;
        requires(elevator);

        // Ensure system knows it's starting in setpoint mode
        manualControl = false;
    }

    @Override
    protected void execute() {
        // Code for finding max velocity of elevator motors
        // var vel = elevator.getVelocity();
        // if (vel > maxVelocity) {
        // maxVelocity = vel;
        // System.out.println(vel);
        // }

        if (!OI.xb.getTrigger(Hand.kLeft)) {
            if (manualControl) {
                // B/c coming from manual, override last input
                elevator.elevatorHold();
                manualControl = false;
            }

            if (!OI.xb.getTrigger(Hand.kRight)) {
                // Default to hatch setpoints
                if (OI.xb.getAButtonPressed()) {
                    elevator.setElevator(ElevatorHeights.BOTTOM_LEVEL);
                } else if (OI.xb.getBButtonPressed()) {
                    elevator.setElevator(ElevatorHeights.MIDDLE_LEVEL);
                } else if (OI.xb.getYButtonPressed()) {
                    elevator.setElevator(ElevatorHeights.TOP_LEVEL);
                }
            } else {
                // Use cargo setpoints if the trigger is pressed
                if (OI.xb.getAButtonPressed()) {
                    elevator.setElevator(CargoHeights.BOTTOM_CARGO);
                } else if (OI.xb.getBButtonPressed()) {
                    elevator.setElevator(CargoHeights.MIDDLE_CARGO);
                } else if (OI.xb.getYButtonPressed()) {
                    elevator.setElevator(CargoHeights.TOP_CARGO);
                } else if (OI.xb.getXButtonPressed()) {
                    elevator.setElevator(CargoHeights.CARGO_SHIP);
                }
            }

        } else {
            // Use manual control if the left trigger is held
            var throttle = -OI.xb.getY(Hand.kLeft);
            elevator.elevatorMove(throttle);
            manualControl = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}