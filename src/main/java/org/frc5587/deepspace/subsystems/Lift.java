package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
    private TalonSRX liftMotor;

    public Lift() {
        liftMotor = new TalonSRX(RobotMap.Lift.LIFT_MOTOR);
    }

    public void liftDown() {
        liftMotor.set(ControlMode.PercentOutput, 1);
    }

    public void liftUp() {
        liftMotor.set(ControlMode.PercentOutput, -1);
    }

    public void liftStop() {
        liftMotor.set(ControlMode.PercentOutput, 0);
    }

    public int sensorPosition() {
        return liftMotor.getSelectedSensorPosition();
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}
