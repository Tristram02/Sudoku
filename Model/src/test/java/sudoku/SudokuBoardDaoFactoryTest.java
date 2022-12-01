package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {

    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    void getFileDaoTest() {
        assertNotNull(factory.getFileDao("file"));
    }
}