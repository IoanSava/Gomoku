import shell.ExitCommand;
import shell.Shell;
import shell.ShowCmdsCommand;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Ioan Sava
 */
public class GameServer {
    // Define the port on which the server is listening
    private final int PORT = 8100;

    public GameServer() {
        Shell shell = getShell();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            boolean running = true;
            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket, shell).start();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Create a custom shell
     */
    private static Shell getShell() {
        Shell shell = new Shell();

        shell.addCommand(new ShowCmdsCommand("show-cmds"));
        shell.addCommand(new ExitCommand("exit"));

        return shell;
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
    }
}
