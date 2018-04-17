package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.MyData.DataBase;
import com.main.Server;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerRestoration implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;

    public ServerRestoration() {
        System.out.println("\tServerRestoration is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortRestoration())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerRestoration.this, serverSocket.accept());
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
        DataBase dataBase = Server.getDataBase();
        if (dataCheck[1].equals("secret")) {
            String[] data1 = new Gson().fromJson(dataCheck[0], String[].class);

            if (!dataBase.CheckEmailAndSecretCode(data1[1], data1[0])) {
                tcpConnection.Send("true");
            }
        } else if (dataCheck[1].equals("change")) {
            dataBase.SetChagePassword(dataCheck[2], ServerLogin.dataUser);
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
