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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class SudokuObjectTest {

    SudokuObject object;
    SudokuObject object_2;
    SudokuObject object_3;
    SudokuObject object_4;
    List<SudokuField> field;
    List<SudokuField> field_2;
    List<SudokuField> field_3;
    @BeforeEach
    void setUp() {
        field = Arrays.asList(new SudokuField[9]);
        field_2 = Arrays.asList(new SudokuField[9]);
        field_3 = Arrays.asList(new SudokuField[8]);
        object = new SudokuObject(field);
        object_2 = new SudokuObject(field_2);
        object_3 = new SudokuObject(field);
        object_4 = new SudokuObject(field);

        for(int i = 0; i < 8; i++) {
            field_3.set(i, new SudokuField());
        }

        for(int i=0;i<9;i++)
        {
            field.set(i, new SudokuField());
            field_2.set(i, new SudokuField());
            field.get(i).setValue(i+1);
            field_2.get(i).setValue(i+1);
        }
        field_2.get(4).setValue(8);
        field_2.get(3).setValue(0);
        object.setValuesOfObject(field);
        object_2.setValuesOfObject(field_2);
    }

    @Test
    public void SudokuObjectWrongSizeTest() {
        Throwable exception = assertThrows(RuntimeException.class, () -> new SudokuObject(field_3));
        assertEquals("Wrong object size!", exception.getMessage());
    }
    @Test
    public void testGetField() {
        assertEquals(object.getField(),field);
    }
    @Test
    public void testPrintValuesOfObject() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "";

        for (int i = 0; i < 9; i++) {
                expectedOutput += field.get(i).getValue();
        }
        object.printValuesOfObject();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testVerify() {
        assertTrue(object.verify());
        assertFalse(object_2.verify());
    }

    @Test
    public void toStringTest() {
        assertNotNull(object_3.toString());
    }

    @Test
    public void equalsTest() {
        assertTrue(object_3.equals(object_4) && object_4.equals(object_3));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(object_3.hashCode(), object_4.hashCode());
    }
}
