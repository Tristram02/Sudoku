package sudoku;


import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import sudoku.exceptions.DaoException;

public class GameScene {

    @FXML
    private GridPane sudokuGrid;
    private final Level lvl = new Level();
    private int levelValue;
    private static boolean fromFile = false;

    private final BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solver);
    private SudokuBoard copyBoard = new SudokuBoard(solver);
    private final ResourceBundle bundle = ResourceBundle.getBundle("Language");
    private File file;
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private Dao<SudokuBoard> fileDao;
    private Dao<SudokuBoard> databaseDao;
    private FileChooser fileChooser;
    private static final Logger logger = Logger.getLogger(GameScene.class.getName());

    public void setLevelValue(int levelValue) {
        this.levelValue = levelValue;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }

    public static void setFromFile(boolean value) {
        fromFile = value;
    }

    public void setSudokuGrid() {
        this.sudokuGrid = (GridPane) SceneChange.getScene().getScene().lookup("#sudokuGrid");
    }


    //Cos jest nie tak z tym plikiem, zaczytywaniem, stanem borda
    //Sprobuj znowu zaczytac innego borda, zobacz czy dobrze zapamieta
    //I jakie rzeczy bedzie wypluwac do fieldow, bo na ten momment ma jedno a robi drugi i chuj wie czemu
    @FXML
    public void initialize() throws CloneNotSupportedException {
        setLevelValue(MenuControls.getCurrentLevel());
        if (fromFile) {
            this.board = MenuControls.getFileBoard();
            this.copyBoard = (SudokuBoard) this.board.clone();
            solver.solve(this.copyBoard);
        } else if (!MenuControls.getOpened() || MenuControls.getLastLevel() != this.levelValue) {
            solver.solve(board);
            this.copyBoard = (SudokuBoard) board.clone();
            this.board = lvl.chooseDifficulty(board, this.levelValue);
        } else {
            this.board = MenuControls.getOpenedBoard();
            this.copyBoard = (SudokuBoard) board.clone();
            solver.solve(this.copyBoard);
        }

        fillGrid();
    }


    private void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setText(String.valueOf(board.get(i, j)));
                if (board.get(i, j) != 0) {
                    textField.setDisable(true);
                }
                sudokuGrid.add(textField, j, i);
            }
        }
    }

    @FXML
    public void onFileSave(ActionEvent actionEvent) {
        fileChooser = new FileChooser();

        if (correctInput()) {
            saveBoard();
            try {
                file = fileChooser.showSaveDialog(SceneChange.getScene());
                fileDao = factory.getFileDao(file.getName());
                fileDao.write(board);
                logger.info("Game was saved in file");
            } catch (NullPointerException | DaoException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(bundle.getString("_error"));
                alert.showAndWait();
                logger.warning("Game was not save in file");
            }
        } else {
            logger.warning(bundle.getString("_badValue"));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }
    }

    @FXML
    public void onDatabaseSave(ActionEvent actionEvent) {
        fileChooser = new FileChooser();

        if (correctInput()) {
            saveBoard();
            try {
                file = fileChooser.showSaveDialog(SceneChange.getScene());
                databaseDao = factory.getDatabaseDao(file.getName());
                databaseDao.write(board);
                logger.info("Saved board to database");
            } catch (NullPointerException | DaoException e) {
                logger.warning("Can not save board to database");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(bundle.getString("_error"));
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }
    }

    @FXML
    public void onReturnClick(ActionEvent actionEvent) throws IOException {
        SceneChange.buildScene("/SudokuMenuScene.fxml", bundle);
        MenuControls.setOpened(true);
        MenuControls.setOpenedBoard(board);
        if (fromFile) {
            MenuControls.setLastLevel(0);
        } else {
            MenuControls.setLastLevel(levelValue);
        }
        fromFile = false;
        MenuControls.setFileBoard(null);
    }

    @FXML
    public void onConfrimClick(ActionEvent actionEvent) throws IOException {

        if (!correctInput()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
            logger.warning("Invalid input in board");
        } else {
            saveBoard();
            if (board.checkBoard()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(bundle.getString("_winner"));
                alert.showAndWait();
                logger.info("Win");
                SceneChange.buildScene("/SudokuMenuScene.fxml", bundle);
                MenuControls.setOpened(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(bundle.getString("_defeat"));
                alert.showAndWait();
                secondChance();
                logger.info("Lost");
            }
        }
    }

    public boolean correctInput() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String field = ((TextField) sudokuGrid.getChildren().get(i * 9 + j)).getText();
                if (!field.matches("[0-9]") || field.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String field = ((TextField) sudokuGrid.getChildren().get(i * 9 + j)).getText();
                if (!field.equals("")) {
                    board.set(i, j, Integer.parseInt(field));
                } else {
                    board.set(i, j, 0);
                }
            }
        }
    }

    public void secondChance() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (copyBoard.get(i, j) != board.get(i, j)) {
                    board.set(i, j, 0);
                }
            }
        }
        sudokuGrid.getChildren().clear();
        fillGrid();
    }

}
