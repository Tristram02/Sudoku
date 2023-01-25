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
    public static final String FieldsTableName = "Fields";
    private String file;
    private int id = 0;

    private String insertData = "insert into " + DataBaseName + "(board_id, tableName) values (?,?)";
    private String insertFieldsData = "insert into " + FieldsTableName + "(x,y,field,board_id) values (?,?,?,?)";

    JdbcSudokuBoardDao(String file) {
        this.file = file;
    }

    public Connection connect(String jdbcUrl) throws DatabaseException {
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
        Connection connection = this.connect(jdbcUrl);
        String receivedData = "";
        ResultSet resultSet;
        String selectData = "select field from " + DataBaseName + " inner join " + FieldsTableName + " on " + DataBaseName + ".board_id = " + FieldsTableName + ".board_id where tableName=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectData)) {
            preparedStatement.setString(1, file);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                receivedData += resultSet.getString(1);
            }
            board.convertStringToBoard(receivedData);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }


        return board;
    }

    @Override
    public void write(SudokuBoard board) throws DatabaseException {

        String jdbcUrl = "jdbc:sqlite:./" + file;
        Connection connection = connect(jdbcUrl);

        String createTable = "create table " + DataBaseName + "(board_id int PRIMARY KEY, tableName varchar(20) not null)";
        String createFieldsTable = "create table " + FieldsTableName + "(x int not null, y int not null, field int not null, board_id int not null ,foreign key (board_id) references " + DataBaseName + "(board_id))";

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.execute(createTable);
            statement.execute(createFieldsTable);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            }
            throw new DatabaseException(e);
        }


        try (PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertFieldsData)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, Integer.toString(id));
            preparedStatement.setString(2, file);
            preparedStatement.executeUpdate();

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement1.setString(1,Integer.toString(i));
                    preparedStatement1.setString(2,Integer.toString(j));
                    preparedStatement1.setString(3,Integer.toString(board.getFieldValue(i,j)));
                    preparedStatement1.setString(4,Integer.toString(this.id));
                    preparedStatement1.executeUpdate();
                }
            }
            this.id++;
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(ex);
            }
            throw new DatabaseException(e);
        }

    }

}
