package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;
import org.frc5587.lib.MathHelper;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {
    private static TalonSRX elevatorTalon;
    private static TalonSRX elevatorSlave;
    private static HashMap<ElevatorHeights, Double> elevatorHeights;

    public Elevator() {
        elevatorTalon = new TalonSRX(RobotMap.Elevator.ELEVATOR_MASTER);
        elevatorSlave = new TalonSRX(RobotMap.Elevator.ELEVATOR_SLAVE);
        elevatorHeights = new HashMap<>();

        elevatorHeights.put(ElevatorHeights.BOTTOM_LEVEL, Constants.Elevator.bottomTicks);
        elevatorHeights.put(ElevatorHeights.MIDDLE_LEVEL, Constants.Elevator.middleTicks);
        elevatorHeights.put(ElevatorHeights.TOP_LEVEL, Constants.Elevator.topTicks);

        elevatorSlave.follow(elevatorTalon);

        configureTalon();
    }

    public static enum ElevatorHeights {
        BOTTOM_LEVEL, MIDDLE_LEVEL, TOP_LEVEL;

    }

    private static void configureTalon() {
        elevatorTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.Elevator.kPIDLoopIdx, Constants.Elevator.kTimeoutMs);
        elevatorTalon.setSensorPhase(false);
        elevatorTalon.setInverted(false);
        elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.Elevator.kTimeoutMs);
        elevatorTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10,
                Constants.Elevator.kTimeoutMs);

        elevatorTalon.configNominalOutputForward(Constants.Elevator.minPercentOut, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configNominalOutputReverse(-Constants.Elevator.minPercentOut, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configPeakOutputForward(Constants.Elevator.maxPercentFw, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configPeakOutputReverse(-Constants.Elevator.maxPercentBw, Constants.Elevator.kTimeoutMs);

        elevatorTalon.configPeakCurrentLimit(40, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configPeakCurrentDuration(200, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configContinuousCurrentLimit(35, Constants.Elevator.kTimeoutMs);

        elevatorTalon.selectProfileSlot(Constants.Elevator.kSlotIdx, Constants.Elevator.kPIDLoopIdx);
        elevatorTalon.config_kF(0, Constants.Elevator.PIDs[3], Constants.Elevator.kTimeoutMs);
        elevatorTalon.config_kP(0, Constants.Elevator.PIDs[0], Constants.Elevator.kTimeoutMs);
        elevatorTalon.config_kI(0, Constants.Elevator.PIDs[1], Constants.Elevator.kTimeoutMs);
        elevatorTalon.config_kD(0, Constants.Elevator.PIDs[2], Constants.Elevator.kTimeoutMs);

        elevatorTalon.configMotionCruiseVelocity(Constants.Elevator.maxVelocity, Constants.Elevator.kTimeoutMs);
        elevatorTalon.configMotionAcceleration(Constants.Elevator.maxAcceleration, Constants.Elevator.kTimeoutMs);

        elevatorTalon.configVoltageCompSaturation(Constants.Elevator.vCompSaturation, Constants.Elevator.kTimeoutMs);
    }

    public double getTicks(ElevatorHeights height) {
        return elevatorHeights.get(height);
    }

    public void setElevator(double height) {
        elevatorTalon.set(ControlMode.MotionMagic, height);
    }

    public void elevatorHold() {
        elevatorTalon.set(ControlMode.PercentOutput, Constants.Elevator.HOLD_VOLAGE);
    }

    public void elevatorMove(double yInput) {
        yInput = yInput > 0 ? yInput : 0.5 * yInput;    
        var scaledValue = MathHelper.limit(yInput + Constants.Elevator.HOLD_VOLAGE, -1, 1);
        elevatorTalon.set(ControlMode.PercentOutput, scaledValue);
    }

    public void resetEncoder() {
        elevatorTalon.setSelectedSensorPosition(0);
    }

    public int getPosition() {
        return elevatorTalon.getSelectedSensorPosition();
    }

    public int getVelocity() {
        return elevatorTalon.getSelectedSensorVelocity();
    }

    public double inchesToTicks(double inches) {
        return inches * Constants.Elevator.STU_PER_INCH;
    }

    public double ticksToInches(double ticks) {
        return ticks / Constants.Elevator.STU_PER_INCH;
    }

    @Override
    protected void initDefaultCommand() {

    }
}