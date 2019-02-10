package org.frc5587.deepspace;

import org.frc5587.lib.control.DeadbandXboxController;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static Joystick joy = new Joystick(1);
    public static DeadbandXboxController xb = new DeadbandXboxController(0, .2);
}
