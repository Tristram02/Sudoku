package sudoku;


import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GameScene {

    private GridPane sudokuGrid;
    private final Level lvl = new Level();
    private int levelValue;

    private final BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard copyBoard = new SudokuBoard(solver);

    public void setLevelValue(int levelValue) {
        this.levelValue = levelValue;
    }

    public void setSudokuGrid() {
        this.sudokuGrid = (GridPane) SceneChange.getScene().getScene().lookup("#sudokuGrid");
    }


    public void initialize() throws CloneNotSupportedException {
        solver.solve(board);
        copyBoard = (SudokuBoard) board.clone();
        board = lvl.chooseDifficulty(board, this.levelValue);
        setSudokuGrid();
        fillGrid();
    }


    private void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setText(String.valueOf(board.get(i, j)));
                if (board.get(i, j) != 0) {
                    textField.setDisable(true);
                }
                sudokuGrid.add(textField, i, j);
            }
        }
    }
}
