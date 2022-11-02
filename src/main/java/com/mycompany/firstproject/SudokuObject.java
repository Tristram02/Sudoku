package com.mycompany.firstproject;

public class SudokuObject {
    private final SudokuField[] field = new SudokuField[9];

    public SudokuObject() {
        for (int i = 0; i < 9; i++) {
            field[i] = new SudokuField();
        }
    }

    public void setValuesOfObject(SudokuField[] values) {
        for (int i = 0; i < 9; i++) {
            field[i] = values[i];
        }
    }

    public void printValuesOfObject() {
        for (int i = 0; i < 9; i++) {
            System.out.print(field[i].getFieldValue());
        }
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                    if (field[i].getFieldValue() == field[j].getFieldValue() && i != j) {
                        return false;
                    }
            }
        }
        return true;
    }
}
