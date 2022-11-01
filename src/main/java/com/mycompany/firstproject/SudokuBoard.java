/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.firstproject;



public class SudokuBoard {
    private final int[][] board = new int[9][9];

    private SudokuField[][] field = new SudokuField[9][9];

    private SudokuRow[] row = new SudokuRow[9];

    private SudokuColumn[] column = new SudokuColumn[9];

    private SudokuBox[] box = new SudokuBox[9];

    public int[][] getBoard() {
        return board;
    }

    public boolean checkBoard() {
        for(int i=0;i<9;i++) {
            row[i].verify();
            column[i].verify();
            box[i].verify();
        }
    }
    
    public int get(int x, int y) {
        return board[x][y];
    }

    public SudokuRow getRow (int y) {
        return row[y];
    }

    public SudokuColumn getColumn (int x) {
        return column[x];
    }

    public SudokuBox getBox (int x, int y) {
        return box[(x/3)*3+y/3];
    }

    public void set(int x, int y, int value) {
        board[x][y] = value;
    }

    public void solveGame() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        backtrackingSudokuSolver.solve(this);
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
