package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.DaoException;
import sudoku.exceptions.FileException;


import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    SudokuBoardDaoFactory factory;
    SudokuBoard board;
    SudokuBoard board_2;
    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    Dao<SudokuBoard> file;

    @BeforeEach
    public void setUp() {
        factory = new SudokuBoardDaoFactory();
        board = new SudokuBoard(solver);
    }

    @Test
    public void readAndWriteTest() throws DaoException {
        file = factory.getFileDao("file");
        file.write(board);
        board_2 = file.read();

        assertEquals(board, board_2);
    }

    @Test
    public void readFileExceptionTest() {
        Throwable exception = assertThrows(FileException.class, () -> {
            file = factory.getFileDao("files");
            file.read();
        });
    }

    @Test
    public void writeFileExceptionTest() {
        Throwable exception = assertThrows(FileException.class, () -> {
            file = factory.getFileDao("?");
            file.write(board);
        });
    }

}