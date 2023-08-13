package aquarium;

import aquarium.controller.AquariumController;
import aquarium.model.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AquariumApplication extends Application {
    public static String viewsrc= "view/aquarium-view.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AquariumApplication.class.getResource(viewsrc));
        Scene scene = new Scene(fxmlLoader.load(), Config.getScene_Width(), Config.getScene_height());
        stage.setTitle("Aquarium Game");
        stage.setScene(scene);
        stage.show();

        AquariumController controller = fxmlLoader.getController();
        controller.init();
    }

    public static void main(String[] args) {
        launch();
    }
}