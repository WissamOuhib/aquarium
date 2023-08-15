package aquarium.controller;

import aquarium.model.Config;
import aquarium.model.AquariumPaneObserver;
import aquarium.model.Fish;
import aquarium.model.FishService;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
    private AnimationTimer animationTimer;

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

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateFishPositions(); // Update fish nodes on the pane
            }
        };

        startAnimation();
    }

    private void updateFishPositions() {
        aquariumPane.getChildren().clear(); // Clear the pane
        for (Fish fish : fishService.getFishList()) {
            fish.move();
         //   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!  "+fish.getX());
            Node fishNode = fish.getImageView();
            fishNode.setLayoutX(fish.getX());
            fishNode.setLayoutY(fish.getY());
            aquariumPane.getChildren().add(fishNode);
        }
    }

    @FXML
    private void handleAddFish(){
        Fish newFish = fishService.addFishWithRandomPosition();
        if(newFish!=null) {
            aquariumPane.getChildren().add(newFish.getImageView());
        }
    }
    @FXML
    private void handleRemoveFish() {
        List<Fish> fishList = FishService.getFishList();
        if (!fishList.isEmpty()) {
            Fish fishToRemove = fishList.get(fishList.size() - 1); // Remove the last added fish
            aquariumPane.getChildren().remove(fishToRemove.getImageView());
            fishService.removeFish(fishToRemove);
        }
    }

    public static void addObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.add(observer);
    }

    public static void removeObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.remove(observer);
    }

    private void notifyObservers() {
        for (AquariumPaneObserver observer : AquariumPaneObservers) {
            observer.onConfigChanged(aquariumPane.getWidth(), aquariumPane.getHeight());
        }
    }

    private void startAnimation() {
        animationTimer.start();
    }
}