package org.frc5587.deepspace;

import org.frc5587.deepspace.commands.routines.RoutineMode;

import edu.wpi.first.wpilibj.Timer;
import jaci.pathfinder.Pathfinder;

/**
 * ProcessData
 */
public class ProcessTCPData {
    private static boolean pipeInput = false;

    public static void update(String[] messageParts) {
        var time = Double.parseDouble(messageParts[0]);

        if (messageParts.length <= 1) {
            // Just a time packet for synchronisation purposes
            var systemTime = Timer.getFPGATimestamp();
            Robot.DRIVETRAIN.setVisionTimeDelta(systemTime - time);
        } else {
            var angleError = Double.parseDouble(messageParts[1]);
            System.out.println(angleError);

            // If meant to control, check whether things have started
            if (pipeInput) {
                switch (TCPServer.MODE) {
                case PATHFINDER:
                    // Here, angle is degrees between midpoint of tape and centre of vision of
                    // camera
                    // Grab additional elements of the message for pathfinder
                    // var distanceX = Double.parseDouble(messageParts[2]);
                    // var distanceY = Double.parseDouble(messageParts[3]);
                    // var infoMessage = new PathfinderGoalMessage(time, angle, distanceX,
                    // distanceY);

                    // TODO: Implement for Pathfinder

                    throw new UnsupportedOperationException("Pathfinder mode not implemented yet");
                    // break;
                case PID:
                    // If command running and it is PID loop, update it
                    // Start by using lag compensation
                    var captureAngle = Robot.DRIVETRAIN.getAngleAtClosestTime(time);
                    var desiredAngle = captureAngle + angleError;

                    // Now update with corrected value
                    Robot.DRIVETRAIN.setTurnPID(desiredAngle);
                    break;
                }
            }
        }
    }

    public static void startPiping() {
        pipeInput = true;
        Robot.DRIVETRAIN.enableTurnPID(true);
    }

    public static void stopPiping() {
        if (TCPServer.MODE == RoutineMode.PID) {
            System.out.println("Disabling");
            pipeInput = false;
            Robot.DRIVETRAIN.enableTurnPID(false);
            Robot.DRIVETRAIN.stop();
        }
    }

    public class PathfinderGoalMessage {
        public final double time, angleToCenterRad, distanceX, distanceY;

        public PathfinderGoalMessage(double time, double angleToCenterDeg, double distanceX, double distanceY) {
            this.time = time;
            this.angleToCenterRad = Pathfinder.d2r(angleToCenterDeg);
            this.distanceX = distanceX;
            this.distanceY = distanceY;
        }
    }
}
