package com.mycompany.firstproject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class SudokuObjectTest {

    SudokuObject object;
    SudokuObject object_2;
    SudokuField[] field;
    SudokuField[] field_2;
    @BeforeEach
    void setUp() {
        object = new SudokuObject();
        object_2 = new SudokuObject();
        field = new SudokuField[9];
        field_2 = new SudokuField[9];

        for(int i=0;i<9;i++)
        {
            field[i] = new SudokuField();
            field_2[i] = new SudokuField();
            field[i].setFieldValue(i+1);
            field_2[i].setFieldValue(i+1);
        }
        field_2[4].setFieldValue(8);
        object.setValuesOfObject(field);
        object_2.setValuesOfObject(field_2);
    }

    @Test
    public void testPrintValuesOfObject() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "";

        for (int i = 0; i < 9; i++) {
                expectedOutput += field[i].getFieldValue();
        }
        object.printValuesOfObject();
        assertEquals(expectedOutput, outContent.toString());
    }
    public void testVerify() {
        assertTrue(object.verify());
        assertFalse(object_2.verify());
    }
}
