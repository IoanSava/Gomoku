package game;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Ioan Sava
 */
@NoArgsConstructor
@Getter
@Setter
public class Player {
    private String name;
    private Game game;
    /**
     * 'x' or 'o'
     */
    private char token;

    public boolean wonTheGame(int x, int y) {
        return this.game.checkWon(token, x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return token == player.token;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
}
