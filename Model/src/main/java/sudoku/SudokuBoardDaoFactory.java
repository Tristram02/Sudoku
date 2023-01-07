package sudoku;

public class SudokuBoardDaoFactory {

    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getDatabaseDao(String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }
}
