package game;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that manages
 * the game rooms.
 *
 * @author Ioan Sava
 */
@Getter
public class GameManager {
    private static GameManager gameManager;
    private List<Game> games;

    private GameManager() {
        this.games = new ArrayList<>();
    }

    public synchronized static GameManager getInstance() {
        if (gameManager == null) {
            gameManager = new GameManager();
        }

        return gameManager;
    }

    public synchronized void addGame(Game game) {
        games.add(game);
    }

    /**
     * Get ids of the available games.
     */
    public synchronized String getAvailableGames() {
        StringBuilder result = new StringBuilder();
        result.append("Available games: ");
        int id = 0;
        for (Game game : games) {
            if (game.isAvailable()) {
                result.append(id).append(' ');
            }
            ++id;
        }
        return result.toString();
    }
}
