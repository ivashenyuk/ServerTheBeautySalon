package com.main.OthersServer;

import com.main.Data.DataWorker;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.util.ArrayList;

public class ServerWorkers implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private TCPConnection tcpConnection = null;
    private Thread thread = null;
    private String[] dataUser;
    private ArrayList<DataWorker> dataWorkers = new ArrayList<DataWorker>();

    public ServerWorkers() {
        GetWorkers();
        System.out.println("\tServerWorkers is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortGetWorkers())) {
                    System.out.println(serverSocket.getLocalSocketAddress());
                    while (true) {
                        tcpConnection = new TCPConnection(ServerWorkers.this, serverSocket.accept());
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

        for (int i = 0; i < dataWorkers.size(); i++) {
            tcpConnection.Send(
                    dataWorkers.get(i).getNameWorker(), dataWorkers.get(i).getKingOfServiceWorker(),
                    dataWorkers.get(i).getPriceWorker(), dataWorkers.get(i).getIdButtonWorker(),
                    dataWorkers.get(i).getImgWorker());
        }

        System.out.println("Added connection.");
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String data) {
        try {

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

    private void GetWorkers() {
        dataWorkers.add(new DataWorker("Кондратюк Андрій", "Стоматолог", "200$",1, null));
        dataWorkers.add(new DataWorker());
        dataWorkers.add(new DataWorker());
        dataWorkers.add(new DataWorker());
        dataWorkers.add(new DataWorker());
    }

}
