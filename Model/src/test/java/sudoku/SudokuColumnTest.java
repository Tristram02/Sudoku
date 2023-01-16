package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {

    SudokuColumn testCol;
    SudokuColumn testCol_2;
    List<SudokuField> fields;

    @BeforeEach
    public void setUp() {
        fields = Arrays.asList(new SudokuField[9]);
        for(int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(i);
        }
        testCol = new SudokuColumn(fields);
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        testCol_2 = (SudokuColumn) testCol.clone();
        assertTrue(testCol.equals(testCol_2) && testCol_2.equals(testCol));
    }
}