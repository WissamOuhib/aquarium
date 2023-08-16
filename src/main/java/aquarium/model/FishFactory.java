package aquarium.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishFactory {
    private static final List<String> imagePaths = Arrays.asList(
            "/assets/fish1.png",
            "/assets/fish2.png",
            "/assets/fish3.png",
            "/assets/fish4.png",
            "/assets/fish5.png",
            "/assets/fish6.png",
            "/assets/fish7.png"
    );

    public static Fish createFish(double x, double y) {
        return new Fish(x, y,createFishImageView());
    }
    public static ImageView createFishImageView() {
        String randomImagePath = imagePaths.get(new Random().nextInt(imagePaths.size()));
        ImageView fishImageView = new ImageView();
        try {
            Image fishImage = new Image(FishFactory.class.getResourceAsStream(randomImagePath));
            fishImageView.setImage(fishImage);
            fishImageView.setPreserveRatio(true);
            fishImageView.setFitWidth(Config.getFish_Width());
            fishImageView.setFitHeight(Config.getFish_height());
            fishImageView.setVisible(false);
        } catch (NullPointerException e) {
            System.out.println("L'image du poisson est introuvable");
        }
        return fishImageView;
    }
}