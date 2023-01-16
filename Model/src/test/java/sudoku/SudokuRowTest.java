package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {

    SudokuRow testRow;
    SudokuRow testRow_2;
    List<SudokuField> fields;

    @BeforeEach
    public void setUp() {
        fields = Arrays.asList(new SudokuField[9]);
        for(int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(i);
        }
        testRow = new SudokuRow(fields);
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        testRow_2 = (SudokuRow) testRow.clone();
        assertTrue(testRow.equals(testRow_2) && testRow_2.equals(testRow));
    }
}