package aquarium.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FishService { //les services
    private Pane aquariumPane; // le conteneur de l'aquarium

    public Pane getAquariumPane() {
        return aquariumPane;
    }

    public FishService(Pane aquariumPane) {
        this.aquariumPane = aquariumPane;
      //  System.out.println("222222222222222222222222222 "+aquariumPane);
    }

    public Fish addFishWithRandomPosition() {
        //System.out.println("!!!!!!!!!!!!!!!!!   "+ Config.getInstance().getGrid().length);
        Coordinate position = Coordinate.generateRandomCoordinates(aquariumPane);
        double randomX = position.getX();
        double randomY = position.getY();

        if(randomX!=0 && randomY!=0) {
            ImageView fishImageView = FishFactory.createFishImageView(Config.getFish_Width(), Config.getFish_height());
            Fish newFish = new Fish(randomX, randomY, fishImageView);
            newFish.initializeFish();
            return newFish;
        }
        return null;
    }


//    private Coordinate spawn() {
//        double x, y;
//        do {
//            x = generateRandomX();
//            y = generateRandomY();
//        } while (Coordinate.hasOverlap(x, y));
//
//        Config.addOccupiedCoordinate(x, y);
//
//        return new Coordinate(x,y);
//
//        // Set the fish's position and other properties
//    }
}
