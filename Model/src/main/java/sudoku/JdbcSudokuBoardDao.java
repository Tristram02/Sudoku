package sudoku;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import sudoku.exceptions.DatabaseException;

public class JdbcSudokuBoardDao implements  Dao<SudokuBoard> {

    public static final String DataBaseName = "Board";
    private static final Logger logger = Logger.getLogger(JdbcSudokuBoardDao.class.getName());
    private String file;

    JdbcSudokuBoardDao(String file) {
        this.file = file;
    }

    private Connection connect(String jdbcUrl) throws DatabaseException {
        Connection connection;

        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return connection;
    }

    @Override
    public SudokuBoard read() throws DatabaseException {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        String jdbcUrl = "jdbc:sqlite:" + this.file;
        Connection connection = connect(jdbcUrl);
        String receivedData;
        ResultSet resultSet;
        String selectData = "select tableName, fields from " + DataBaseName + " where tableName=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectData)) {
            logger.info(preparedStatement.toString());
            preparedStatement.setString(1, file);
            logger.info(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            receivedData = resultSet.getString(2);
            logger.info(receivedData);
            board.convertStringToBoard(receivedData);
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }


        return board;
    }

    @Override
    public void write(SudokuBoard board) throws DatabaseException {

        String jdbcUrl = "jdbc:sqlite:./" + file;
        Connection connection = connect(jdbcUrl);

        String createTable = "create table " + DataBaseName + "(tableName varchar(20) primary key not null," + "fields varchar(81))";

        String insertData = "insert into Board(tableName, fields) values (?,?)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
                preparedStatement.setString(1, file);
                preparedStatement.setString(2, board.convertBoardToString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }


}
