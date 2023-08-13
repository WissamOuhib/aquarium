package aquarium.model;

import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Fish {
    @Getter
    private double x;
    @Getter
    private double y;
    @Getter
    private ImageView imageView;

    public void initializeFish() {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setVisible(true);
    }
}