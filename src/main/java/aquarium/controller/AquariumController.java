package aquarium.controller;

import aquarium.model.Config;
import aquarium.model.AquariumPaneObserver;
import aquarium.model.Fish;
import aquarium.model.FishService;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AquariumController {
//    @FXML
//    private Label welcomeText;
//
//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }

    @FXML
    private Pane aquariumPane;
    private FishService fishService;

    //observateurs du changement des dimensions du aquariumPane
    private static List<AquariumPaneObserver> AquariumPaneObservers;

    public void init() {
        fishService = new FishService(aquariumPane);
        AquariumPaneObservers = new ArrayList<>();
        addObserver(Config.getInstance()); //la config observe les changement des dimensions du pane
        notifyObservers(); //config recupère les dimensions du pane

        //changement des dimensions
        aquariumPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            Config.getInstance().updateAquariumDimensions(newValue.doubleValue(), aquariumPane.getHeight());
        });
        aquariumPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            Config.getInstance().updateAquariumDimensions(aquariumPane.getWidth(), newValue.doubleValue());
        });
    }
    public void handleAddFish(){

      //  System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx "+aquariumPane.getWidth());
      //  System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy "+fishService.getAquariumPane().getWidth());
        Fish newFish = fishService.addFishWithRandomPosition();
        aquariumPane.getChildren().add(newFish.getImageView());
    }

    public static void addObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.add(observer);
    }

    public static void removeObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.remove(observer);
    }

    private void notifyObservers() {
        for (AquariumPaneObserver observer : AquariumPaneObservers) {
            System.out.println("xxxxxxxxxxxxxxxxxx "+observer);
            observer.onConfigChanged(aquariumPane.getWidth(), aquariumPane.getHeight());
        }
    }
}