package sudoku;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class MenuControls {

    @FXML
    private ToggleGroup levelGroup;
    private int currentLevel = 1;

    @FXML
    private final GameScene GS = new GameScene();

    private void setCurrentLevel() {
        RadioButton selectedLevel = (RadioButton) levelGroup.getSelectedToggle();
        String level = selectedLevel.getText();
        currentLevel = level.charAt(level.length()-1) - '0';
    }
    private int getCurrentLevel() {
        return currentLevel;
    }
    @FXML
    public void onStartClick(ActionEvent actionEvent) throws IOException, CloneNotSupportedException {
        actionEvent.consume();
        setCurrentLevel();
        GS.setLevelValue(getCurrentLevel());
        SceneChange.buildScene("SudokuGameScene.fxml");
        GS.initialize();
    }

}
