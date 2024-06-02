package ru.netology;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private boolean running;
    private ExecutorService pool;

    public Server(int port) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(10);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("Сервер запущен на порту " + port);

        while (running) {
            try {
                Socket connection = serverSocket.accept();
                pool.execute(new ConnectionHandler(connection));
            } catch (IOException e) {
                if (!running) break;
                System.out.println("Ошибка при подключении клиента: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        pool.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Ошибка при остановке сервера: " + e.getMessage());
        }
    }
}