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
    void testHashCode() {
        assertEquals(field.hashCode(), field_2.hashCode());
    }

    @Test
    void testEquals() {
        assertTrue(field.equals(field_2) && field_2.equals(field));
    }

    @Test
    void testToString() {
        assertNotNull(field.toString());
    }
}