package aquarium.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FishFactory {

    public static Fish createFish(double x, double y) {
        return new Fish(x, y,createFishImageView());
    }
    public static ImageView createFishImageView() {
        String imagePath = "/assets/fish1.png"; // Correct path to the image
        Image fishImage = new Image(FishFactory.class.getResourceAsStream(imagePath));

        ImageView fishImageView = new ImageView();
        fishImageView.setImage(fishImage);
        fishImageView.setPreserveRatio(true);
        fishImageView.setFitWidth(Config.getFish_Width());
        fishImageView.setFitHeight(Config.getFish_height());
        fishImageView.setVisible(false);
        return fishImageView;
    }
}