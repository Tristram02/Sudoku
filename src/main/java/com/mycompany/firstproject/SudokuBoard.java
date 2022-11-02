package com.mycompany.firstproject;



public class SudokuBoard {
    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver sudokuSolver;

    private SudokuField[][] field = new SudokuField[9][9];

    private SudokuRow row = new SudokuRow();

    private SudokuColumn column = new SudokuColumn();

    private SudokuBox box = new SudokuBox();

    public SudokuBoard(SudokuSolver solver) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
        sudokuSolver = solver;
    }

    public SudokuField[][] getBoard() {
        return board;
    }

    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify() || !getColumn(i).verify()) {
                return false;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!getBox(i,j).verify()) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public int get(int x, int y) {
        return board[x][y].getFieldValue();
    }

    public SudokuRow getRow(int y) {
        SudokuField[] values = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            values[i] = board[y][i];
        }
        row.setValuesOfObject(values);
        return row;
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] values = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            values[i] = board[i][x];
        }
        column.setValuesOfObject(values);
        return column;
    }

    public SudokuBox getBox(int x, int y) {
        int firstRow = y - y % 3;
        int firstCol = x - x % 3;
        int k = 0;
        SudokuField[] values = new SudokuField[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                values[k] = board[firstRow + i][firstCol + j];
                k++;
            }
        }
        box.setValuesOfObject(values);

        return box;
    }

    public void set(int x, int y, int value) {
        board[x][y].setFieldValue(value);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public void printBoard() {
        for (int i = 0;i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j].getFieldValue());
            }
            System.out.println();
        }
    }
}
