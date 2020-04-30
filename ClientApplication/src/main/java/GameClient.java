import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Ioan Sava
 */
public class GameClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {

            int exitFlag = 0;
            while (exitFlag == 0) {
                String command = getCommand();
                out.println(command);

                String response = in.readLine();
                System.out.println(response);

                if (command.equals("exit")) {
                    exitFlag = 1;
                }
            }
        } catch (UnknownHostException exception) {
            System.err.println("No server listening: " + exception);
        }
    }

    private static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Send a new command: ");
        return scanner.next();
    }
}
