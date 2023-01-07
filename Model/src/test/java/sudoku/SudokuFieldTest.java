package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.WrongValueException;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField field;
    SudokuField field_2;

    ResourceBundle bundle = ResourceBundle.getBundle("Language");

    @BeforeEach
    public void setUp() {
        field = new SudokuField();
        field_2 = new SudokuField();
    }

    @Test
    public void setFieldValueExceptionTest() {
        Throwable exception = assertThrows(WrongValueException.class, () -> {
            field.setFieldValue(10);
        });
        Throwable exception_2 = assertThrows(WrongValueException.class, () -> {
            field.setFieldValue(-1);
        });
        assertEquals(bundle.getString("_badValue"), exception.getMessage());
        assertEquals(bundle.getString("_badValue"), exception_2.getMessage());
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
        field_2.setFieldValue(7);
        assertTrue(field.compareTo(field_2) < 0);
        field_2.setFieldValue(2);
        assertTrue(field.compareTo(field_2) > 0);
        field_2.setFieldValue(5);
        assertEquals(field.compareTo(field_2), 0);
    }


    @Test
    public void compareToExceptionTest() {
        Throwable exception = assertThrows(NullPointerException.class, () -> {
            int test = field.compareTo(null);
        });
        assertEquals("Wrong object!", exception.getMessage());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        field.setFieldValue(5);
        field_2 = (SudokuField) field.clone();
        assertTrue(field.equals(field_2) && field_2.equals(field));
    }
}