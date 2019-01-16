package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;
import org.frc5587.deepspace.Constants;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Turret
 */
public class Turret extends Subsystem {
    private TalonSRX turretTalon;

    public Turret() {
        turretTalon = new TalonSRX(RobotMap.Turret.TURRET_TALON);

        turretTalon.setNeutralMode(NeutralMode.Brake);

        turretTalon.config_kF(Constants.Turret.PID_SLOT, Constants.Turret.turretFPID[0]);
        turretTalon.config_kP(Constants.Turret.PID_SLOT, Constants.Turret.turretFPID[1]);
        turretTalon.config_kI(Constants.Turret.PID_SLOT, Constants.Turret.turretFPID[2]);
        turretTalon.config_kD(Constants.Turret.PID_SLOT, Constants.Turret.turretFPID[3]);
    }

    @Override
    protected void initDefaultCommand() {
        
    }
}