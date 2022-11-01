package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest {

    BacktrackingSudokuSolver b;
    SudokuBoard testBoard;
    SudokuSolver solver;
    
    @BeforeEach
    void setUp() {
        solver = new BacktrackingSudokuSolver();
        b = new BacktrackingSudokuSolver();
        testBoard = new SudokuBoard(solver);
    }

    @Test
    public void testCorrectRow() {
        testBoard.set(0,0,1);
        assertFalse(b.correctNumber(0,5,1,testBoard));
        assertTrue(b.correctNumber(0,5,5,testBoard));
    }
    @Test
    public void testCorrectColumn() {
        testBoard.set(0,0,1);
        assertFalse(b.correctNumber(5,0,1,testBoard));
        assertTrue(b.correctNumber(5,0,5,testBoard));
    }

    @Test
    public void testCorrectBox() {
        testBoard.set(0,0,1);
        assertFalse(b.correctNumber(1,1,1,testBoard));
        assertTrue(b.correctNumber(1,1,5,testBoard));
    }

    @Test
    public void testCorrectNumber() {
        testBoard.set(2,7,4);
        assertFalse(b.correctNumber(2, 3, 4, testBoard));
        assertFalse(b.correctNumber(5, 7, 4, testBoard));
        assertFalse(b.correctNumber(1, 8, 4, testBoard));
        assertTrue(b.correctNumber(6, 2, 4, testBoard));
    }
}
