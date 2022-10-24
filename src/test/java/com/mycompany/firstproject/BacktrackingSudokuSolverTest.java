package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest {

    @Test
    public void testCorrectNumber() {
        SudokuBoard testBoard = new SudokuBoard();
        testBoard.set(2,7,4);
        assertFalse(BacktrackingSudokuSolver.correctNumber(2, 3, 4, testBoard));
        assertFalse(BacktrackingSudokuSolver.correctNumber(5, 7, 4, testBoard));
        assertFalse(BacktrackingSudokuSolver.correctNumber(1, 8, 4, testBoard));
        assertTrue(BacktrackingSudokuSolver.correctNumber(6, 2, 4, testBoard));
    }
}
