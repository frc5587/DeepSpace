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
    private PrintWriter out;

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
                String[] messageParts = null;
                while (inStream.available() > 0) {
                    var fromClient = recieve();
                    messageParts = fromClient.split(":");
                }

                // Process only when something has been recieved
                if (messageParts != null) {
                    ProcessTCPData.update(messageParts);
                }
            }
        } catch (SocketException | NullPointerException e) {
            // If socket has disconnected, try to reconnect
            System.out.println(e.getStackTrace().toString());
            reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String recieve() throws IOException, NullPointerException {
        var fromClient = inReader.readLine();
        System.out.println("Recieved: " + fromClient);

        if (fromClient == null) {
            throw new NullPointerException("Recieved null from the client");
        }

        return fromClient;
    }

    private void send(String message) {
        System.out.println("Send: " + message);
        out.println(message);
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
        out = new PrintWriter(new DataOutputStream(currentServer.getOutputStream()), true);
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
