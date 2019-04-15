package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class Limelight extends Command {
    // current horizontal angle
    private static final NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");

    private LimelightWorker limelightWorker;

    public Limelight() {
        this.limelightWorker = new LimelightWorker();
    }

    @Override
    protected void initialize() {
        limelightWorker.start();
        Robot.DRIVETRAIN.enableTurnPID(true);
    }

    @Override
    protected void execute() {
    }

    @Override
    protected void end() {
        limelightWorker.interrupt();
        Robot.DRIVETRAIN.enableTurnPID(false);
        Robot.DRIVETRAIN.vbusLR(0, 0);
    }

    @Override
    protected boolean isFinished() {
        return limelightWorker.isInterrupted();
    }

    private static class LimelightWorker extends Thread {
        public LimelightWorker() {
        }

        @Override
        public void run() {
            while (!interrupted()) {
                var newError = tx.getDouble(0);
                var currentHeading = Robot.DRIVETRAIN.getHeading(180.0);
                double desiredAngle = currentHeading + newError;
                Robot.DRIVETRAIN.setTurnPID(desiredAngle);
            }
        }
    }
}
