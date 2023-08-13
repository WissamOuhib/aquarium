package aquarium.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FishFactory {
    public static ImageView createFishImageView(double width, double height) {
        String imagePath = "/assets/fish1.png"; // Correct path to the image
        Image fishImage = new Image(FishFactory.class.getResourceAsStream(imagePath));

      //  System.out.println("wwwwwwwwwwwwwww "+fishImage.getUrl()+"   "+fishImage.getWidth());

        ImageView fishImageView = new ImageView();
        fishImageView.setImage(fishImage);
        fishImageView.setPreserveRatio(true);
        fishImageView.setFitWidth(width);
        fishImageView.setFitHeight(height);
        fishImageView.setVisible(false);
        return fishImageView;
    }
}