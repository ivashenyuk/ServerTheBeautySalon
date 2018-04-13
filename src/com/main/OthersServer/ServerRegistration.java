package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.Data.DataWorker;
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
        if (!GetUserForEmail(dataReg[1])) {
            //TODO: Write in DB!
            ServerLogin.dataUser.password = dataReg[2];
            ServerLogin.dataUser.emailUser = dataReg[1];
            ServerLogin.dataUser.nameUser = dataReg[0];
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

    private boolean GetUserForEmail(String email) {
        return false;
    }
}
