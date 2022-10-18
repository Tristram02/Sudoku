/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.firstproject;



public class SudokuBoard {
    private int[][] board = new int[9][9];
    
    public int[][] getBoard() {
        return board;
    }
    
    public boolean correctNumber(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        
        int firstRow = row - row % 3;
        int firstCol = col - col % 3;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[firstRow + i][firstCol + j] == num) {
                    return false;
                }
            }
        }
        
        return true;
    }

    public boolean fillBoard(int[][] board) {
       for (int r = 0; r < 9; r++) {
           for (int c = 0; c < 9; c++) {
               if (board[r][c] == 0) {
                   for (int i = 1; i < 10; i++) {
                       int num = (int)Math.floor(Math.random() * (9 - 1 + 1) + 1);
                       if (correctNumber(r,c,num)) {
                           board[r][c] = num;
                           
                           if (fillBoard(board)) {
                               return true;
                           } else {
                               board[r][c] = 0;
                           }
                       }
                   }
                   return false;
               }
           }
       }
       return true;
    }

    
    public void printBoard() {
        for (int i = 0;i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
