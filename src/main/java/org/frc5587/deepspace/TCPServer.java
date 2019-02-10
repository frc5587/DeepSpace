package org.frc5587.deepspace;

import java.net.*;

import org.frc5587.deepspace.commands.Routines;
import org.frc5587.deepspace.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;

import java.io.*;

public class TCPServer extends Thread {
    public static final boolean PATHFINDER_ALT = true;
    public static final int IN_BYTE_COUNT = 4;
    private ServerSocket serverSocket;
    private Socket currentServer;
    private InputStream inStream;
    private BufferedReader inReader;

    private Command routineCommand;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        // serverSocket.setSoTimeout(10000);
        routineCommand = null;
    }

    public void run() {
        try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            currentServer = serverSocket.accept();
            System.out.println("Just connected to " + currentServer.getRemoteSocketAddress());
            inStream = currentServer.getInputStream();
            inReader = new BufferedReader(new InputStreamReader(inStream));

            while (true) {
                if (currentServer.getInputStream().available() > 1) {
                    var messageParts = inReader.readLine().split(":");

                    var time = Double.parseDouble(messageParts[0]);
                    var angle = Double.parseDouble(messageParts[1]);
                    System.out.println(angle);

                    if (PATHFINDER_ALT) {
                        // Here, angle is degrees between midpoint of tap and centre of vision of camera
                        // Grab additional elements of the message for pathfinder
                        var distanceX = Double.parseDouble(messageParts[2]);
                        var distanceY = Double.parseDouble(messageParts[3]);

                        var infoMessage = new DistToGoalMessage(time, angle, distanceX, distanceY);

                        routineCommand = new Routines.PathfinderHatchPickup(Drive.SLOW_PATHGEN, infoMessage);
                    } else {
                        // Angle is how far off the robot is from being perpendicular to the target
                        // Run with PID loop on angle
                        var infoMessage = new AngleFromGoalMessage(time, angle);
                        routineCommand = new Routines.PIDHatchPickup(infoMessage);
                    }

                    routineCommand.start();

                    // Robot.TURRET.setPositionDeg(Robot.DRIVETRAIN.getHeading() - angle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            currentServer.close();
        } catch (IOException e) {
            System.out.println("Server already closed");
            return;
        }
    }

    public class AngleFromGoalMessage {
        public final double time, angleToPerpendicular;

        public AngleFromGoalMessage(double time, double angleToPerpendicular) {
            this.time = time;
            this.angleToPerpendicular = angleToPerpendicular;
        }
    }

    public class DistToGoalMessage {
        public final double time, angleToCenterRad, distanceX, distanceY;

        public DistToGoalMessage(double time, double angleToCenterDeg, double distanceX, double distanceY) {
            this.time = time;
            this.angleToCenterRad = Pathfinder.d2r(angleToCenterDeg);
            this.distanceX = distanceX;
            this.distanceY = distanceY;
        }
    }
}