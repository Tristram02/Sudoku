package sudoku;


import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Sudoku extends Application {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("Language");
    private static final Logger logger = Logger.getLogger(Sudoku.class.getName());

    static {
        String path = Objects.requireNonNull(Sudoku.class.getClassLoader().getResource("logging.properties")).getFile();
        System.setProperty("java.util.logging.config.file", path);
    }


    @Override
    public void start(Stage stage) throws IOException {
        SceneChange.buildScene(stage, "/SudokuMenuScene.fxml", bundle);
    }

    public static void main(String[] args) {
        FileHandler fh = null;
        try {
            fh = new FileHandler("Log.txt");
            Logger.getLogger("").addHandler(fh);
        } catch (SecurityException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Error");
            alert.showAndWait();
        } finally {
            if (fh != null) {
                fh.close();
            }
        }

        logger.info("App started");
        launch(args);
    }
}
