package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.MyData.DataBase;
import com.main.MyData.DataUser;
import com.main.Server;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerLogin implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private TCPConnection tcpConnection = null;
    private Thread thread = null;
    public static DataUser dataUser = new DataUser();

    public ServerLogin() {
        System.out.println("\tServerLogin is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortLogin())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        tcpConnection = new TCPConnection(ServerLogin.this, serverSocket.accept());
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
        try {
            String[] dataUPE = new Gson().fromJson(data, String[].class);
            DataBase dataBase = Server.getDataBase();
            if (!dataBase.CheckEmailAndPassword(dataUPE[0], dataUPE[1])) {
                tcpConnection.Send(new Gson().toJson(dataUser));
                tcpConnection.Send("true");
            }
            //tcpConnection.Disconnect();
        } catch (Exception e) {
            e.fillInStackTrace();
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
