/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SudokuBoardTest {

    @Test
    public void testGetandSet() {
        int x = 2, y = 4, value = 5;
        SudokuBoard testBoard = new SudokuBoard();
        testBoard.set(x, y, value);
        assertTrue(testBoard.get(2,4)==5);
    }

    @Test
    public void testSolveGame() {
        SudokuBoard firstInstance = new SudokuBoard();
        SudokuBoard secondInstance = new SudokuBoard();
        
        firstInstance.solveGame();
        secondInstance.solveGame();
        
        assertFalse(firstInstance.getBoard() == secondInstance.getBoard());
        
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (j != k) {
                        assertFalse(firstInstance.get(i,j) == firstInstance.get(i,k));
                    }
                }
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (j != k) {
                        assertFalse(firstInstance.get(j,i) == firstInstance.get(k,i));
                    }
                }
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int r = i - i % 3;
                int c = j - j % 3;
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (r+x != i && c+y != j) {
                           assertFalse(firstInstance.get(r+x,c+y) == firstInstance.get(i,j));
                        }
                    }
                }
            }
        }
        
    }
    
}
