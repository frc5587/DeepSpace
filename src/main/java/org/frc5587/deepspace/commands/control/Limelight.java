package org.frc5587.deepspace.commands.control;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class Limelight extends Command {
    // current horizontal angle
    private static final NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
    private static final NetworkTableEntry ledMode = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode");
    private static boolean lockLEDs = false;

    private ScheduledExecutorService scheduledExecutorService;

    public Limelight() {
        requires(Robot.DRIVETRAIN);
    }

    @Override
    protected void initialize() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(Limelight::updatePID, 0, 10, TimeUnit.MILLISECONDS);
        Robot.DRIVETRAIN.enableTurnPID(true);
        lockLEDs = true;
        ledMode.setNumber(3);
    }

    @Override
    protected void end() {
        // May need to be changed to shutdownNow depending on behaviour
        scheduledExecutorService.shutdown();
        Robot.DRIVETRAIN.enableTurnPID(false);
        Robot.DRIVETRAIN.stop();
        lockLEDs = false;
        ledMode.setNumber(1);
    }

    @Override
    protected boolean isFinished() {
        return scheduledExecutorService.isTerminated();
    }

    public static void disableLEDs() {
        if (!lockLEDs) {
            ledMode.setNumber(1);
        }
    }

    private static void updatePID() {
        var newError = tx.getDouble(0);
        var currentHeading = Robot.DRIVETRAIN.getHeading(180.0);
        double desiredAngle = currentHeading + newError;
        Robot.DRIVETRAIN.setTurnPID(desiredAngle);
    }
}
