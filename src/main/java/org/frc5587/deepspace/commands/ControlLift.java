package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class ControlLift extends Command {
    private int height;
    private int upperValue =  6;
    private double allowance = .5;

    public ControlLift() {

    }
    //TODO: change to acurate values
    @Override
    protected void execute() {
        height = Robot.LIFT.sensorPosition();
        if(OI.xb.getStickButtonPressed(Hand.kRight)) {
            if(height < upperValue) {
                Robot.LIFT.liftDown();
            }
            else if(upperValue - allowance <= height && height <= upperValue + allowance) {
                Robot.LIFT.liftStop();
            }
        }
        if(OI.xb.getStickButtonPressed(Hand.kLeft)) {
            if(height <= upperValue) {
                Robot.LIFT.liftUp();
            }
            else if(0 - allowance <= height && height <= 0 + allowance) {
                Robot.LIFT.liftStop();
            }
        }
    }
    @Override
    protected boolean isFinished() {
        return false;
    }
}