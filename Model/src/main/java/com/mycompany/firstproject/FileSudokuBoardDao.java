package com.mycompany.firstproject;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard obj = null;

        try (FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            obj = (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}