package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    private static TalonSRX elevatorTalon;
    private static VictorSPX elevatorVictor;

    public Elevator() {
        elevatorTalon = new TalonSRX(RobotMap.Elevator.ELEVATOR_MASTER);
        elevatorVictor = new VictorSPX(RobotMap.Elevator.ELEVATOR_SLAVE);
        elevatorVictor.follow(elevatorTalon);
    }

    public static enum ElevatorHeights {
        BOTTOM_LEVEL(0), MIDDLE_LEVEL(1), TOP_LEVEL(2);

        private double height;

        ElevatorHeights(double height) {
            this.height = height;
        }

        public double getHeight() {
            return height;
        }
    }

    public void setElevator(ElevatorHeights setpoint) {
        elevatorTalon.set(ControlMode.Position, setpoint.getHeight());
    }

    public void elevatorHold() {
        elevatorTalon.set(ControlMode.PercentOutput, Constants.holdVoltage);
    }

    public void elevatorMove(double yInput) {
        elevatorTalon.set(ControlMode.PercentOutput, yInput);
    }
   /* public void elevatorUp() {
        elevatorTalon.set(ControlMode.PercentOutput, 1);
        /*if(OI.joy.getThrottle() > 0) {
            elevatorTalon.set(ControlMode.PercentOutput, 1);
        }
        else if(OI.joy.getThrottle() < 0) {
            elevatorTalon.set(ControlMode.PercentOutput, -1);
        }
        else if(OI.joy.getThrottle() == 0) {
            elevatorHold();
        }

    public void elevatorDown() {
        elevatorTalon.set(ControlMode.PercentOutput, -1);
    } */

    @Override
    protected void initDefaultCommand() {
        
    }
}