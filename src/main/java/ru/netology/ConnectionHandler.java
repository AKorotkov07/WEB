package ru.netology;

import java.io.*;
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
                if (inputLine.startsWith("GET")) {
                    String filePath = inputLine.split(" ")[1];
                    handleClientRequest(filePath, out);
                } else {
                }
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

    private void handleClientRequest(String filePath, PrintWriter out) {
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
            }
        } else {
            out.println("Файл не найден");
        }
    }
}