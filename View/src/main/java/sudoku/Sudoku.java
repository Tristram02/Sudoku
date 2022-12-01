package sudoku;


import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Sudoku extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneChange.buildScene(stage, "SudokuMenuScene.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
