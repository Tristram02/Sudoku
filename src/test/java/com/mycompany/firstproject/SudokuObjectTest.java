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
    @BeforeEach
    void setUp() {
        object = new SudokuObject();
        object_2 = new SudokuObject();
        object_3 = new SudokuObject();
        object_4 = new SudokuObject();
        field = Arrays.asList(new SudokuField[9]);
        field_2 = Arrays.asList(new SudokuField[9]);

        for(int i=0;i<9;i++)
        {
            field.set(i, new SudokuField());
            field_2.set(i, new SudokuField());
            field.get(i).setFieldValue(i+1);
            field_2.get(i).setFieldValue(i+1);
        }
        field_2.get(4).setFieldValue(8);
        object.setValuesOfObject(field);
        object_2.setValuesOfObject(field_2);
    }

    @Test
    public void testPrintValuesOfObject() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        String expectedOutput = "";

        for (int i = 0; i < 9; i++) {
                expectedOutput += field.get(i).getFieldValue();
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
