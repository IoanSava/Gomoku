package shell;

import game.Player;

/**
 * Use this command in order
 * to submit a move.
 * Format: 'submit-move x y'
 *
 * @author Ioan Sava
 */
public class SubmitMoveCommand extends Command {

    public SubmitMoveCommand(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 3) {
            return "Format: submit-move x y";
        }

        Player player = (Player) arguments[0];
        int x = Integer.parseInt((String) arguments[1]);
        int y = Integer.parseInt((String) arguments[2]);

        if (player.getGame().getBoard().setPiece(x, y, player.getToken())) {
            String result = "Player " + player.getName() + " placed a token at (" + x + "," + y + ")";
            String move = arguments[1] + " " + arguments[2];
            player.getGame().addMove(player, move);
            if (player.wonTheGame(x, y)) {
                result = result.concat(" and won the game");
                player.getGame().addMove(player, "won the game");
            }
            return result;
        }

        return "Invalid move";
    }
}
