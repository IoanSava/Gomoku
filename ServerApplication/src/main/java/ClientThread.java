import game.Player;
import shell.Command;
import shell.Shell;
import shell.SubmitMoveCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of this class will be responsible
 * with communicating with a client Socket.
 *
 * @author Ioan Sava
 */
public class ClientThread extends Thread {
    private final Socket socket;
    private final Shell shell;
    private final Player player;

    public ClientThread(Socket socket, Shell shell) {
        this.socket = socket;
        this.shell = shell;
        this.player = new Player();
    }

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
                } else if (response.contains("stopped")) {
                    System.exit(0);
                } else if (response.contains("created") || response.contains("joined")) {
                    gameMode(in, out);
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
        } else if (command.getCommand().equals("show-cmds")) {
            return command.execute(shell);
        } else if (command.getCommand().equals("set-name")) {
            return executeSetName(command, commandArguments);
        } else if (command.getCommand().equals("create-game")) {
            return command.execute(player);
        } else if (command.getCommand().equals("join-game")) {
            return executeJoinGame(command, commandArguments);
        }
        return command.execute(); // show-games, stop-server, exit
    }

    private String executeSetName(Command command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private String executeJoinGame(Command command, String[] commandArguments) {
        if (commandArguments.length > 1) {
            return command.execute(player, commandArguments[1]);
        }
        return command.execute();
    }

    private void gameMode(BufferedReader in, PrintWriter out) throws IOException {
        waitOtherPlayer();
        String response;
        if (player.getToken() == 'x') {
            response = "Your opponent is " + player.getGame().getPlayers().get(1).getName() + ". It's your turn to submit a move";
            out.println(response);
            out.flush();
        }

        String request;
        boolean playing = true;
        while (playing) {
            if (player.getGame().getCurrentTurn() == player.getToken()) {
                boolean validMove = false;
                while (!validMove) {
                    request = in.readLine();

                    response = submitMove(request);
                    out.println(response);
                    out.flush();
                    if (!response.contains("Invalid")) {
                        if (response.contains("won")) {
                            playing = false;
                            player.getGame().generateHTMLRepresentation();
                        }
                        validMove = true;
                    }
                }
                player.getGame().updateTurn();
            }

            if (player.getGame().getCurrentTurn() != player.getToken()) {
                response = getLastMoveFromOpponent(playing);
                if (playing) {
                    out.println(response);
                    out.flush();
                    if (response.contains("lost")) {
                        playing = false;
                    }
                }
            }
        }
    }

    /**
     * Wait an opponent to connect in order to start the game
     * using a wait-notify approach
     */
    private void waitOtherPlayer() {
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            while (player.getGame().getPlayers().size() < 2) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private String submitMove(String move) {
        SubmitMoveCommand submitMoveCommand = new SubmitMoveCommand("submit-move", "x, y");
        String[] moveArguments = move.split(" ");
        if (moveArguments.length == 2) {
            return submitMoveCommand.execute(player, moveArguments[0], moveArguments[1]);
        }
        return submitMoveCommand.execute();
    }

    /**
     * Wait-notify approach in order to get the opponent
     * last move.
     */
    private String getLastMoveFromOpponent(boolean playing) {
        String move = "";
        synchronized (player.getGame()) {
            player.getGame().notifyAll();
            if (!playing) {
                return "";
            }
            // if the last move was submitted by the current player
            while (player.getGame().getMoves().size() == 0 ||
                    player.getGame().getLastMove().get("token").equals(String.valueOf(player.getToken()))) {
                try {
                    player.getGame().wait();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            if (player.getGame().getLastMove().get("move").equals("won the game")) {
                move = move.concat("You lost.");
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getMoves().get(player.getGame().getMoves().size() - 2).get("move"));
            } else {
                move = move.concat("Your opponent move: ");
                move = move.concat(player.getGame().getLastMove().get("move"));
            }
        }
        return move;
    }
}
