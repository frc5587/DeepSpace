package org.frc5587.deepspace.commands.routines;

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
    private static final ScheduledExecutorService schedulerExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void initialize() {
        schedulerExecutorService.scheduleAtFixedRate(new LimelightWorker(), 0, 10, TimeUnit.MILLISECONDS);
        Robot.DRIVETRAIN.enableTurnPID(true);
    }

    @Override
    protected void end() {
        // May need to be changed to shutdownNow depending on behaviour
        schedulerExecutorService.shutdown();
        Robot.DRIVETRAIN.enableTurnPID(false);
        Robot.DRIVETRAIN.stop();
    }

    @Override
    protected boolean isFinished() {
        return schedulerExecutorService.isTerminated();
    }

    private static class LimelightWorker implements Runnable {
        @Override
        public void run() {
            var newError = tx.getDouble(0);
            var currentHeading = Robot.DRIVETRAIN.getHeading(180.0);
            double desiredAngle = currentHeading + newError;
            Robot.DRIVETRAIN.setTurnPID(desiredAngle);
        }
    }
}
