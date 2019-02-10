package org.frc5587.deepspace.commands;

import org.frc5587.deepspace.Constants;
import org.frc5587.deepspace.Robot;
import org.frc5587.deepspace.TCPServer;
import org.frc5587.deepspace.subsystems.Elevator;
import org.frc5587.lib.pathfinder.GyroCompMPRunner;
import org.frc5587.lib.pathfinder.Pathgen;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class Routines {
    public static class PathfinderHatchPickup extends CommandGroup {
        public PathfinderHatchPickup(Pathgen trajPathgen, TCPServer.DistToGoalMessage infoMessage) {
            var perpGoalTraj = trajPathgen.createTrajectory(new Waypoint(0, 0, 0),
                    new Waypoint(infoMessage.distanceX, infoMessage.distanceY, infoMessage.angleToCenterRad));

            addParallel(new ControlElevator());

            addSequential(new GyroCompMPRunner(Robot.DRIVETRAIN, perpGoalTraj, trajPathgen,
                    Constants.Drive.pathfinderPIDVALeft, Constants.Drive.pathfinderPIDVARight, Constants.Drive.gyrokP));
            // TODO: Use elevator to go to setpoint and release hatch

        }
    }

    public static class PIDHatchPickup extends CommandGroup {
        public PIDHatchPickup(TCPServer.AngleFromGoalMessage infoMessage) {
            // TODO: Implement processing for angle from perpendicular to goal
            throw new UnsupportedOperationException("Processing for solely angle messages is not supported yet");
        }
    }
}