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
        double randomX = generateRandomX();
        double randomY = generateRandomY();

        ImageView fishImageView = FishFactory.createFishImageView(Config.getFish_Width(), Config.getFish_height());
        Fish newFish = new Fish(randomX, randomY, fishImageView);
        newFish.initializeFish();

        return newFish;
    }

    private double generateRandomX() {
        double x =  Math.random() * (aquariumPane.getWidth() - Config.getFish_Width());
        System.out.println("11111111111111111111111 "+aquariumPane.getWidth()+"   "+x);

        return x;
    }

    private double generateRandomY() {
        double y = Math.random() * (aquariumPane.getHeight() - Config.getFish_height());
        System.out.println("22222222222222222222222 "+aquariumPane.getHeight()+"   "+y);

        return y;
    }
}
