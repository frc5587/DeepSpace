package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class LimitResetElevator extends Command {

    public LimitResetElevator() {
 
    }
  
    @Override
    protected void execute() {
        if(Robot.ELEVATOR.limitSwitchValue()) {
            Robot.ELEVATOR.resetEncoder();
            Robot.ELEVATOR.elevatorHold();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}