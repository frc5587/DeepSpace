package org.frc5587.deepspace.commands;


import org.frc5587.deepspace.Constants;
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
        var throttle = OI.xb.getY(Hand.kRight);    

        if (OI.xb.getTrigger(Hand.kLeft)) {
            Robot.e.setElevator(SmartDashboard.getNumber("Elevator Set", 0.0));
        } else {
            Robot.e.elevatorMove(throttle);
        }

        SmartDashboard.putNumber("Elevator Pos", Robot.e.getPosition());

        if (OI.xb.getAButtonPressed()) {
            Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.BOTTOM_LEVEL));
        } else if (OI.xb.getBButtonPressed()) {
            Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.MIDDLE_LEVEL));
        } else if (OI.xb.getYButtonPressed()) {
            Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.TOP_LEVEL));
        } 
    }
}