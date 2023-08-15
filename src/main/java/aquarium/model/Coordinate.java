package aquarium.model;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Coordinate {
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Override hashCode and equals methods
    // This allows using Coordinate objects as keys in collections like Set
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate coordinate = (Coordinate) obj;
        return Double.compare(coordinate.x, x) == 0 && Double.compare(coordinate.y, y) == 0;
    }

    public static Coordinate generateRandomCoordinates(Pane aquariumPane) {
        if (!Config.canSpawnMoreFish()) {
            // No more space for additional fish
            return new Coordinate(0,0);
        }

        double x, y;
        do { //System.out.println("loooooooooooooooooooooooooooooooooooooooooop");
            x = (int) generateRandomX(aquariumPane);
            y = (int) generateRandomY(aquariumPane);
        } while (hasOverlap(x, y));

        return new Coordinate(x,y);

        // Set the fish's position and other properties
    }

    private static double generateRandomX(Pane aquariumPane) {
        double x =  Math.random() * (aquariumPane.getWidth() - Config.getFish_Width());
        return (int)x;
    }

    private static double generateRandomY(Pane aquariumPane) {
        double y = Math.random() * (aquariumPane.getHeight() - Config.getFish_height());
        return (int)y;
    }

    public static boolean hasOverlap(double x, double y) {
        double fishWidth = Config.getFish_Width();
        double fishHeight = Config.getFish_height();

        // Check for overlap with occupied coordinates
        for (Coordinate occupiedCoordinate : Config.getOccupiedCoordinates()) {
            double occupiedX = occupiedCoordinate.getX();
            double occupiedY = occupiedCoordinate.getY();

            if (x + fishWidth > occupiedX && x < occupiedX + fishWidth &&
                    y + fishHeight > occupiedY && y < occupiedY + fishHeight) {
                return true; // Overlapping
            }
        }

        return false; // No overlapping
    }

    public static Set<Coordinate> calculateOccupiedSpace(double x, double y) {
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

    public static void updateOccupiedSpace(double oldX, double oldY, double newX, double newY) {
        // Calculate old occupied coordinates
        Set<Coordinate> oldOccupiedCoordinates = Coordinate.calculateOccupiedSpace(oldX, oldY);
        // Calculate new occupied coordinates
        Set<Coordinate> newOccupiedCoordinates = Coordinate.calculateOccupiedSpace(newX, newY);

        // Calculate the coordinates that need to be added and removed
        Set<Coordinate> coordinatesToRemove = new HashSet<>(oldOccupiedCoordinates);
        coordinatesToRemove.removeAll(newOccupiedCoordinates);
        Set<Coordinate> coordinatesToAdd = new HashSet<>(newOccupiedCoordinates);
        coordinatesToAdd.removeAll(oldOccupiedCoordinates);

        // Remove old occupied coordinates
        Config.getOccupiedCoordinates().removeAll(coordinatesToRemove);

        // Add new occupied coordinates
        Config.getOccupiedCoordinates().addAll(coordinatesToAdd);
    }

}
