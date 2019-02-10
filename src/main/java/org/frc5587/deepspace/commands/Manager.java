package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Manager extends Command {
    private CommandGroup currentRoutine;
    public double setpoint;

    public Manager() {
        currentRoutine = null;
    }

    @Override
    protected void execute() {
        if (currentRoutine == null || currentRoutine.isCompleted()) {
            if (OI.xb.getTrigger(Hand.kRight)) {
                if (OI.xb.getAButtonPressed()) {
                    setpoint = Constants.Elevator.bottomTicks;
                } else if (OI.xb.getBButtonPressed()) {
                    setpoint = Constants.Elevator.middleTicks;
                } else if (OI.xb.getYButtonPressed()) {
                    setpoint = Constants.Elevator.topTicks;
                } else {
                    return;
                }
            } else {
                new ControlElevator().start();

                new ArcadeDrive().start();
            }
        } 
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}