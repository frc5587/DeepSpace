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

/**
 * Subsystem for the elevator.
 * 
 * <p>
 * The elevator moves the hatch and cargo intakes vertically on a carriage and
 * consists of a single SparkMax, an encoder, and a limit switch that is placed
 * at the bottom of the elevator to indicate that the intake carriage has hit
 * the bottom.
 */
public class Elevator extends Subsystem {
    private HashMap<HatchHeights, Double> elevatorHeights;
    private HashMap<CargoHeights, Double> cargoHeights;
    private CANSparkMax elevatorSpark;
    private DigitalInput elevatorLimitSwitch;
    private CANPIDController spark_pidController;
    private CANEncoder spark_encoder;

    public Elevator() {
        elevatorLimitSwitch = new DigitalInput(RobotMap.Elevator.ELEVATOR_LIMIT_SWITCH);

        elevatorHeights = new HashMap<>();
        elevatorHeights.put(HatchHeights.BOTTOM_LEVEL, Constants.Elevator.BOTTOM_HATCH);
        elevatorHeights.put(HatchHeights.MIDDLE_LEVEL, Constants.Elevator.MIDDLE_HATCH);
        elevatorHeights.put(HatchHeights.TOP_LEVEL, Constants.Elevator.TOP_HATCH);

        cargoHeights = new HashMap<>();
        cargoHeights.put(CargoHeights.CARGO_SHIP, Constants.Elevator.CARGO_SHIP);
        cargoHeights.put(CargoHeights.BOTTOM_CARGO, Constants.Elevator.BOTTOM_CARGO);
        cargoHeights.put(CargoHeights.MIDDLE_CARGO, Constants.Elevator.MIDDLE_CARGO);
        cargoHeights.put(CargoHeights.TOP_CARGO, Constants.Elevator.TOP_CARGO);

        elevatorSpark = new CANSparkMax(RobotMap.Elevator.ELEVATOR_SPARK, MotorType.kBrushless);
        spark_pidController = elevatorSpark.getPIDController();
        spark_encoder = elevatorSpark.getEncoder();

        configureSpark();
    }

    /**
     * Configure the SparkMax and its encoder that are used for the elevator
     */
    private void configureSpark() {
        elevatorSpark.setInverted(false);

        elevatorSpark.setSoftLimit(SoftLimitDirection.kForward, Constants.Elevator.MAX_PERCENT_FW);
        elevatorSpark.setSoftLimit(SoftLimitDirection.kReverse, -Constants.Elevator.MAX_PERCENT_BW);
        spark_pidController.setOutputRange(-Constants.Elevator.MAX_PERCENT_BW, Constants.Elevator.MAX_PERCENT_FW);

        elevatorSpark.setSmartCurrentLimit(40, 35);

        var pids = Constants.Elevator.ELEVATOR_PID;
        spark_pidController.setP(pids.kP, Constants.TIMEOUT_MS);
        spark_pidController.setI(pids.kI, Constants.TIMEOUT_MS);
        spark_pidController.setD(pids.kD, Constants.TIMEOUT_MS);
        spark_pidController.setFF(pids.kF, Constants.TIMEOUT_MS);

        spark_pidController.setSmartMotionMaxVelocity(Constants.Elevator.MAX_VELOCITY, Constants.TIMEOUT_MS);
        spark_pidController.setSmartMotionMaxAccel(Constants.Elevator.MAX_ACCELERATION, Constants.TIMEOUT_MS);
        spark_pidController.setSmartMotionMinOutputVelocity(Constants.Elevator.MIN_VELOCITY,
                Constants.Elevator.SMART_MOTION_SLOT);
        spark_pidController.setSmartMotionAllowedClosedLoopError(Constants.Elevator.ALLOWED_ERR,
                Constants.Elevator.SMART_MOTION_SLOT);

        elevatorSpark.enableVoltageCompensation(Constants.V_COMP_SATURATION);
    }

    /**
     * Get the encoder ticks value associated with the provided height
     * 
     * @param height the height to convert to encoder ticks
     * @return the correponding encoder ticks value
     * @see #inchesToTicks(double)
     */
    private double toTicks(HatchHeights height) {
        return elevatorHeights.get(height);
    }

    /**
     * Get the encoder ticks value associated with the provided height
     * 
     * @param height the height to convert to encoder ticks
     * @return the correponding encoder ticks value
     * @see #inchesToTicks(double)
     */
    private double toTicks(CargoHeights height) {
        return cargoHeights.get(height);
    }

    /**
     * Set the elevator to the desired position.
     * 
     * @param height the height to set the position to
     */
    private void setElevator(double height) {
        spark_pidController.setReference(height, ControlType.kSmartMotion);
    }

    /**
     * Set the elevator to the desired position.
     * 
     * @param height the height to set the elevator to
     */
    public void setElevator(HatchHeights height) {
        setElevator(toTicks(height));
    }

    /**
     * Set the elevator to the desired position.
     * 
     * @param height the height to set the elevator to
     */
    public void setElevator(CargoHeights height) {
        setElevator(toTicks(height));
    }

    /**
     * Stop any movement and hold the elevator at the current position.
     * 
     * <p>
     * This method sets the elevator's output to the percent indicated by
     * {@link Constants.Elevator#HOLD_PERCENT}, although a better tuned PID loop
     * should be used in the future.
     */
    public void elevatorHold() {
        elevatorSpark.set(Constants.Elevator.HOLD_PERCENT);
    }

    /**
     * Set the elevator motor to the desired percent output.
     * 
     * @param percent the percent to set the motor to
     */
    public void elevatorMove(double percent) {
        // percent = percent > 0 ? percent : 0.5 * percent; <- makes it go down slower
        var scaledValue = MathHelper.limit(percent + Constants.Elevator.HOLD_PERCENT, -1, 1);
        elevatorSpark.set(scaledValue);
    }

    /**
     * Reset the elevator's encoder to zero.
     */
    public void resetEncoder() {
        spark_encoder.setPosition(0);
    }

    /**
     * Get the current position of the elevator motor (in rotation with the
     * SparkMax).
     * 
     * @return the current position of the elevator motor
     */
    public double getPosition() {
        return spark_encoder.getPosition();
    }

    /**
     * Get the current velocity of the motor (in RPM with the SparkMax).
     * 
     * @return the velocity of the motor
     */
    public double getVelocity() {
        return spark_encoder.getVelocity();
    }

    /**
     * Get the current being supplied to the elevator motor by its motor controller.
     * 
     * @return the current being supplied to the motor
     */
    public double getCurrent() {
        return elevatorSpark.getOutputCurrent();
    }

    /**
     * Convert inches to motor encoder ticks based on the conversion in
     * {@link Constants.Elevator#TICKS}.
     * 
     * @param inches the inches measurement to convert
     * @return the equivalent motor encoder ticks
     * 
     * @see #ticksToInches(double)
     */
    public static double inchesToTicks(double inches) {
        return inches * Constants.Elevator.TICKS_PER_INCH;
    }

    /**
     * Convert motor encoder ticks to inches based on the conversion in
     * {@link Constants.Elevator#TICKS}.
     * 
     * @param ticks the encoder tick value to convert
     * @return the equivalent inches measurement
     * 
     * @see #inchesToTicks(double)
     */
    public static double ticksToInches(double ticks) {
        return ticks / Constants.Elevator.TICKS_PER_INCH;
    }

    /**
     * Get whether the limit switch at the bottom of the elevator is currently
     * pressed.
     * 
     * @return whether the bottom limit switch is pressed
     */
    public boolean bottomSwitchValue() {
        // Limit switches are pulled high
        return !elevatorLimitSwitch.get();
    }

    /**
     * Log subsystem-specific debug data to SmartDashboard.
     */
    public void logDebugData() {
        SmartDashboard.putNumber("Ele Pos", getPosition());
        SmartDashboard.putBoolean("Ele Switch", bottomSwitchValue());
        SmartDashboard.putNumber("Ele Current", getCurrent());
        SmartDashboard.putNumber("Ele Out %", elevatorSpark.getAppliedOutput());
    }

    /**
     * Prepare for refreshing the PID constants and target position of this
     * subsystem by posting cells to SmartDashboard.
     */
    public void startRefresh() {
        SmartDashboard.putNumber("Ele P", 0.0);
        SmartDashboard.putNumber("Ele I", 0.0);
        SmartDashboard.putNumber("Ele D", 0.0);
        SmartDashboard.putNumber("Ele Set", 0.0);
    }

    /**
     * Update the PID constants and target position of this subsystem with data from
     * SmartDashboard
     *
     * <p>
     * Note that the {@link #startRefresh()} method should be called before first
     * calling this method for the first time.
     */
    public void refreshPID() {
        spark_pidController.setP(SmartDashboard.getNumber("Ele P", 0.0), 20);
        spark_pidController.setI(SmartDashboard.getNumber("Ele I", 0.0), 20);
        spark_pidController.setD(SmartDashboard.getNumber("Ele D", 0.0), 20);
        spark_pidController.setReference(SmartDashboard.getNumber("Ele Set", 0.0), ControlType.kPosition);
    }

    @Override
    protected void initDefaultCommand() {

    }

    /**
     * The levels of the various hatch ports on the field
     */
    public static enum HatchHeights {
        /**
         * The position of the lowermost hatch port on the rocket and the hatch ports on
         * the cargo ship.
         */
        BOTTOM_LEVEL,

        /**
         * The position of the middle hatch port on the rocket.
         */
        MIDDLE_LEVEL,

        /**
         * The position of the top hatch port on the rocket.
         */
        TOP_LEVEL
    }

    /**
     * The levels of the various cargo ports on the field.
     */
    public static enum CargoHeights {
        /**
         * The position to deposit cargo in the cargo ship.
         */
        CARGO_SHIP,

        /**
         * The position of the lowermost cargo port on the rocket.
         */
        BOTTOM_CARGO,

        /**
         * The position of the middle cargo port on the rocket.
         */
        MIDDLE_CARGO,

        /**
         * The position of the top cargo port on the rocket.
         */
        TOP_CARGO
    }
}
