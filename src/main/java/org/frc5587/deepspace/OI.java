package org.frc5587.deepspace;

import org.frc5587.lib.control.DeadbandXboxController;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
    public static Joystick joy = new Joystick(1);
    public static DeadbandXboxController xb = new DeadbandXboxController(0, .2);
}
