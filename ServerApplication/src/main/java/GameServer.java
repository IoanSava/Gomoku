import shell.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * An instance of this class will create a
 * ServerSocket running at a specified port.
 * The server will receive requests
 * (commands) from clients and it will execute them.
 *
 * @author Ioan Sava
 */
public class GameServer {
    // Define the port on which the server is listening
    private final int PORT = 8100;

    public GameServer() {
        Shell shell = getShell();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
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

        shell.addCommand(new SetNameCommand("set-name", "name"));
        shell.addCommand(new CreateGameCommand("create-game"));
        shell.addCommand(new JoinGameCommand("join-game", "id"));
        shell.addCommand(new ShowGamesCommand("show-games"));
        shell.addCommand(new ShowCmdsCommand("show-cmds"));
        shell.addCommand(new StopServerCommand("stop-server"));
        shell.addCommand(new ExitCommand("exit"));

        return shell;
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
    }
}
