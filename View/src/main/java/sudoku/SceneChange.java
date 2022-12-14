package sudoku;


import java.io.IOException;
import java.util.ResourceBundle;

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

    public static Parent loadScene(String file, ResourceBundle bundle) throws IOException {
        return new FXMLLoader(SceneChange.class.getResource(file), bundle).load();
    }

    public static void buildScene(Stage scene, String file, ResourceBundle bundle) throws IOException {
        setScene(scene);
        scene.setScene(new Scene(loadScene(file, bundle)));
        scene.show();
    }

    public static void buildScene(String file, ResourceBundle bundle) throws IOException {
        scene.setScene(new Scene(loadScene(file, bundle)));
        scene.show();
    }
}
