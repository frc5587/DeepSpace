package org.frc5587.deepspace;

import java.net.*;

import org.frc5587.deepspace.commands.routines.RoutineMode;

import jaci.pathfinder.Pathfinder;

import java.io.*;

public class TCPServer extends Thread {
    public static final RoutineMode MODE = RoutineMode.PID;
    public static final int IN_BYTE_COUNT = 4;
    private ServerSocket serverSocket;
    private Socket currentServer;
    private InputStream inStream;
    private BufferedReader inReader;

    private boolean pipeInput;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        // serverSocket.setSoTimeout(10000);
        pipeInput = false;
    }

    public void run() {
        try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            currentServer = serverSocket.accept();
            System.out.println("Just connected to " + currentServer.getRemoteSocketAddress());
            inStream = currentServer.getInputStream();
            inReader = new BufferedReader(new InputStreamReader(inStream));

            while (true) {
                // Check if there are any messages to be recieved
                if (currentServer.getInputStream().available() > -1) {
                    // Fetch message
                    var messageParts = "1:180".split(":");
                    // var messageParts = inReader.readLine().split(":");

                    var time = Double.parseDouble(messageParts[0]);
                    var angle = Double.parseDouble(messageParts[1]);
                    System.out.println(angle);

                    // If meant to control, check whether things have started
                    if (pipeInput) {
                        if (MODE == RoutineMode.PATHFINDER) {
                            // Here, angle is degrees between midpoint of tape and centre of vision of
                            // camera
                            // Grab additional elements of the message for pathfinder
                            // var distanceX = Double.parseDouble(messageParts[2]);
                            // var distanceY = Double.parseDouble(messageParts[3]);
                            // var infoMessage = new PathfinderGoalMessage(time, angle, distanceX, distanceY);

                            // TODO: Implement for Pathfinder
                            throw new UnsupportedOperationException("Pathfinder mode not implemented yet");
                        } else if (MODE == RoutineMode.PID) {
                            // If command running and it is PID loop, update it
                            Robot.DRIVETRAIN.setTurnPID(angle);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            currentServer.close();
        } catch (IOException | NullPointerException e) {
            System.out.println("Server already closed");
            return;
        }
    }

    public void startPiping() {
        pipeInput = true;
        Robot.DRIVETRAIN.enableTurnPID(true);
    }

    public void stopPiping() {
        if (MODE == RoutineMode.PID) {
            System.out.println("Disabling");
            pipeInput = false;
            Robot.DRIVETRAIN.enableTurnPID(false);
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