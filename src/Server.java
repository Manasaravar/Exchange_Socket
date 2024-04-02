
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private String host;
    private int port;
    private PrintWriter out;
    private Scanner in;

    private boolean isRunning = true;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Scanner getIn() {
        return in;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void run() throws IOException {
        try (ServerSocket server = serverInit(); Socket client = clientInit(server)) {
            send(out, "server> You connect to Server_Admin...");

            //          Thread outThread = new ServerOutThread(out);
            //           outThread.start();

            String message;
            while (true) {
                message = in.nextLine();
                if (message.equals("break"))
                    break;
                System.out.println(message);
// Обмен валют
                // if (message.equals("change")) {
                out.println("Enter currency USD/EUR/CNY: ");
                String currency = in.nextLine().substring(19);
                send(out, "Enter amount: ");
                String amount = in.nextLine().substring(19);
                out.println("Amount in rub = " + exchange(currency, amount));
                //}
            }

        } catch (IOException e) {
            System.out.println("***SERVER*** инициализация не прошла");
            throw new RuntimeException(e);
        } finally {
            in.close();
            out.close();
        }
    }

    private ServerSocket serverInit() throws IOException {
        System.out.println("***SERVER*** starting...");
        return new ServerSocket(port, 10, InetAddress.getByName(host));
    }

    private Socket clientInit(ServerSocket server) {
        Socket client;
        try {
            client = server.accept(); // ожидание входящих подключений
            out = outInit(client);
            in = inInit(client);
            return client;
        } catch (IOException e) {
            System.out.println("клиент не подключился");
            throw new RuntimeException(e);
        }
    }

    private Scanner inInit(Socket client) {
        try {
            return new Scanner(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.print("соединение разорвано");
            throw new RuntimeException(e);
        }
    }

    private PrintWriter outInit(Socket client) {
        try {
            return new PrintWriter(
                    new OutputStreamWriter(
                            client.getOutputStream()
                    ), true
            );
        } catch (IOException e) {
            System.out.print("соединение разорвано");
            throw new RuntimeException(e);
        }
    }

    public void send(PrintWriter out, String message) {
        System.out.printf("***SERVER*** send message: %s\n\n", message);
        out.println(message);
    }

    public String receive(Scanner in) {
        return in.nextLine();
    }

    // 2)	Реализовать сервис (на сервере), который будет
    // конвертировать валюты одна в другую: 3 валют достаточно.
    // На клиенте вводится запрос в формате <валюта1:валюта2:значение_в_валюте1>
    // (без угловых скобок) и получает ответ в виде значения в валюте 2.
    public double exchange(String currency1, String money) {
        double result = 0;
        int intMoney = Integer.parseInt(money);
        Currency[] values = Currency.values();
        for (Currency currency:values) {
            if(currency.name().equals(currency1)) {
                result = intMoney * currency.getCourse();
                System.out.printf("%d %s = %.2f %s", intMoney, currency1, result, currency.getNameCurrency());
                return result;
            }
        }
        System.out.print("money in currency: ");
        return result;
    }

}

class ServerOutThread extends Thread {
    private final PrintWriter out;

    public ServerOutThread(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void run() {
        String message;
        Scanner console = new Scanner(System.in);
        while (!(message = console.nextLine()).equals("break")) {
            out.println("Server_Admin> " + message);
        }
        out.println(message);
    }
}





