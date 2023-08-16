package aquarium.model;

import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

public class Fish {
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter @Setter
    private double speedX;
    @Getter @Setter
    private double speedY;
    @Getter @Setter
    private ImageView imageView;

    public Fish(double x, double y, ImageView imageView) {
        this.x = x;
        this.y = y;
        this.imageView = imageView;
    }

    public void initializeFish() {
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setVisible(true);
        speedX = Math.random() * 4 - 2; // Random value between -2 and 2
        speedY = Math.random() * 4 - 2;
    }

    public void move() {
        // Calculate new position
        double newX = x + speedX;
        double newY = y + speedY;
        boolean changeXDirection = (speedX >0); //revert image if fish goes to the right

        // Update coordinates
        //instead of calling this here, i get a snapshot of coordinates when i spawn fish that i call in generateRandomCoordinates
       // Coordinate.updateOccupiedSpace(x,y, newX, newY);

        // Check for collisions with other fish
        if (collidesWithOtherFish(newX, newY)) {
            // Change direction if colliding with other fish
            speedX *= -1; // Change horizontal direction
            speedY *= -1; // Change vertical direction
            changeXDirection = true;
        }

        // Check for collisions with aquarium borders
        double fish_x_edge; //physical limit of the fish (considering fish image size)
        double fish_y_edge;

        if(speedX > 0) fish_x_edge = newX + Config.getFish_Width(); //movement to the right
        else fish_x_edge = newX; //movement to the left

        if (newX < 0 || fish_x_edge > Config.getAquarium_Width()) { //hits aquarium horizontal border
            speedX *= -1; // Change horizontal direction
            changeXDirection = true;
        }

        if(speedY > 0) fish_y_edge = newY + Config.getFish_height(); //movement to bottom
        else fish_y_edge = newY; //movement to top

        if (newY < 0 || fish_y_edge > Config.getAquarium_height()) { //hits aquarium vertical border
            speedY *= -1; // Change vertical direction
        }

        // Update fish position
        x += speedX;
        y += speedY;

        // Invert ImageView horizontally when changing direction
        if (changeXDirection) {
            if (speedX > 0) { // Fish is moving to the right
                imageView.setScaleX(-1); // Flip horizontally
            } else { // Fish is moving to the left
                imageView.setScaleX(1); // Reset scaling
            }
        }
    }

    private boolean collidesWithOtherFish(double newX, double newY) {
        Set<Coordinate> myOccupiedCoordinates = Coordinate.calculateOccupiedSpace(this.x, this.y);

        return FishService.getFishList().stream()
                .filter(otherFish -> otherFish != this) // Skip checking against itself
                .anyMatch(otherFish -> {
                    Set<Coordinate> otherFishOccupiedCoordinates = Coordinate.calculateOccupiedSpace(otherFish.x, otherFish.y);
                    return checkOverlap(myOccupiedCoordinates, otherFishOccupiedCoordinates);
                });
    }

    private boolean checkOverlap(Set<Coordinate> coordinates1, Set<Coordinate> coordinates2) {
        return coordinates1.stream()
                .anyMatch(coordinates2::contains);
    }

}