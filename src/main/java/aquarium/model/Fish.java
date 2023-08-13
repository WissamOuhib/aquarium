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

//    public void move() {
//        // Calculate the new position
//        int newX = (int)x+100;
//        int newY = (int)y+100;
//
//        // Clear the fish's previous occupied space
//        clearOccupiedSpace((int)x,(int)y);
//
//        // Mark the new occupied space
//        markOccupiedSpace(newX, newY);
//
//        // Update the fish's position and other properties
//    }
//
//    private void markOccupiedSpace(int x, int y) {
//        Config.markOccupied(x, y);
//        this.x = x;
//        this.y = y;
//    }
//
//    private void clearOccupiedSpace(int x, int y) {
//        Config.markVacant(x, y);
//    }
}