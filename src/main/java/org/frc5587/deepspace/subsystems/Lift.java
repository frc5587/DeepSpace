package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Lift extends Subsystem {
    private TalonSRX liftLeader,liftFollower;

    public Lift() {
        liftLeader = new TalonSRX(RobotMap.Drive.leftMaster);
        liftFollower = new TalonSRX(RobotMap.Drive.rightMaster);
    }

    public void liftDown() {
        liftLeader.set(ControlMode.PercentOutput, 1);
        liftFollower.set(ControlMode.PercentOutput, 1);
        }

    public void liftUp() {
        liftLeader.set(ControlMode.PercentOutput, -1);
        liftFollower.set(ControlMode.PercentOutput, -1);
    }

    public void liftStop() {
        liftLeader.set(ControlMode.PercentOutput, 0);
        liftFollower.set(ControlMode.PercentOutput, 0);
    }

    public void setLift(double value) {
        liftLeader.set(ControlMode.PercentOutput, value);
        liftFollower.set(ControlMode.PercentOutput, value);
    }

    // public int sensorPosition() {
    //     return liftMotor.getSelectedSensorPosition();
    // }

    @Override
    protected void initDefaultCommand() {
        
    }
}
