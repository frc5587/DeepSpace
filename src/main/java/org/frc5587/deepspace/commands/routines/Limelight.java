package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class Limelight extends Command {

    // current horizontal angle
    private NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");

    public Limelight() {

    }

    @Override
    protected void initialize() {
        Robot.DRIVETRAIN.enableTurnPID(true);
    }

    @Override
    protected void execute() {
        var currentHeading = Robot.DRIVETRAIN.getHeading(180.0);
        double desiredAngle = currentHeading - tx.getDouble(0);
        Robot.DRIVETRAIN.setTurnPID(desiredAngle);
    }

    @Override
    protected void end() {
        Robot.DRIVETRAIN.enableTurnPID(false);
        Robot.DRIVETRAIN.vbusLR(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}