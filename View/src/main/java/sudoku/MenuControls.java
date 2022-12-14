package sudoku;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

public class MenuControls {

    @FXML
    private ComboBox lang;
    @FXML
    private ToggleGroup levelGroup;
    private int currentLevel = 1;

    private ResourceBundle bundle = ResourceBundle.getBundle("sudoku/Language");

    @FXML
    private final GameScene gs = new GameScene();

    @FXML
    private void initialize() throws IOException {
        lang.getItems().addAll(
                bundle.getString("_polish"),
                bundle.getString("_english")
        );
        bundle = ResourceBundle.getBundle("sudoku/Language");
    }

    @FXML
    public void onLangChange(ActionEvent actionEvent) throws IOException {

        try {
            String langName = lang.getSelectionModel().getSelectedItem().toString();
            if (langName.equals(bundle.getString("_english"))) {
                Locale.setDefault(new Locale("en"));
                initialize();
            } else if (langName.equals(bundle.getString("_polish"))) {
                Locale.setDefault(new Locale("pl"));
                initialize();
            }

            SceneChange.buildScene("SudokuMenuScene.fxml", bundle);
        } catch (NullPointerException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(bundle.getString("_error"));
            alert.showAndWait();
        }

    }

    private void setCurrentLevel() {
        RadioButton selectedLevel = (RadioButton) levelGroup.getSelectedToggle();
        String level = selectedLevel.getText();
        currentLevel = level.charAt(level.length() - 1) - '0';
    }

    private int getCurrentLevel() {
        return currentLevel;
    }

    @FXML
    public void onStartClick(@NotNull ActionEvent actionEvent) throws IOException, CloneNotSupportedException {
        actionEvent.consume();
        setCurrentLevel();
        gs.setLevelValue(getCurrentLevel());
        SceneChange.buildScene("SudokuGameScene.fxml", bundle);
        gs.initialize();
    }

}
