package org.frc5587.deepspace.commands.routines;

import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.TCPServer;
import org.frc5587.deepspace.subsystems.Elevator.ElevatorHeights;
import org.frc5587.lib.pathfinder.Pathgen;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Pickup
 */
public class HatchPickup extends CommandGroup {
    public HatchPickup() {
        addSequential(new SetElevator(ElevatorHeights.BOTTOM_LEVEL));
        addSequential(new UltrasonicThreshold(10));
        addSequential(new DisableTurningPID());
        addSequential(new SetHatch(Value.kReverse));
    }
    
    public HatchPickup(Pathgen trajPathgen, TCPServer.PathfinderGoalMessage infoMessage) {
        throw new UnsupportedOperationException("Processing with pathfinder is not supported yet");
        // var perpGoalTraj = trajPathgen.createTrajectory(new Waypoint(0, 0, 0),
        //         new Waypoint(infoMessage.distanceX, infoMessage.distanceY, infoMessage.angleToCenterRad));
        // addSequential(new GyroCompMPRunner(Robot.DRIVETRAIN, perpGoalTraj, trajPathgen,
        //         Constants.Drive.pathfinderPIDVALeft, Constants.Drive.pathfinderPIDVARight, Constants.Drive.gyrokP));
    }

    @Override
    protected void initialize() {
        Robot.tcpServer.startPiping();
    }

    @Override
    protected void end() {
        Robot.tcpServer.stopPiping();
    }

    @Override
    protected void interrupted() {
        end();
    }
}