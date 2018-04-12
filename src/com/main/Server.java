package com.main;

import com.google.gson.Gson;
import com.main.OthersServer.ServerLogin;
import com.main.OthersServer.ServerSchedule;
import com.main.OthersServer.ServerWorkers;
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
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Added connection.");
        for (TCPConnection tcp : connections) {
            tcp.Send("getdata");
        }
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String data) {
        if (data.equals("login") && loginServer == null)
            loginServer = new ServerLogin();
        else if (data.equals("getworkers") && workersServer == null)
            workersServer = new ServerWorkers();
        else if (data.equals("getschedule") && scheduleServer == null);
            scheduleServer = new ServerSchedule();
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }

    private static void SendMsgAllClient(String enemy, int enemyOrUser) {

    }

    private static void SendMsgAllClientStep() {

    }


}
