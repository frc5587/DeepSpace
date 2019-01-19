package org.frc5587.deepspace;

// File Name GreetingServer.java
import java.net.*;
import java.io.*;

public class TCPTestServer extends Thread {
    private ServerSocket serverSocket;
    private DataInputStream inStream;

    public TCPTestServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
    }

    public void run() {
        try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());

            inStream = new DataInputStream(new BufferedInputStream(server.getInputStream()));

            while (true) {
                var line = inStream.readDouble();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public static void main(String[] args) {
    //     int port = Integer.parseInt(args[0]);
    //     try {
    //         Thread t = new TCPTestServer(port);
    //         t.start();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}