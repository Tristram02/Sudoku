package com.mycompany.firstproject;

public class SudokuRow {

    public boolean verify() {
        for (int i = 0; i < 9; i++)
        {
            for(int j=0;j<9;j++)
            {
                if(i!=j)
                    if(field[i]==field[j]) return false;

            }
        }
        return true;
    }
}
