package org.frc5587.deepspace.commands;


import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlElevator extends Command {
    public ControlElevator() {

    }

    @Override
    protected void execute() {

        if(OI.xb.getY(Hand.kRight) != 0) {
            Robot.e.elevatorMove(OI.xb.getY());
        }
        else if(OI.xb.getY(Hand.kRight) == 0) {
            Robot.e.elevatorHold();
        }
        
    //    // pick a button (setpoints??)
    //     if (OI.xb.getAButton()) {
    //         Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.BOTTOM_LEVEL));
    //     } else if (OI.xb.getBButton()) {
    //         Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.MIDDLE_LEVEL));
    //     } else if (OI.xb.getYButton()) {
    //         Robot.e.setElevator(Robot.e.getTicks(ElevatorHeights.TOP_LEVEL));
    //     } 
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}