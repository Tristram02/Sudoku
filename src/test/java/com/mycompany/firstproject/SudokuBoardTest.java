/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


public class SudokuBoardTest {

    SudokuBoard testBoard;
    SudokuBoard testBoard_2;
    SudokuSolver solver;

    @BeforeEach
    void setUp() {
        solver = new BacktrackingSudokuSolver();
        testBoard = new SudokuBoard(solver);
        testBoard_2 = new SudokuBoard(solver);
    }
    @BeforeEach
    void prepareTestBoard_2() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard_2.set(i,j,1);
            }
        }
    }


    @Test
    public void testSet() {
        int x = 2, y = 4, value = 5;
        testBoard.set(x, y, value);
        assertTrue(testBoard.get(2,4)==5);
    }

    @Test
    public void testPrintBoard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "";

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                expectedOutput += testBoard_2.get(i,j);
            }
            expectedOutput += "\r\n";
        }
        testBoard_2.printBoard();
        assertEquals(expectedOutput, outContent.toString());

    }

    @Test
    public void testSolveGame() {
        testBoard.solveGame();
        testBoard_2.solveGame();

        assertFalse(testBoard.getBoard() == testBoard_2.getBoard());
        assertNotEquals(testBoard.getBoard(), testBoard_2.getBoard());
    }
    
}
