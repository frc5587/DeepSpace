package org.frc5587.deepspace.commands.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.subsystems.Drive;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for using the Limelight to update the turn PID loop in the
 * drivetrain.
 * 
 * <p>
 * Note that, while running, the command uses a separate thread to run the
 * update every 10ms as opposed to the typical 20ms frequency of Commands.
 * 
 * @see Drive
 */
public class Limelight extends Command {
    // Current horizontal angle
    private static final NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
    // Used to toggle the LEDs
    private static final NetworkTableEntry ledMode = NetworkTableInstance.getDefault().getTable("limelight")
            .getEntry("ledMode");
    // Whether the limelight is currently in use
    private static boolean trackingTarget = false;

    // Thread to repeatedly use the Limelight's data at a set rate higher than 20ms
    private ScheduledExecutorService scheduledExecutorService;
    private Drive drivetrain;

    public Limelight() {
        drivetrain = Robot.DRIVETRAIN;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        // Run the update method every 10ms
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(Limelight::updatePID, 0, 10, TimeUnit.MILLISECONDS);

        // Enable turn PID, the LEDs, and lock the LEDs so they cannot be toggled
        Robot.DRIVETRAIN.enableTurnPID(true);
        trackingTarget = true;
        enableLEDs(true);
    }

    @Override
    protected void end() {
        // May need to be changed to shutdownNow depending on behaviour
        scheduledExecutorService.shutdown();

        // Disable turnPID now that Limelight control is over
        Robot.DRIVETRAIN.enableTurnPID(false);
        Robot.DRIVETRAIN.stop();

        // Turn off LEDs and allow them to be toggled
        trackingTarget = false;
        ledMode.setNumber(1);
    }

    @Override
    protected boolean isFinished() {
        return scheduledExecutorService.isTerminated();
    }

    /**
     * Checks that the Limelight LEDs are not currently being used to track a target
     * and sets them to the desired state if they are not.
     * 
     * <p>
     * Note that the LEDs must be set on for the Limelight to actually track and
     * detect a target.
     * 
     * @param enabled whether the LEDs should be on or off
     */
    public static void enableLEDs(boolean enabled) {
        if (!trackingTarget) {
            if (enabled) {
                ledMode.setNumber(3);
            } else {
                ledMode.setNumber(1);
            }
        }
    }

    /**
     * Updates the drivetrain's turn PID loop with the angle from the target as
     * determined by the Limelight.
     * 
     * <p>
     * Note that this method <b>does not</b> check that the turn PID is enabled or
     * that the Limelight's LEDs are currently on, even though this method will not
     * be able to do anything if these are not both set on.
     * 
     * @see #enableLEDs(boolean)
     * @see Drive#enableTurnPID(boolean)
     */
    private static void updatePID() {
        var newError = tx.getDouble(0);
        var currentHeading = Robot.DRIVETRAIN.getHeading(180.0);
        double desiredAngle = currentHeading + newError;
        Robot.DRIVETRAIN.setTurnPID(desiredAngle);
    }
}
