package com.mycompany.firstproject;

public class BacktrackingSudokuSolver implements SudokuSolver {
    public boolean solve(SudokuBoard board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getBoard()[r][c] == 0) {
                    for (int i = 1; i < 10; i++) {
                        int num = (int)Math.floor(Math.random() * (9 - 1 + 1) + 1);
                        if (correctNumber(r,c,num, board)) {
                            board.getBoard()[r][c] = num;

                            if (solve(board)) {
                                return true;
                            } else {
                                board.getBoard()[r][c] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean correctRow(int row, int col, int num, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.getBoard()[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean correctColumn(int row, int col, int num, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.getBoard()[row][i] == num) {
                return false;
            }
        }
        return true;
    }

    public boolean correctBox(int row, int col, int num, SudokuBoard board) {
        int firstRow = row - row % 3;
        int firstCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[firstRow + i][firstCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean correctNumber(int row, int col, int num, SudokuBoard board) {
        if (correctRow(row, col, num, board) && correctColumn(row, col, num, board) && correctBox(row, col, num, board)) {
            return true;
        }
        return false;
    }
}
