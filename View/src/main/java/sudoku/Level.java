package sudoku;



import javafx.util.Pair;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Level {

    private final Set<Pair<Integer, Integer>> hiddenFields = new HashSet<>();
    private final Random rand = new Random();
    private int levelValue = 1;

    public int getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(int levelValue) {
        this.levelValue = levelValue;
    }

    public SudokuBoard chooseDifficulty(SudokuBoard board, int level) {
        return drawRandomIndexes(board,5 * level);
    }


    private SudokuBoard drawRandomIndexes(SudokuBoard board, int numberOfIndexes) {
        while(hiddenFields.size() < numberOfIndexes) {
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);
            hiddenFields.add(new Pair<>(x,y));
        }

        for(Pair<Integer, Integer> x : hiddenFields) {
            board.set(x.getKey(), x.getValue(), 0);
        }
        return board;
    }

}
