package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket connection;

    public ConnectionHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             PrintWriter out = new PrintWriter(connection.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Сервер получил: " + inputLine);
                out.println("Эхо: " + inputLine);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при обработке подключения: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии подключения: " + e.getMessage());
            }
        }
    }
}