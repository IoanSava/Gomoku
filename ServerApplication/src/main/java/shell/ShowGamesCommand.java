package shell;

import game.GameManager;

/**
 * This command can be used to
 * show the available games.
 * Format: 'show-games'
 *
 * @author Ioan Sava
 */
public class ShowGamesCommand extends Command {
    private final GameManager gameManager = GameManager.getInstance();

    public ShowGamesCommand(String command) {
        super(command);
    }

    @Override
    public String execute(Object... arguments) {
        return gameManager.getAvailableGames();
    }
}
