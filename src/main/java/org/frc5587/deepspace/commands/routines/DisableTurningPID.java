package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * DisableTurningPID
 */
public class DisableTurningPID extends InstantCommand {
    public DisableTurningPID() {

    }

    @Override
    protected void initialize() {
        Robot.tcpServer.stopPiping();
        Robot.DRIVETRAIN.vbusLR(0, 0);
    }
}