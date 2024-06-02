import ru.netology.Server;

import java.io.IOException;

public static void main(String[] args) {
    int port = 8080;
    Server server = new Server(port);
    try {
        server.start();
    } catch (IOException e) {
        System.out.println("Не удалось запустить сервер: " + e.getMessage());
    }
}