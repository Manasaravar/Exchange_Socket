import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final String HOST;
    final int PORT;
    Scanner in;
    PrintWriter out;

    public Client(String HOST, int PORT) {
        this.HOST = HOST;
        this.PORT = PORT;
    }

    public void run() throws IOException {
        try (Socket client = new Socket(HOST, PORT)) {
            in = new Scanner(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);

          //  System.out.println("Server message : " + in.nextLine());
            out.println("hello server");

            new ClientOutThread(out).start();
            String serverMessage;

            while (true) {
                serverMessage = in.nextLine();
                if (serverMessage.equals("break")) {
                    break;
                }
                System.out.println(serverMessage);
            }
        } finally {
            in.close();
            out.close();
        }
    }
}

class ClientOutThread extends Thread {
    private final PrintWriter out;
    private final Scanner console;

    public ClientOutThread(PrintWriter out) {
        this.out = out;
        this.console = new Scanner(System.in);
    }

    @Override
    public void run() {
        String message;

        while (true) {
          message = console.nextLine();
            if (message.equals("break")) {
                out.println("break");
                break;
            } else {
                out.println("Client_Korniushin> " + message);
            }
        }
    }
}