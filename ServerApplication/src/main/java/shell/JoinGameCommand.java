package shell;

import game.Game;
import game.GameManager;
import game.Player;

/**
 * Use this command in order to
 * join a game given by a
 * specified id.
 * Format: 'join-game id'
 *
 * @author Ioan Sava
 */
public class JoinGameCommand extends Command {
    private final GameManager gameManager = GameManager.getInstance();

    public JoinGameCommand(String command, String arguments) {
        super(command, arguments);
    }

    @Override
    public String execute(Object... arguments) {
        if (arguments.length != 2) {
            return "Format: join-game id";
        }
        Player player = (Player) arguments[0];
        int id = Integer.parseInt((String) arguments[1]);

        if (gameManager.getGames().size() - 1 >= id && gameManager.getGames().get(id).isAvailable()) {
            Game game = gameManager.getGames().get(id);
            player.setGame(game);
            player.setToken('o');
            game.addPlayer(player);
            return "You joined the game with id " + id + ". Your token is 'o'. Your opponent is " + game.getPlayers().get(0).getName();
        }

        return "Game unavailable";
    }
}
