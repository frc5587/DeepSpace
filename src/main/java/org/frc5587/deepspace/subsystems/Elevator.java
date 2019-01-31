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

    @Override
    protected void initDefaultCommand() {
        
    }
}