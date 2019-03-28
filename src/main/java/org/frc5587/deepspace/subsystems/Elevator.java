package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
    private static TalonSRX elevatorTalon;
    private static TalonSRX elevatorSlave;
    private static DigitalInput elevatorLimitSwitch;
    private static HashMap<ElevatorHeights, Double> elevatorHeights;
    private static HashMap<CargoHeights, Double> cargoHeights;

    public Elevator() {
        elevatorTalon = new TalonSRX(RobotMap.Elevator.ELEVATOR_MASTER);
        elevatorSlave = new TalonSRX(RobotMap.Elevator.ELEVATOR_SLAVE);
        elevatorSlave.setInverted(true);

        elevatorLimitSwitch = new DigitalInput(RobotMap.Elevator.ELEVATOR_LIMIT_SWITCH);
        elevatorHeights = new HashMap<>();

        elevatorHeights.put(ElevatorHeights.BOTTOM_LEVEL, Constants.Elevator.bottomTicks);
        elevatorHeights.put(ElevatorHeights.MIDDLE_LEVEL, Constants.Elevator.middleTicks);
        elevatorHeights.put(ElevatorHeights.TOP_LEVEL, Constants.Elevator.topTicks);

        cargoHeights = new HashMap<>();

        cargoHeights.put(CargoHeights.CARGO_SHIP, Constants.Elevator.cargoShip);
        cargoHeights.put(CargoHeights.BOTTOM_CARGO, Constants.Elevator.bottomCargo);
        cargoHeights.put(CargoHeights.MIDDLE_CARGO, Constants.Elevator.middleCargo);
        cargoHeights.put(CargoHeights.TOP_CARGO, Constants.Elevator.topCargo);

        elevatorSlave.follow(elevatorTalon);

        configureTalon();
    }

    public static enum ElevatorHeights {
        BOTTOM_LEVEL, MIDDLE_LEVEL, TOP_LEVEL;

    }

    public static enum CargoHeights {
        CARGO_SHIP, BOTTOM_CARGO, MIDDLE_CARGO, TOP_CARGO;
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

    public double getTicks(CargoHeights height) {
        return cargoHeights.get(height);
    }

    public void setElevator(double height) {
        elevatorTalon.set(ControlMode.MotionMagic, height);
    }

    public void setElevator(ElevatorHeights height) {
        setElevator(getTicks(height));
    }

    public void setElevator(CargoHeights height) {
        setElevator(getTicks(height));
    }

    public boolean isMPFinished() {
        return elevatorTalon.isMotionProfileFinished();
    }

    public void elevatorHold() {
        elevatorTalon.set(ControlMode.PercentOutput, Constants.Elevator.HOLD_VOLTAGE);
    }

    public double getCurrent() {
        return elevatorTalon.getOutputCurrent();
    }

    public void elevatorMove(double yInput) {
        yInput = yInput > 0 ? yInput : 0.5 * yInput;    
        // var scaledValue = MathHelper.limit(yInput + Constants.Elevator.HOLD_VOLTAGE, -1, 1);
        var scaledValue = yInput;
        // System.out.println(scaledValue);
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

    public boolean limitSwitchValue() {
        // Limit switches are pulled high
        return !elevatorLimitSwitch.get();
    }

    public void sendDebugData() {
        SmartDashboard.putNumber("Ele Pos", getPosition());
        SmartDashboard.putBoolean("Ele Switch", limitSwitchValue());
        SmartDashboard.putNumber("Ele Current", getCurrent());
        SmartDashboard.putNumber("Ele Out %", elevatorTalon.getMotorOutputPercent());
        SmartDashboard.putBoolean("Ele MP Done", elevatorTalon.isMotionProfileFinished());
    }

    public void startRefresh() {
        SmartDashboard.putNumber("Ele P", 0.0);
        SmartDashboard.putNumber("Ele I", 0.0);
        SmartDashboard.putNumber("Ele D", 0.0);

        SmartDashboard.putNumber("Ele Set", 0.0);
    }

    public void refreshPID() {
        elevatorTalon.config_kP(0, SmartDashboard.getNumber("Ele P", 0.0), 20);
        elevatorTalon.config_kI(0, SmartDashboard.getNumber("Ele I", 0.0), 20);
        elevatorTalon.config_kD(0, SmartDashboard.getNumber("Ele D", 0.0), 20);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
