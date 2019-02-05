package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    private static TalonSRX elevatorTalon;
    private static VictorSPX elevatorVictor;
    private static HashMap<ElevatorHeights, Double> elevatorHeights;

    public Elevator() {
        elevatorTalon = new TalonSRX(RobotMap.Elevator.ELEVATOR_MASTER);
        elevatorVictor = new VictorSPX(RobotMap.Elevator.ELEVATOR_SLAVE);
        elevatorHeights = new HashMap<>();

        elevatorHeights.put(ElevatorHeights.BOTTOM_LEVEL, 0.0);
        elevatorHeights.put(ElevatorHeights.MIDDLE_LEVEL, 1.0);
        elevatorHeights.put(ElevatorHeights.TOP_LEVEL, 2.0);

        elevatorVictor.follow(elevatorTalon);
    }


    public static enum ElevatorHeights {
        BOTTOM_LEVEL, MIDDLE_LEVEL, TOP_LEVEL;

    }

    public void setElevator(ElevatorHeights setpoint) {
        elevatorTalon.set(ControlMode.Position, elevatorHeights.get(setpoint));
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