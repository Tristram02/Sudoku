package sudoku;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import sudoku.exceptions.DaoException;


public class MenuControls {

    private final Authors authors = new Authors();
    @FXML
    private ComboBox lang;
    @FXML
    private ToggleGroup levelGroup;
    private static int currentLevel = 1;
    private static int lastLevel = 1;
    private static boolean opened = false;
    private static SudokuBoard openedBoard;
    private static SudokuBoard fileBoard;
    private FileChooser fileChooser;
    private Dao<SudokuBoard> fileDao;
    private Dao<SudokuBoard> jdbcFileDao;
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private static final Logger logger = Logger.getLogger(MenuControls.class.getName());

    private ResourceBundle bundle = ResourceBundle.getBundle("Language");

    @FXML
    private final GameScene gs = new GameScene();

    @FXML
    private void initialize() throws IOException {
        lang.getItems().addAll(
                bundle.getString("_polish"),
                bundle.getString("_english")
        );
        bundle = ResourceBundle.getBundle("Language");
    }

    @FXML
    public void onLangChange(ActionEvent actionEvent) throws IOException {

        try {
            String langName = lang.getSelectionModel().getSelectedItem().toString();
            if (langName.equals(bundle.getString("_english"))) {
                Locale.setDefault(new Locale("en"));
            } else if (langName.equals(bundle.getString("_polish"))) {
                Locale.setDefault(new Locale("pl"));
            }

            initialize();
            SceneChange.buildScene("/SudokuMenuScene.fxml", bundle);
            logger.info("Language has changed");
        } catch (NullPointerException e) {
            logger.warning("Language was not selected");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }

    }

    public static void setLastLevel(int level) {
        lastLevel = level;
    }

    public static int getLastLevel() {
        return lastLevel;
    }

    public void setCurrentLevel() {
        RadioButton selectedLevel = (RadioButton) levelGroup.getSelectedToggle();
        String level = selectedLevel.getText();
        currentLevel = level.charAt(level.length() - 1) - '0';
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setOpened(boolean value) {
        opened = value;
    }

    public static boolean getOpened() {
        return opened;
    }

    public static void setOpenedBoard(SudokuBoard board) {
        openedBoard = board;
    }

    public static SudokuBoard getOpenedBoard() {
        return openedBoard;
    }

    public static SudokuBoard getFileBoard() {
        return fileBoard;
    }

    public static void setFileBoard(SudokuBoard board) {
        fileBoard = board;
    }

    @FXML
    public void onStartClick(ActionEvent actionEvent) throws IOException {
        setCurrentLevel();
        GameScene.setFromFile(fileBoard != null);
        SceneChange.buildScene("/SudokuGameScene.fxml", bundle);
        logger.info("Game started");
    }

    @FXML
    public void onAuthorsClick(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setTitle(bundle.getString("_authors"));
        alert.setContentText(authors.getObject("1") + "\n" + authors.getObject("2"));
        alert.showAndWait();
        logger.info("Authors was shown");
    }

    @FXML
    public void onFileUpload(ActionEvent actionEvent) {

        String file;
        fileChooser = new FileChooser();

        try {
            file = fileChooser.showOpenDialog(SceneChange.getScene()).getName();
            fileDao = factory.getFileDao(file);
            fileBoard = fileDao.read();
            levelGroup.getToggles().forEach(toggle -> {
                Node btn = (Node) toggle;
                btn.setDisable(true);
            });

        } catch (NullPointerException | DaoException e) {
            logger.warning("Can not read a file");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }
    }

    @FXML
    public void onDatabaseUpload(ActionEvent actionEvent) {
        String file;
        fileChooser = new FileChooser();

        try {
            file = fileChooser.showOpenDialog(SceneChange.getScene()).getName();
            jdbcFileDao = factory.getDatabaseDao(file);
            fileBoard = jdbcFileDao.read();
            levelGroup.getToggles().forEach(toggle -> {
                Node btn = (Node) toggle;
                btn.setDisable(true);
            });
        } catch (NullPointerException | DaoException e) {
            logger.warning("Can not read board from database!");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }
    }
}

