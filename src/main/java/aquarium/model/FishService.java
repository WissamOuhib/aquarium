package aquarium.model;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FishService { //A fish service
    @Getter @Setter
    private Pane aquariumPane; // aquarium container -not the whole window-
    @Getter @Setter
    private static List<Fish> fishList = new ArrayList<>();
    private static FishService FishService_instance;

    private FishService(Pane aquariumPane) {
        this.aquariumPane = aquariumPane;
    }
    public static FishService getInstance(Pane aquariumPane) {
        if(FishService_instance == null) {
            FishService_instance = new FishService(aquariumPane);
        }
        return FishService_instance;
    }

    public Fish addFishWithRandomPosition() {
        Coordinate position = Coordinate.generateRandomCoordinates(aquariumPane);
        double randomX = position.getX();
        double randomY = position.getY();

        if(randomX!=0 && randomY!=0) {
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
