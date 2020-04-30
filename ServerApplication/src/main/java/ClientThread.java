import lombok.AllArgsConstructor;
import shell.Command;
import shell.Shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Ioan Sava
 */
@AllArgsConstructor
public class ClientThread extends Thread {
    private final Socket socket;
    private final Shell shell;

    public void run() {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            int stopFlag = 0;
            while (stopFlag == 0) {
                String request = in.readLine();
                String[] commandArguments = request.split(" ", 2);
                Command command = shell.getCommand(commandArguments[0]);
                String response = executeCommand(shell, command, commandArguments);
                out.println(response);
                out.flush();
                if (response.equals("Goodbye")) {
                    stopFlag = 1;
                }
            }
        } catch (IOException exception) {
            System.err.println("Communication error: " + exception);
        } finally {
            try {
                socket.close();
            } catch (IOException exception) {
                System.err.println(exception.getMessage());
            }
        }
    }

    private String executeCommand(Shell shell, Command command, String[] commandArguments) {
        if (command == null) {
            return "Invalid command. Type 'show-cmds' to see the available commands";
        } else if (command.getCommand().equals("exit")) {
            return command.execute((Object) null);
        } else if (command.getCommand().equals("show-cmds")) {
            return command.execute(shell);
        }
        return "Server received the request";
    }
}
