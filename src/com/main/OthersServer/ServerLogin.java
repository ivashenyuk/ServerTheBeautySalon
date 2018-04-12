package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.Data.DataUser;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerLogin implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private TCPConnection tcpConnection = null;
    private Thread thread = null;
    private DataUser dataUser = new DataUser();

    public ServerLogin() {
        System.out.println("\tServerLogin is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortLogin())) {
                    System.out.println(serverSocket.getLocalSocketAddress());
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
//        for (TCPConnection tcp : connections) {
//            tcp.Send("s");
//        }
        System.out.println("Added connection.");
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String data) {
        try {
            String[] dataUPE = new Gson().fromJson(data, String[].class);
            if (dataUPE[0].equals(dataUser.getEmailUser()) && dataUPE[1].equals(dataUser.getPassword())) {
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

    private static void SendMsgAllClient(String enemy, int enemyOrUser) {

    }

    private static void SendMsgAllClientStep() {

    }


}
