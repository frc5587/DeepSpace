package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;
import org.frc5587.deepspace.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turret
 */
public class Turret extends Subsystem {
    private TalonSRX turretTalon;

    static final double kToleranceDegrees = 2.0f;

    public Turret() {
        // super("Turret", Constants.Turret.TURRET_FPID[0],
        // Constants.Turret.TURRET_FPID[1], Constants.Turret.TURRET_FPID[2],
        // Constants.Turret.TURRET_FPID[3]);

        turretTalon = new TalonSRX(RobotMap.Turret.TURRET_TALON);
        turretTalon.setNeutralMode(NeutralMode.Brake);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        turretTalon.setSensorPhase(true);
        turretTalon.configVoltageCompSaturation(Constants.kVCompSaturation, Constants.kTimeoutMs);
        turretTalon.enableVoltageCompensation(true);
        // turretTalon.configMotionCruiseVelocity(Constants.Turret.MAX_CRUISE_VELOCITY,
        // Constants.kTimeoutMs);

        fillPidSlot(Constants.Turret.PID_SLOT);

        // Configuration for a Wpilib PID loop instead of running one on the TalonSRX
        // setInputRange(-180.0f, 180.0f);
        // setOutputRange(-1.0, 1.0);
        // setAbsoluteTolerance(kToleranceDegrees);
        // getPIDController().setContinuous(true);
        // disable();
        // turretTalon.config_kF(Constants.Turret.PID_SLOT,
        // Constants.Turret.TURRET_FPID[0]);
        // turretTalon.config_kP(Constants.Turret.PID_SLOT,
        // Constants.Turret.TURRET_FPID[1]);
        // turretTalon.config_kI(Constants.Turret.PID_SLOT,
        // Constants.Turret.TURRET_FPID[2]);
        // turretTalon.config_kD(Constants.Turret.PID_SLOT,
        // Constants.Turret.TURRET_FPID[3]);
    }

    private void fillPidSlot(int slotIdx) {
        turretTalon.config_kF(slotIdx, Constants.Turret.TURRET_FPID[0]);
        turretTalon.config_kP(slotIdx, Constants.Turret.TURRET_FPID[1]);
        turretTalon.config_kI(slotIdx, Constants.Turret.TURRET_FPID[2]);
        turretTalon.config_kD(slotIdx, Constants.Turret.TURRET_FPID[3]);
    }

    public void setThrottle(double throttle) {
        turretTalon.set(ControlMode.PercentOutput, throttle);
    }

    public void setPositionNU(double posNativeUnits) {
        turretTalon.set(ControlMode.Position, posNativeUnits);
    }

    public void setPositionDeg(double posDegrees) {
        setPositionNU(degToTicks(posDegrees));
    }

    private double degToTicks(double degrees) {
        return degrees * Constants.Turret.NU_PER_DEGREE;
    }

    public void resetEncoders() {
        turretTalon.setSelectedSensorPosition(0, 0, Constants.Drive.kTimeoutMs);
    }

    public void postDebugData() {
        SmartDashboard.putNumber("Turret Pos", turretTalon.getSelectedSensorPosition());
        SmartDashboard.putNumber("Turret Vel", turretTalon.getSelectedSensorVelocity());
    }

    // @Override
    // protected double returnPIDInput() {
    // // return Robot.DRIVETRAIN.getHeading();
    // return 9.0;
    // }

    // @Override
    // protected void usePIDOutput(double output) {
    // setMotor(output);
    // }

    @Override
    protected void initDefaultCommand() {

    }
}