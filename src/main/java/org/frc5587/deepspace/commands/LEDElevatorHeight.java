package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Elevator;
import org.frc5587.deepspace.subsystems.LEDControl;

import edu.wpi.first.wpilibj.command.Command;

public class LEDElevatorHeight extends Command {
    private Elevator Elevator;
    private LEDControl LEDControl;

    public LEDElevatorHeight() {
        requires(LEDControl);
        this.Elevator = Robot.e;
        this.LEDControl = Robot.LED_CONTROL;
        this.setRunWhenDisabled(true);
    }

    @Override
    protected void execute() {
        LEDControl.sendHeight((int) Elevator.ticksToInches());

    }
    @Override
    protected boolean isFinished() {
        return false;
    }
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}