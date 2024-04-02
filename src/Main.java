

import java.io.IOException;

public class Main {
    static final int PORT = 8081;
    static final String HOST = "127.0.0.1";
        //"25.7.187.76"; // запуская у себя на машине, используем 127.0.0.1

    public static void main(String[] args) {

        Thread serverThread = new Thread( () -> {
            try {
                new Server(HOST, PORT).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread clientThread = new Thread(() -> {
            try {
                new Client(HOST, PORT).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        serverThread.start();
        clientThread.start();

        try {
            serverThread.join();
            clientThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

