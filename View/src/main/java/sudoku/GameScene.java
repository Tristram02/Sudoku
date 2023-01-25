package sudoku;


import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;
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


    @FXML
    public void initialize() throws CloneNotSupportedException, NoSuchMethodException {
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


    private void fillGrid() throws NoSuchMethodException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();

                JavaBeanIntegerProperty value = JavaBeanIntegerPropertyBuilder.create().bean(board.getField(i,j)).name("value").build();
                Bindings.bindBidirectional(textField.textProperty(),value, new NumberStringConverter());

                UnaryOperator<TextFormatter.Change> filter = change -> {
                    String text = change.getControlNewText();
                    if (text.matches("[1-9]*") && change.getControlNewText().length() <= 1) {
                        return change;
                    }
                    return null;
                };
                textField.setTextFormatter(new TextFormatter<>(filter));

                if (board.getFieldValue(i, j) == 0) {
                    textField.setText("");
                } else {
                    textField.setText(String.valueOf(board.getFieldValue(i, j)));
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
            try {
                file = fileChooser.showSaveDialog(SceneChange.getScene());
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".db")) {
                   file = new File(filePath + ".db");
                }
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
    public void onConfrimClick(ActionEvent actionEvent) throws IOException, NoSuchMethodException {

        if (!correctInput()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
            logger.warning("Invalid input in board");
        } else {
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
                if (!field.matches("[0-9]|^$")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void secondChance() throws NoSuchMethodException {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (copyBoard.getFieldValue(i, j) != board.getFieldValue(i, j)) {
                    board.setField(i, j, 0);
                }
            }
        }
        sudokuGrid.getChildren().clear();
        fillGrid();
    }


}
