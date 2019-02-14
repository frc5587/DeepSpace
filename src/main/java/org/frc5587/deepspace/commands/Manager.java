package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.ProcessTCPData;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.commands.control.ArcadeDrive;
import org.frc5587.deepspace.commands.control.ControlElevator;
import org.frc5587.deepspace.commands.control.ControlHatch;
import org.frc5587.deepspace.commands.routines.HatchDropoff;
import org.frc5587.deepspace.commands.routines.HatchPickup;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

public class Manager extends Command {
    private ElevatorHeights setpoint;
    private Command currentRoutine;

    public Manager() {

    }

    @Override
    protected void initialize() {
        Robot.HATCH.grab();
        Robot.HATCH.stow();
        ProcessTCPData.stopPiping();
    }

    @Override
    protected void execute() {
        if (currentRoutine == null) {
            if (OI.xb.getStickButton(Hand.kLeft)) {
                if (OI.xb.getAButtonPressed()) {
                    setpoint = ElevatorHeights.BOTTOM_LEVEL;
                } else if (OI.xb.getBButtonPressed()) {
                    setpoint = ElevatorHeights.MIDDLE_LEVEL;
                } else if (OI.xb.getYButtonPressed()) {
                    setpoint = ElevatorHeights.TOP_LEVEL;
                } else {
                    System.out.println("No input given to start hatch dropoff");
                    return;
                }

                System.out.println("Starting deposit routine");
                currentRoutine = new HatchDropoff(setpoint);
            } else if (OI.xb.getStickButton(Hand.kRight)) {
                currentRoutine = new HatchPickup();
            } else {
                new ControlElevator().start();
                new ControlHatch().start();
                new ArcadeDrive().start();
                return;
            }

            currentRoutine.start();
        } else if (OI.xb.getStickButtonReleased(Hand.kLeft) || OI.xb.getStickButtonReleased(Hand.kRight)) {
            currentRoutine.cancel();
            currentRoutine = null;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}