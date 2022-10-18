/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.firstproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SudokuBoardTest {

    @Test
    public void testFillBoard() {
        SudokuBoard firstInstance = new SudokuBoard();
        SudokuBoard secondInstance = new SudokuBoard();
        
        boolean firstResult = firstInstance.fillBoard(firstInstance.getBoard());
        boolean secondResult = secondInstance.fillBoard(secondInstance.getBoard());
        
        assertFalse(firstInstance.getBoard() == secondInstance.getBoard());
        
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (j != k) {
                        assertFalse(firstInstance.getBoard()[i][j] == firstInstance.getBoard()[i][k]);
                    }
                }
            }
        }
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (j != k) {
                        assertFalse(firstInstance.getBoard()[j][i] == firstInstance.getBoard()[k][i]);
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
                           assertFalse(firstInstance.getBoard()[r+x][c+y] == firstInstance.getBoard()[i][j]);    
                        }
                    }
                }
            }
        }
        
    }
    
}
