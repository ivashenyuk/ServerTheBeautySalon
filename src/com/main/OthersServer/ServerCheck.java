package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.MyData.DataBase;
import com.main.MyData.DataWorker;
import com.main.Server;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerCheck implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;
    private ArrayList<DataWorker> dataWorkers = new ArrayList<DataWorker>();

    public ServerCheck() {
        System.out.println("\tServerCheck is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortCheck())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerCheck.this, serverSocket.accept());
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("Added connection. " + tcpConnection.socket.getInetAddress());
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String data) {
        String[] dataCheck = new Gson().fromJson(data, String[].class);
        System.out.println(data);
        DataBase dataBase = Server.getDataBase();

        if (dataBase.CheckDate(dataCheck[3])) {
            dataBase.addOrder(dataCheck[0], dataCheck[1], dataCheck[2], dataCheck[3], dataCheck[4], dataCheck[5]);
            tcpConnection.Send("true");
        } else {
            tcpConnection.Send("false");
        }
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
