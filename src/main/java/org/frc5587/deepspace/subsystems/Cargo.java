package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Cargo extends Subsystem {
  private TalonSRX cargoTalon;

  public Cargo() {
    cargoTalon = new TalonSRX(RobotMap.Cargo.CARGO_MOTOR);
  }

  public void cargoIn() {
    cargoTalon.set(ControlMode.PercentOutput, 1);
  }

  public void cargoOut() {
    cargoTalon.set(ControlMode.PercentOutput, -1);
  }

  public void cargoStop(){
    cargoTalon.neutralOutput();
  }

  @Override
  protected void initDefaultCommand() {

  }
}
