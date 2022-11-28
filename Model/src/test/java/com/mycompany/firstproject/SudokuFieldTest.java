package com.mycompany.firstproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField field;
    SudokuField field_2;

    @BeforeEach
    public void setUp() {
        field = new SudokuField();
        field_2 = new SudokuField();
    }

    @Test
    public void testHashCode() {
        assertEquals(field.hashCode(), field_2.hashCode());
    }

    @Test
    public void testEquals() {
        assertTrue(field.equals(field_2) && field_2.equals(field));
    }

    @Test
    public void testToString() {
        assertNotNull(field.toString());
    }

    @Test
    public void compareToTest() {
        field.setFieldValue(5);
        field_2.setFieldValue(10);
        assertTrue(field.compareTo(field_2) < 0);
        field_2.setFieldValue(2);
        assertTrue(field.compareTo(field_2) > 0);
        field_2.setFieldValue(5);
        assertEquals(field.compareTo(field_2), 0);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        field.setFieldValue(5);
        field_2 = (SudokuField) field.clone();
        assertTrue(field.equals(field_2) && field_2.equals(field));
    }
}