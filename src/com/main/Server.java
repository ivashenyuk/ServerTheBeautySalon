package com.main;

import com.main.MyData.DataBase;
import com.main.OthersServer.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private ServerRestoration serverRestoration;
    private ServerCheck serverCheck;
    private ServerLogin loginServer = null;
    private ServerWorkers workersServer;
    private ServerSchedule scheduleServer;
    private ServerProfit serverProfit;
    private ServerRegistration registrationServer;
    private ServerOrders serverOrders;
    private static DataBase dataBase;

    public Server() {
        this.dataBase = new DataBase();

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
        if (serverCheck == null)
            serverCheck = new ServerCheck();
        if (serverOrders == null)
            serverOrders = new ServerOrders();
        if (serverRestoration == null)
            serverRestoration = new ServerRestoration();
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

    public static DataBase getDataBase() {
        return dataBase;
    }
}
