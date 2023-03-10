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
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements Serializable, Cloneable {
    private final List<List<SudokuField>> board;
    private final SudokuSolver sudokuSolver;

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
    
    public int getFieldValue(int x, int y) {
        return board.get(x).get(y).getValue();
    }

    public SudokuField getField(int x, int y) {
        return board.get(x).get(y);
    }

    public SudokuRow getRow(int y) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            values.set(i, board.get(y).get(i));
        }
        return new SudokuRow(values);
    }

    public SudokuColumn getColumn(int x) {
        List<SudokuField> values = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            values.set(i, board.get(i).get(x));
        }

        return new SudokuColumn(values);
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

        return new SudokuBox(values);
    }

    public void setField(int x, int y, int value) {
        board.get(x).get(y).setValue(value);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public void printBoard() {
        for (int i = 0;i < 9;i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board.get(i).get(j).getValue());
            }
            System.out.println();
        }
    }

    public SudokuBoard convertStringToBoard(String text) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                setField(i, j, Character.getNumericValue(text.charAt(i * 9 + j)));
            }
        }
        return this;
    }

    public String convertBoardToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(String.valueOf(getFieldValue(i,j)));
            }
        }
        return builder.toString();
    }


    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(board, ((SudokuBoard) obj).board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,31).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("board", board).toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        SudokuBoard board1 = new SudokuBoard(this.sudokuSolver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1.setField(i, j, getFieldValue(i, j));
            }
        }
        return board1;
    }
}
