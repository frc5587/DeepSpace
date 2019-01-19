package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;
import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Turret
 */
public class Turret extends PIDSubsystem {
    private TalonSRX turretTalon;

    static final double kToleranceDegrees = 2.0f;

    public Turret() {
        super("Turret", Constants.Turret.turretFPID[0], Constants.Turret.turretFPID[1], Constants.Turret.turretFPID[2],
                Constants.Turret.turretFPID[3]);

        turretTalon = new TalonSRX(RobotMap.Turret.TURRET_TALON);
        turretTalon.setNeutralMode(NeutralMode.Brake);
        turretTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        setInputRange(-180.0f, 180.0f);
        setOutputRange(-1.0, 1.0);
        setAbsoluteTolerance(kToleranceDegrees);
        getPIDController().setContinuous(true);

        disable();

        // turretTalon.config_kF(Constants.Turret.PID_SLOT,
        // Constants.Turret.turretFPID[0]);
        // turretTalon.config_kP(Constants.Turret.PID_SLOT,
        // Constants.Turret.turretFPID[1]);
        // turretTalon.config_kI(Constants.Turret.PID_SLOT,
        // Constants.Turret.turretFPID[2]);
        // turretTalon.config_kD(Constants.Turret.PID_SLOT,
        // Constants.Turret.turretFPID[3]);
    }

    public void setMotor(double throttle) {
        turretTalon.set(ControlMode.PercentOutput, throttle);
    }

    @Override
    protected double returnPIDInput() {
        // return Robot.DRIVETRAIN.getHeading();
        return 9.0;
    }

    @Override
    protected void usePIDOutput(double output) {
        setMotor(output);
    }

    @Override
    protected void initDefaultCommand() {

    }
}