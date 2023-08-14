package aquarium.model;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class Fish {
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter
    private double speedX;
    @Getter
    private double speedY;
    @Getter
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
        speedX = Config.getFish_x_speed();
        speedY = Config.getFish_y_speed();
    }


    public void move() {
        // Calculate new position
        double newX = x + speedX;
        double newY = y + speedY;
        boolean changeXDirection = (speedX >0); //s'il va à droite au départ, j'inverse l'image

        // Check for collisions with other fish
        if (collidesWithOtherFish(newX, newY)) {
            // Change direction if colliding with other fish
            speedX *= -1; // Change horizontal direction
            speedY *= -1; // Change vertical direction
            changeXDirection = true;
        }

        // Check for collisions with aquarium borders
        if (newX < 0 || newX > Config.getAquarium_Width()) {
            speedX *= -1; // Change horizontal direction
            changeXDirection = true;
        }
        if (newY < 0 || newY > Config.getAquarium_height()) {
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
        Set<Coordinate> myOccupiedCoordinates = calculateOccupiedSpace();

        for (Fish otherFish : FishService.getFishList()) {
            if (otherFish != this) { // Skip checking against itself
                Set<Coordinate> otherFishOccupiedCoordinates = otherFish.calculateOccupiedSpace();

                // Check for overlap between occupied spaces
                if (checkOverlap(myOccupiedCoordinates, otherFishOccupiedCoordinates)) {
                    return true; // Collision detected with another fish
                }
            }
        }
        return false; // No collision detected with other fish
    }

    public Set<Coordinate> calculateOccupiedSpace() {
        Set<Coordinate> occupiedCoordinates = new HashSet<>();
        int gridX = (int) (x / Config.getGridCellSize());
        int gridY = (int) (y / Config.getGridCellSize());

        for (int i = gridX; i < gridX + Config.getFish_Width() / Config.getGridCellSize(); i++) {
            for (int j = gridY; j < gridY + Config.getFish_height() / Config.getGridCellSize(); j++) {
                occupiedCoordinates.add(new Coordinate(i * Config.getGridCellSize(), j * Config.getGridCellSize()));
            }
        }

        return occupiedCoordinates;
    }

    private boolean checkOverlap(Set<Coordinate> coordinates1, Set<Coordinate> coordinates2) {
        for (Coordinate coord1 : coordinates1) {
            if (coordinates2.contains(coord1)) {
                return true; // Overlapping detected
            }
        }
        return false; // No overlapping detected
    }

//    private boolean collidesWithOtherFish(double newX, double newY) {
//        // Check if the new position collides with any other fish's position
//        for (Coordinate occupiedCoordinate : Config.getOccupiedCoordinates()) {
//            if (occupiedCoordinate.getX() == newX && occupiedCoordinate.getY() == newY) {
//                return true;
//            }
//        }
//        return false;
//    }

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