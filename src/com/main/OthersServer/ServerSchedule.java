package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.Data.DataScheduleLine;
import com.main.Data.DataWorker;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerSchedule implements TCPConnectionListener {
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;
    private final ArrayList<DataScheduleLine> listSchedule = new ArrayList<DataScheduleLine>();

    public ServerSchedule() {
        GetSchedule();
        System.out.println("\tServerSchedule is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortGetSchedule())) {
                    System.out.println("\t" + serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerSchedule.this, serverSocket.accept());
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

        tcpConnection.Send(new Gson().toJson(listSchedule));
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

    private void GetSchedule() {
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Івашенюк", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Стрижка", "Ivasheniuk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Стрижка", "Пастерук", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
        listSchedule.add(new DataScheduleLine(0, "Масаж", "Pasteruk", "09:00-18:00 Пн-Пт "));
    }

}
