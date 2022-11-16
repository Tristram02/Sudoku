package com.mycompany.firstproject;

/*-
 * #%L
 * FirstProject
 * %%
 * Copyright (C) 2022 PROKOMP
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard {
    private final List<List<SudokuField>> board;
    private final SudokuSolver sudokuSolver;

    private final SudokuRow row = new SudokuRow();

    private final SudokuColumn column = new SudokuColumn();

    private final SudokuBox box = new SudokuBox();

    public SudokuBoard(SudokuSolver solver) {
        board = Arrays.asList(new List[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i,Arrays.asList(new SudokuField[9]));
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.get(i).set(j, new SudokuField());
            }
        }
        sudokuSolver = solver;
    }

    public List<List<SudokuField>> getBoard() {
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
        return board.get(x).get(y).getFieldValue();
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            values.set(i, board.get(y).get(i));
        }
        row.setValuesOfObject(values);
        return row;
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            values.set(i, board.get(i).get(x));
        }
        column.setValuesOfObject(values);
        return column;
    }

    public SudokuBox getBox(int x, int y) {
        int firstRow = y - y % 3;
        int firstCol = x - x % 3;
        int k = 0;
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                values.set(k, board.get(firstRow + i).get(firstCol + j));
                k++;
            }
        }
        box.setValuesOfObject(values);

        return box;
    }

    public void set(int x, int y, int value) {
        board.get(x).get(y).setFieldValue(value);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public void printBoard() {
        for (int i = 0;i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board.get(i).get(j).getFieldValue());
            }
            System.out.println();
        }
    }
}
