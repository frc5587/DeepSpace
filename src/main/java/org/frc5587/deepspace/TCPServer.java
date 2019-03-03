package org.frc5587.deepspace;

import java.net.*;
import java.util.concurrent.TimeUnit;

import org.frc5587.deepspace.commands.routines.RoutineMode;

import java.io.*;

public class TCPServer extends Thread {
    public static final RoutineMode MODE = RoutineMode.PID;
    public static final int IN_BYTE_COUNT = 4;
    private final int bindPort;
    private ServerSocket serverSocket;
    private Socket currentServer;
    private InputStream inStream;
    private BufferedReader inReader;

    public TCPServer(int port) throws IOException {
        // Create a port that will wait forever and then connect to port
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(0);
        this.bindPort = port;
    }

    public void run() {
        try {
            connect();

            while (true) {
                // Check if there are any messages to be recieved
                if (currentServer.getInputStream().available() > -1) {
                    // Fetch message
                    // var messageParts = "1:180".split(":");
                    var messageParts = inReader.readLine().split(":");

                    ProcessTCPData.update(messageParts);
                }
            }
        } catch (SocketException | NullPointerException e) {
            // If socket has disconnected, try to reconnect
            System.out.println(e.getStackTrace().toString());
            reconnect();
            // reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reconnect() {
        while (true) {
            try {
                // serverSocket = new ServerSocket(bindPort);
                serverSocket.close();
                serverSocket = new ServerSocket(bindPort);
                break;
            } catch (IOException ioException) {
                try {
                    ioException.printStackTrace();
                    System.out.println("Could not open port. Waiting one second and trying again...");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Done sleeping; reconnecting");
                    continue;
                } catch (InterruptedException interruptedException) {
                    System.out.println("TCP Server interrupted while trying to reconnect");
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        this.run();
    }

    private void connect() throws IOException {
        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        currentServer = serverSocket.accept();
        System.out.println("Just connected to " + currentServer.getRemoteSocketAddress());
        inStream = currentServer.getInputStream();
        inReader = new BufferedReader(new InputStreamReader(inStream));
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
