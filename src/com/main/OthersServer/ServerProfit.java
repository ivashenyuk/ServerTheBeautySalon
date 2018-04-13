package com.main.OthersServer;

import com.google.gson.Gson;
import com.main.Data.DataProfitLine;
import com.main.Data.DataScheduleLine;
import com.main.Setting;
import com.main.TCPConnection;
import com.main.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerProfit implements TCPConnectionListener{
    private static final ArrayList<TCPConnection> connections = new ArrayList<TCPConnection>();
    private Thread thread = null;
    private ArrayList<DataProfitLine> listProfit = new ArrayList<DataProfitLine>();

    public ServerProfit() {
        GetSchedule();
        System.out.println("\tServerProfit is running...");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(Setting.getPortGetProfit())) {
                    System.out.println("\t" +serverSocket.getLocalSocketAddress());
                    while (true) {
                        new TCPConnection(ServerProfit.this, serverSocket.accept());
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

        tcpConnection.Send(new Gson().toJson(listProfit));
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
        listProfit.add(new DataProfitLine(1, "Ivashenuik", "100", "30"));
        listProfit.add(new DataProfitLine(1, "Ivashenuik", "100", "30"));
        listProfit.add(new DataProfitLine(1, "Ivashenuik", "100", "30"));
        listProfit.add(new DataProfitLine(1, "Ivashenuik", "100", "30"));
    }

}
