package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.OI;
import org.frc5587.deepspace.commands.control.ArcadeDrive;
import org.frc5587.deepspace.commands.control.Limelight;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Manager manages the switching between the Limelight's automatic target
 * tracking and manual control with a joystick.
 * 
 * <p>
 * While the button with ID 12 on the joystick is held down, automatic tracking
 * with the Limelight is used, otherwise the joystick is used to manually
 * control the drivetrain through {@link ArcadeDrive}.
 * 
 * @see Limelight
 * @see ArcadeDrive
 */
public class Manager extends Command {
    private Command limelightCommand;

    public Manager() {

    }

    @Override
    protected void execute() {
        if (limelightCommand == null) {
            if (OI.joy.getRawButton(12)) {
                // Start using the Limelight if button 12 is pressed and no routine is running
                limelightCommand = new Limelight();
                limelightCommand.start();
            } else {
                // Default to joystick control
                new ArcadeDrive().start();
            }
        } else if (OI.joy.getRawButtonReleased(12)) {
            // Stop using the Limelight as soon as button 12 is released
            limelightCommand.cancel();
            limelightCommand = null;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // Stop the Limelight's control of the drivetrain if it is running
        if (limelightCommand != null) {
            limelightCommand.cancel();
        }
    }
}
