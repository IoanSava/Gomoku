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
    private static char token;
    private static char opponentToken;

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

                if (command.equals("exit") || command.equals("stop-server")) {
                    exitFlag = 1;
                } else if (response.contains("created") || response.contains("joined")) {
                    setToken(response);
                    gameMode(in, out);
                }
            }
        } catch (UnknownHostException exception) {
            System.err.println("No server listening: " + exception);
        }
    }

    private static String getCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Send a new command: ");
        return scanner.nextLine();
    }

    private static void setToken(String response) {
        if (response.contains("create")) {
            token = 'x';
            opponentToken = 'o';
        } else {
            token = 'o';
            opponentToken = 'x';
        }
    }

    private static void gameMode(BufferedReader in, PrintWriter out) throws IOException {
        Board board = new Board();
        String response = in.readLine();
        System.out.println(response);

        // second player
        if (token == 'o') {
            updateBoard(board, response, opponentToken);
            board.print();
        }

        boolean playing = true;
        while (playing) {
            if (response.contains("won") || response.contains("lost")) {
                playing = false;
            } else {
                String move = "";
                boolean validMove = false;
                while (!validMove) {
                    move = submitMove();
                    out.println(move);

                    response = in.readLine();
                    System.out.println(response);
                    if (!response.contains("Invalid")) {
                        validMove = true;
                    }
                }

                updateBoard(board, move, token);
                board.print();

                if (response.contains("won") || response.contains("lost")) {
                    playing = false;
                } else {
                    response = in.readLine();
                    System.out.println(response);
                    updateBoard(board, response, opponentToken);
                    board.print();
                }
            }
        }
    }

    private static String submitMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("submit-move ");
        return scanner.nextLine();
    }

    private static void updateBoard(Board board, String move, char piece) {
        String[] arguments = move.split(" ");
        int x = Integer.parseInt(arguments[arguments.length - 2]);
        int y = Integer.parseInt(arguments[arguments.length - 1]);
        board.setPiece(x, y, piece);
    }
}
