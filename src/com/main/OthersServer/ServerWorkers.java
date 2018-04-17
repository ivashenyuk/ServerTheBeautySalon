package com.main.OthersServer;

import com.main.MyData.DataBase;
import com.main.MyData.DataWorker;
import com.main.Server;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerWorkers implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;
    private ArrayList<DataWorker> dataWorkers = new ArrayList<DataWorker>();

    public ServerWorkers() {
        DataBase dataBase = Server.getDataBase();
        dataWorkers = dataBase.GetWorkers();
        System.out.println("\tServerWorkers is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortGetWorkers())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerWorkers.this, serverSocket.accept());
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
        //System.out.println( new Gson().toJson(DataWorker.encodeToString( dataWorkers.get(0).getImgWorker(), "jpg")));

        for (int i = 0; i < dataWorkers.size(); i++) {
            tcpConnection.Send(
                    dataWorkers.get(i).getNameWorker(), dataWorkers.get(i).getKingOfServiceWorker(),
                    dataWorkers.get(i).getPriceWorker(), dataWorkers.get(i).getIdButtonWorker(),
                    dataWorkers.get(i).getImgWorker());
        }

        System.out.println("Added connection. " + tcpConnection.socket.getInetAddress());
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
