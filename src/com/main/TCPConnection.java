package com.main;

import com.google.gson.Gson;
import com.main.Data.DataWorker;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.Charset;

public class TCPConnection {
    public final Socket socket;
    public final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private BufferedReader in;
    private BufferedWriter out;

    public TCPConnection(TCPConnectionListener eventListener, String ipAddress, int port) throws IOException {
        this(eventListener, new Socket(ipAddress, port));
    }

    public TCPConnection(TCPConnectionListener eventListener, Socket socket) throws IOException {
        this.socket = socket;
        this.eventListener = eventListener;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8")));
        rxThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TCPConnection.this.eventListener.onConnectionReady(TCPConnection.this);
                    while (!rxThread.isInterrupted()) {
                        String data = in.readLine();
                        TCPConnection.this.eventListener.onReceive(TCPConnection.this, data);
                    }
                } catch (SocketException e) {
                    System.out.println("Client was disconnected! " + socket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                    rxThread.interrupt();
                } catch (NullPointerException ex){
                    //System.out.println("\n");
                }
                finally {
                    TCPConnection.this.eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }

    public synchronized void Send(String data) {
        try {
            out.write(data + "\r\n");
            out.flush();
        } catch (IOException e) {
            this.eventListener.onExeption(TCPConnection.this, e);
            Disconnect();
        }
    }

    public synchronized void Send(String nameWorker, String kingOfServiceWorker, String priceWorker, int idButtonWorker, String imgWorker) {
        try {
            out.write(
                    nameWorker + "\r\n" +
                            kingOfServiceWorker + "\r\n" +
                            priceWorker + "\r\n" +
                            idButtonWorker + "\r\n" +
                            imgWorker + "\r\n"
            );
            out.flush();
        } catch (IOException e) {
            this.eventListener.onExeption(TCPConnection.this, e);
            Disconnect();
        }
    }

    public synchronized void Disconnect() {
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            this.eventListener.onExeption(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnectin: " + socket.getInetAddress() + ":" + socket.getPort();
    }
}
