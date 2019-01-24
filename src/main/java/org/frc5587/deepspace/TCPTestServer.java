package org.frc5587.deepspace;

import java.net.*;
import java.io.*;

public class TCPTestServer extends Thread {
    public static final int IN_BYTE_COUNT = 4;
    private ServerSocket serverSocket;
    private Socket currentServer;
    private InputStream inStream;
    private BufferedReader inReader;

    public TCPTestServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        // serverSocket.setSoTimeout(10000);
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
                    var message = inReader.readLine().split(":");

                    // var time = message[0]; // Commented out b/c not used
                    var angleFromCenterDeg = Double.parseDouble(message[1]);
                    System.out.println(angleFromCenterDeg);

                    Robot.TURRET.setPositionDeg(Robot.DRIVETRAIN.getHeading() - angleFromCenterDeg);
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
}