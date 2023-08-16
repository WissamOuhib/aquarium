package aquarium.controller;

import aquarium.model.*;
import aquarium.sound.SoundManager;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

public class AquariumController implements Initializable {

    @FXML @Getter @Setter
    private Pane aquariumPane;
    @FXML
    private Label maxFishMessage;
    @Getter @Setter
    private FishService fishService;
    @Getter @Setter
    private SoundManager soundManager;
    @Getter @Setter
    private Config config;

    //observateurs du changement des dimensions du aquariumPane
    private static List<AquariumPaneObserver> AquariumPaneObservers;
    private AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fishService = FishService.getInstance(aquariumPane);
        soundManager = SoundManager.get_instance();
        config = Config.getInstance();

        AquariumPaneObservers = new ArrayList<>();
        addObserver(Config.getInstance()); //la config observe les changement des dimensions du pane
        notifyObservers(); //config recupère les dimensions du pane

        //changement des dimensions
        aquariumPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            Config.updateAquariumDimensions(newValue.doubleValue(), aquariumPane.getHeight());
        });
        aquariumPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            Config.updateAquariumDimensions(aquariumPane.getWidth(), newValue.doubleValue());
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateFishPositions(); // Update fish nodes on the pane
            }
        };

        startAnimation();
        soundManager.playAmbientSound();
    }

    public void updateFishPositions() {
        aquariumPane.getChildren().clear(); // Clear the pane
        for (Fish fish : fishService.getFishList()) {
            fish.move();
            Node fishNode = fish.getImageView();
            fishNode.setLayoutX(fish.getX());
            fishNode.setLayoutY(fish.getY());
            aquariumPane.getChildren().add(fishNode);
        }
    }

    @FXML
    public void handleAddFish(){
        if (FishService.getFishList().size() < Config.getMaxFishCount()) {
            Fish newFish = fishService.addFishWithRandomPosition();
            if (newFish != null) {
                aquariumPane.getChildren().add(newFish.getImageView());
                soundManager.playFishPopInSound();
            }
        } else {
            displayMessageAnimation(); // message plus de place
        }
    }
    @FXML
    public void handleRemoveFish() {
        List<Fish> fishList = FishService.getFishList();
        if (!fishList.isEmpty()) {
            Fish fishToRemove = fishList.get(fishList.size() - 1); // Remove the last added fish
            aquariumPane.getChildren().remove(fishToRemove.getImageView());
            fishService.removeFish(fishToRemove);
            soundManager.playFishPopOutSound();
        }
    }

    public static void addObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.add(observer);
    }

    public static void removeObserver(AquariumPaneObserver observer) {
        AquariumPaneObservers.remove(observer);
    }

    public void notifyObservers() {
        AquariumPaneObservers.stream()
                .forEach(observer -> observer.onConfigChanged(aquariumPane.getWidth(), aquariumPane.getHeight()));
    }

    private void startAnimation() {
        animationTimer.start();
    }

    private void displayMessageAnimation() {
        maxFishMessage.setVisible(true); // Show the message
        //sound
        soundManager.playMaxFishReachedSound();

        // animation message
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), maxFishMessage);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // hide the message when the animation ends
        fadeOut.setOnFinished(event -> maxFishMessage.setVisible(false));

        // Play the animation
        fadeOut.play();
    }


}