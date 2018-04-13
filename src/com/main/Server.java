package com.main;

import com.google.gson.Gson;
import com.main.OthersServer.*;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private ServerLogin loginServer = null;
    private ServerWorkers workersServer;
    private ServerSchedule scheduleServer;
    private ServerProfit serverProfit;
    private ServerRegistration registrationServer;

    public Server() {
        System.out.println("Server is running...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortConnection())) {
                    System.out.println(serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(Server.this, serverSocket.accept());
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (loginServer == null)
            loginServer = new ServerLogin();
        if (registrationServer == null)
            registrationServer = new ServerRegistration();
        if (workersServer == null)
            workersServer = new ServerWorkers();
        if (scheduleServer == null)
            scheduleServer = new ServerSchedule();
        if (serverProfit == null)
            serverProfit = new ServerProfit();
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Added connection. " + tcpConnection.socket.getInetAddress());
        tcpConnection.Send("getdata");
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String data) {
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }
}
