package com.mycompany.firstproject;

public interface SudokuSolver {
    boolean solve(SudokuBoard board);

    boolean correctNumber(int row, int col, int num, SudokuBoard board);
}
