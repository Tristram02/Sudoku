package sudoku;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sudoku.exceptions.FileException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws FileException {
        SudokuBoard obj = null;

        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            obj = (SudokuBoard) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new FileException(e);
        }
        return obj;
    }

    @Override
    public void write(SudokuBoard obj) throws FileException {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new FileException(e);
        }
    }
}
