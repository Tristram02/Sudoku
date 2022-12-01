package sudoku;


import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChange {

    private static Stage scene;


    public static Stage getScene() {
        return scene;
    }

    public static void setScene(Stage scene) {
        SceneChange.scene = scene;
    }

    public static Parent loadScene(String file) throws IOException {
        return new FXMLLoader(SceneChange.class.getResource(file)).load();
    }

    public static void buildScene(Stage scene, String file) throws IOException {
        setScene(scene);
        scene.setScene(new Scene(loadScene(file)));
        scene.show();
    }

    public static void buildScene(String file) throws IOException {
        scene.setScene(new Scene(loadScene(file)));
        scene.show();
    }
}
