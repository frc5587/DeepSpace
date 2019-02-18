package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lift extends Subsystem {
    private TalonSRX liftLeader, liftFollower;

    public Lift() {
        liftLeader = new TalonSRX(RobotMap.Lift.LIFT_MOTOR_ONE);
        liftFollower = new TalonSRX(RobotMap.Lift.LIFT_MOTOR_TWO);

        liftFollower.setInverted(true);
        liftFollower.follow(liftLeader);
    }

    public void liftDown() {
        liftLeader.set(ControlMode.PercentOutput, 1);
    }

    public void liftUp() {
        liftLeader.set(ControlMode.PercentOutput, -1);
    }

    public void liftStop() {
        liftLeader.set(ControlMode.PercentOutput, 0);
    }

    public void setLift(double value) {
        liftLeader.set(ControlMode.PercentOutput, value);
    }

    public void sendDebugData() {
        SmartDashboard.putNumber("Lift Current", liftLeader.getOutputCurrent());
        SmartDashboard.putNumber("Lift Out %", liftLeader.getMotorOutputPercent());
    }

    @Override
    protected void initDefaultCommand() {

    }
}
