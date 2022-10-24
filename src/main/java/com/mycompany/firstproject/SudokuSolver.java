package com.mycompany.firstproject;

public interface SudokuSolver {
    public boolean solve(SudokuBoard board);

    public boolean correctNumber(int row, int col, int num, SudokuBoard board);
}
