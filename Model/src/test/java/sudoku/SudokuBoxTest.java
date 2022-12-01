package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {

    SudokuBox testBox;
    SudokuBox testBox_2;
     List<SudokuField> fields;

    @BeforeEach
    public void setUp() {
        fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setFieldValue(i);
        }
        testBox = new SudokuBox(fields);
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        testBox_2 = (SudokuBox) testBox.clone();
        assertTrue(testBox.equals(testBox_2) && testBox_2.equals(testBox));
    }
}