package org.frc5587.deepspace;

import java.net.*;

import org.frc5587.deepspace.commands.routines.RoutineMode;

import java.io.*;

public class TCPServer extends Thread {
    public static final RoutineMode MODE = RoutineMode.PID;
    public static final int IN_BYTE_COUNT = 4;
    private ServerSocket serverSocket;
    private Socket currentServer;
    private InputStream inStream;
    private BufferedReader inReader;

    public TCPServer(int port) throws IOException {
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
                // Check if there are any messages to be recieved
                if (currentServer.getInputStream().available() > -1) {
                    // Fetch message
                    var messageParts = "1:180".split(":");
                    // var messageParts = inReader.readLine().split(":");

                    ProcessTCPData.update(messageParts);
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
}
