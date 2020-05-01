package shell;

import game.Game;
import game.GameManager;
import game.Player;

/**
 * Use this command in order to
 * create a new game.
 * Format: 'create-game'
 *
 * @author Ioan Sava
 */
public class CreateGameCommand extends Command {
    private final GameManager gameManager = GameManager.getInstance();

    public CreateGameCommand(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        Player player = (Player) arguments[0];
        player.setToken('x');
        Game game = new Game();
        game.addPlayer(player);
        player.setGame(game);
        gameManager.addGame(game);
        return "Game created. Your token is 'x'. Wait an opponent";
    }
}
