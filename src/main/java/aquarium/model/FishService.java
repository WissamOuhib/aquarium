package aquarium.model;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FishService { //les services
    @Getter
    private Pane aquariumPane; // le conteneur de l'aquarium
    @Getter
    private static List<Fish> fishList = new ArrayList<>();
    public FishService(Pane aquariumPane) {
        this.aquariumPane = aquariumPane;
    }

    public Fish addFishWithRandomPosition() {
        Coordinate position = Coordinate.generateRandomCoordinates(aquariumPane);
        double randomX = position.getX();
        double randomY = position.getY();

        if(randomX!=0 && randomY!=0) {
          //  ImageView fishImageView = FishFactory.createFishImageView();
            Fish newFish = FishFactory.createFish(randomX, randomY);
            newFish.initializeFish();
            Config.addOccupiedCoordinate(randomX, randomY);
            Config.incrementSpawnedFishCount();
            fishList.add(newFish);
            newFish.move();
            return newFish;
        }
        return null;
    }

    public void removeFish(Fish fish) {
        fishList.remove(fish);
        Config.freeOccupiedCoordinate(fish.getX(), fish.getY());
        Config.decrementSpawnedFishCount();
    }


}
