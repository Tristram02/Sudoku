package sudoku;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sudoku.exceptions.DatabaseException;

public class JdbcSudokuBoardDao implements  Dao<SudokuBoard> {

    public static final String DataBaseName = "Board";
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
        String selectData = "select tableName, fields, isEditable from " + DataBaseName + " where tableName=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectData)) {
            System.out.println(preparedStatement.toString());
            preparedStatement.setString(1, file);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            receivedData = resultSet.getString(2);
            System.out.println(receivedData);
            board.convertStringToBoard(receivedData);
            receivedData = resultSet.getString(3);
            board.convertStringToIsEditable(receivedData);
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

        String createTable = "create table " + DataBaseName + "(tableName varchar(20) primary key not null," + "fields varchar(81), isEditable varchar(81))";

        String insertData = "insert into Board(tableName, fields, isEditable) values (?,?,?)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
                preparedStatement.setString(1, file);
                preparedStatement.setString(2, board.convertBoardToString());
                preparedStatement.setString(3, board.convertIsEditableToString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }


}
