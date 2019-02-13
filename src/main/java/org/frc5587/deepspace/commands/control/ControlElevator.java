package org.frc5587.deepspace.commands.control;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControlElevator extends InstantCommand {
    public ControlElevator() {

    }

    @Override
    protected void execute() {
        var throttle = -OI.xb.getY(Hand.kRight);    
        if (!OI.xb.getTrigger(Hand.kLeft)) {
            if (OI.xb.getAButtonPressed()) {
                Robot.ELEVATOR.setElevator(Robot.ELEVATOR.getTicks(ElevatorHeights.BOTTOM_LEVEL));
            } else if (OI.xb.getBButtonPressed()) {
                Robot.ELEVATOR.setElevator(Robot.ELEVATOR.getTicks(ElevatorHeights.MIDDLE_LEVEL));
            } else if (OI.xb.getYButtonPressed()) {
                Robot.ELEVATOR.setElevator(Robot.ELEVATOR.getTicks(ElevatorHeights.TOP_LEVEL));
            }
        } else {
            Robot.ELEVATOR.elevatorMove(throttle);
        }

        SmartDashboard.putNumber("Elevator Pos", Robot.ELEVATOR.getPosition());
    }
}