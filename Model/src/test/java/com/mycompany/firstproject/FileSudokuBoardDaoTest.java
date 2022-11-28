package com.mycompany.firstproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    SudokuBoardDaoFactory factory;
    SudokuBoard board;
    SudokuBoard board_2;
    SudokuSolver solver;
    Dao<SudokuBoard> file;

    @BeforeEach
    public void setUp() {
        factory = new SudokuBoardDaoFactory();
        board = new SudokuBoard(solver);
    }

    @Test
    public void readAndWriteTest() {
        file = factory.getFileDao("file");
        file.write(board);
        board_2 = file.read();

        assertEquals(board, board_2);
    }

    @Test
    public void readIOExceptionTest() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            file = factory.getFileDao("files");
            file.read();
        });
    }

    @Test
    public void writeIOExceptionTest() {
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            file = factory.getFileDao("?");
            file.write(board);
        });
    }

}