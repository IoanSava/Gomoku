import exceptions.InvalidPositionException;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Ioan Sava
 */
@Getter
@ToString
public class Board {
    private final int SIZE = 10;
    private char[][] board;

    public Board() {
        board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                board[i][j] = '-';
            }
        }
    }

    public char getPiece(int xIndex, int yIndex) {
        try {
            if (xIndex < 0 || yIndex < 0 || xIndex >= SIZE || yIndex >= SIZE) {
                throw new InvalidPositionException();
            }
            return this.board[xIndex][yIndex];
        } catch (InvalidPositionException e) {
            System.out.println(e.getMessage());
            return '&';
        }
    }

    public boolean setPiece(int xIndex, int yIndex, char piece) {
        try {
            if (xIndex < 0 || yIndex < 0 || xIndex >= SIZE || yIndex >= SIZE) {
                throw new InvalidPositionException();
            }
            if (this.board[xIndex][yIndex] == '-') {
                this.board[xIndex][yIndex] = piece;
                return true;
            }
            return false;
        } catch (InvalidPositionException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void print() {
        System.out.println("------------------------------");
        System.out.print("  ");
        for (int i = 0; i < SIZE; ++i) {
            System.out.print(i);
            System.out.print(' ');
        }
        System.out.println();

        for (int i = 0; i < SIZE; ++i) {
            System.out.print(i);
            System.out.print(' ');
            for (int j = 0; j < SIZE; ++j) {
                System.out.print(this.board[i][j]);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.println("------------------------------");
    }
}
