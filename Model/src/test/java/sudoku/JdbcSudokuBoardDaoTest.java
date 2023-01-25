package sudoku;

import org.junit.jupiter.api.Test;
import sudoku.exceptions.DatabaseException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class JdbcSudokuBoardDaoTest {

    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard board_2 = new SudokuBoard(solver);
    private Dao<SudokuBoard> database;




    @Test
    void writeReadTest() throws IOException {
        Files.deleteIfExists(Paths.get("./base.db"));
        database = factory.getDatabaseDao("base.db");
        database.write(board);
        board_2 = database.read();

        assertEquals(board, board_2);
    }

    @Test
    public void readDatabaseExceptionTest() {
        Throwable exception = assertThrows(DatabaseException.class, () -> {
            database = factory.getDatabaseDao("database");
            database.read();
        });
    }


    @Test
    public void writeDatabaseNameExceptionTest() {
        Throwable exception = assertThrows(DatabaseException.class, () -> {
            database = factory.getDatabaseDao("?");
            database.write(board);
        });
    }

    @Test
    public void writeDatabaseCreateTableExceptionTest() throws IOException {
        Files.deleteIfExists(Paths.get("./testbase2.db"));
        database = factory.getDatabaseDao("testbase2.db");
        database.write(board);
        Throwable exception = assertThrows(DatabaseException.class, () -> {
            database = factory.getDatabaseDao("testbase2.db");
            database.write(board);
        });
    }


    @Test
    public void writeDatabaseRollbackTest() throws Exception {
        Files.deleteIfExists(Paths.get("./testbase.db"));

        Throwable exception = assertThrows(DatabaseException.class, () -> {
            Field insert = JdbcSudokuBoardDao.class.getDeclaredField("insertData");
            insert.setAccessible(true);
            SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
            Dao<SudokuBoard> boardDao = sudokuBoardDaoFactory.getDatabaseDao("testbase.db");

            insert.set(boardDao, "");

            boardDao.write(board);
        });

    }

}