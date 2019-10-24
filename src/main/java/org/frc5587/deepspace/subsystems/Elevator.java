package org.frc5587.deepspace.subsystems;

import java.util.HashMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.RobotMap;
import org.frc5587.lib.MathHelper;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {
    private static CANSparkMax elevatorSpark;
    private static DigitalInput elevatorLimitSwitch;
    private static HashMap<ElevatorHeights, Double> elevatorHeights;
    private static HashMap<CargoHeights, Double> cargoHeights;
    private static CANPIDController spark_pidController;
    private static CANEncoder spark_encoder;

    public Elevator() {
        elevatorSpark = new CANSparkMax(RobotMap.Elevator.ELEVATOR_SPARK, MotorType.kBrushless);

        elevatorLimitSwitch = new DigitalInput(RobotMap.Elevator.ELEVATOR_LIMIT_SWITCH);
        elevatorHeights = new HashMap<>();

        elevatorHeights.put(ElevatorHeights.BOTTOM_LEVEL, Constants.Elevator.BOTTOM_TICKS);
        elevatorHeights.put(ElevatorHeights.MIDDLE_LEVEL, Constants.Elevator.MIDDLE_TICKS);
        elevatorHeights.put(ElevatorHeights.TOP_LEVEL, Constants.Elevator.TOP_TICKS);

        cargoHeights = new HashMap<>();

        cargoHeights.put(CargoHeights.CARGO_SHIP, Constants.Elevator.CARGO_SHIP);
        cargoHeights.put(CargoHeights.BOTTOM_CARGO, Constants.Elevator.BOTTOM_CARGO);
        cargoHeights.put(CargoHeights.MIDDLE_CARGO, Constants.Elevator.MIDDLE_CARGO);
        cargoHeights.put(CargoHeights.TOP_CARGO, Constants.Elevator.TOP_CARGO);

        spark_pidController = elevatorSpark.getPIDController();
        spark_encoder = elevatorSpark.getEncoder();

        configureSpark();
    }

    public static enum ElevatorHeights {
        BOTTOM_LEVEL, MIDDLE_LEVEL, TOP_LEVEL;

    }

    public static enum CargoHeights {
        CARGO_SHIP, BOTTOM_CARGO, MIDDLE_CARGO, TOP_CARGO;
    }

    private static void configureSpark() {
        elevatorSpark.setInverted(false);

        elevatorSpark.setSoftLimit(SoftLimitDirection.kForward, Constants.Elevator.MAX_PERCENT_FW);
        elevatorSpark.setSoftLimit(SoftLimitDirection.kReverse, -Constants.Elevator.MAX_PERCENT_BW);
        spark_pidController.setOutputRange(Constants.Elevator.MIN_PERCENT_OUT, Constants.Elevator.MAX_PERCENT_FW);

        elevatorSpark.setSmartCurrentLimit(40, 35);

        spark_pidController.setP(Constants.Elevator.PIDs[0], Constants.Elevator.K_TIMEOUT_MS);
        spark_pidController.setI(Constants.Elevator.PIDs[1], Constants.Elevator.K_TIMEOUT_MS);
        spark_pidController.setD(Constants.Elevator.PIDs[2], Constants.Elevator.K_TIMEOUT_MS);
        spark_pidController.setFF(Constants.Elevator.PIDs[3], Constants.Elevator.K_TIMEOUT_MS);

        spark_pidController.setSmartMotionMaxVelocity(Constants.Elevator.MAX_VELOCITY, Constants.Elevator.K_TIMEOUT_MS);
        spark_pidController.setSmartMotionMaxAccel(Constants.Elevator.MAX_ACCELERATION, Constants.Elevator.K_TIMEOUT_MS);
        spark_pidController.setSmartMotionMinOutputVelocity(Constants.Elevator.MIN_VELOCITY, Constants.Elevator.SMART_MOTION_SLOT);
        spark_pidController.setSmartMotionAllowedClosedLoopError(Constants.Elevator.ALLOWED_ERR, Constants.Elevator.SMART_MOTION_SLOT);

        elevatorSpark.enableVoltageCompensation(Constants.Elevator.V_COMP_SATURATION);

    }

    public double getTicks(ElevatorHeights height) {
        return elevatorHeights.get(height);
    }

    public double getTicks(CargoHeights height) {
        return cargoHeights.get(height);
    }

    public void setElevator(double height) {
        spark_pidController.setReference(height, ControlType.kVelocity);
    }

    public void setElevator(ElevatorHeights height) {
    setElevator(getTicks(height));
    }

    public void setElevator(CargoHeights height) {
    setElevator(getTicks(height));
    }

    public void elevatorHold() {
        elevatorSpark.set(Constants.Elevator.HOLD_VOLTAGE);
    }

    public double getCurrent() {
        return elevatorSpark.getOutputCurrent();
    }

    public void elevatorMove(double yInput) {
        // yInput = yInput > 0 ? yInput : 0.5 * yInput; <- makes it go down slower
        var scaledValue = MathHelper.limit(yInput + Constants.Elevator.HOLD_VOLTAGE, -1, 1);
        elevatorSpark.set(scaledValue);
    }

    public void resetEncoder() {
        spark_encoder.setPosition(0);
    }

    public double getPosition() {
        return spark_encoder.getPosition();
    }

    public double getVelocity() {
        return spark_encoder.getVelocity();
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
        SmartDashboard.putNumber("Ele Out %", elevatorSpark.getAppliedOutput());
    }

    public void startRefresh() {
        SmartDashboard.putNumber("Ele P", 0.0);
        SmartDashboard.putNumber("Ele I", 0.0);
        SmartDashboard.putNumber("Ele D", 0.0);

        SmartDashboard.putNumber("Ele Set", 0.0);
    }

    public void refreshPID() {
        spark_pidController.setP(SmartDashboard.getNumber("Ele I", 0.0), 20);
        spark_pidController.setI(SmartDashboard.getNumber("Ele I", 0.0), 20);
        spark_pidController.setD(SmartDashboard.getNumber("Ele D", 0.0), 20);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
