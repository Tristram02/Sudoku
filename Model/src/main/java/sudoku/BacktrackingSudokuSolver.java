package sudoku;

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

import java.io.Serializable;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    public boolean solve(SudokuBoard board) {
        Random rand = new Random();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board.getBoard().get(r).get(c).getFieldValue() == 0) {
                    for (int i = 1; i < 15; i++) {
                        int num = rand.nextInt(10 - 1) + 1;
                        if (correctNumber(r,c,num, board)) {
                            board.getBoard().get(r).get(c).setFieldValue(num);

                            if (solve(board)) {
                                return true;
                            } else {
                                board.getBoard().get(r).get(c).setFieldValue(0);
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
            if (board.getBoard().get(i).get(col).getFieldValue() == num) {
                return false;
            }
        }
        return true;
    }

    public boolean correctColumn(int row, int col, int num, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.getBoard().get(row).get(i).getFieldValue() == num) {
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
                if (board.getBoard().get(firstRow + i).get(firstCol + j).getFieldValue() == num) {
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
