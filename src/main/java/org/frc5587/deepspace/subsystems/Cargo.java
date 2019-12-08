package org.frc5587.deepspace.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.frc5587.deepspace.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for the cargo intake.
 * 
 * <p>The cargo intake subsytem consists of one motor for picking up or spitting
 * out balls.
 */
public class Cargo extends Subsystem {
  private TalonSRX cargoTalon;

  public Cargo() {
    cargoTalon = new TalonSRX(RobotMap.Cargo.CARGO_MOTOR);
  }

  /**
   * Set the cargo intake's motors to output percentage indicated by the percent
   * parameter.
   * 
   * @param percent percent to set the motors to
   */
  public void cargoSet(double percent) {
    cargoTalon.set(ControlMode.PercentOutput, percent);
  }

  @Override
  protected void initDefaultCommand() {

  }
}
