package sudoku;


import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;

public class Sudoku extends Application {

    private ResourceBundle bundle = ResourceBundle.getBundle("sudoku/Language");
    @Override
    public void start(Stage stage) throws IOException {
        SceneChange.buildScene(stage, "SudokuMenuScene.fxml", bundle);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
