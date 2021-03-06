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

public class ServerRegistration implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;
    private ArrayList<DataWorker> dataWorkers = new ArrayList<DataWorker>();

    public ServerRegistration() {
        System.out.println("\tServerRegistration is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortRegistration())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerRegistration.this, serverSocket.accept());
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
        System.out.println(data);
        String[] dataReg = new Gson().fromJson(data, String[].class);
        DataBase dataBase = Server.getDataBase();
        if (dataBase.GetUserForEmail(dataReg[1])) {
            dataBase.addUser(dataReg[0], dataReg[1], dataReg[2], dataReg[3], "user");

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
