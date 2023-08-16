package aquarium.model;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    // Coordinates will be saved in Sets and will be hashed for better performance
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
        do {
            System.out.println("loooooooooooooooooooooooooooooooooooooooooop");
            x = (int) generateRandomX(aquariumPane);
            y = (int) generateRandomY(aquariumPane);
        } while (hasOverlap(x, y));

        return new Coordinate(x,y);
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

        boolean isOverlapping = Config.getOccupiedCoordinates().stream()
                .anyMatch(occupiedCoordinate -> {
                    double occupiedX = occupiedCoordinate.getX();
                    double occupiedY = occupiedCoordinate.getY();
                    return x + fishWidth > occupiedX && x < occupiedX + fishWidth &&
                            y + fishHeight > occupiedY && y < occupiedY + fishHeight;
                });

        if (isOverlapping) {
            return true; // Overlapping
        }
        return false; // No overlapping
    }

    public static Set<Coordinate> calculateOccupiedSpace(double x, double y) {
        int gridX = (int) (x / Config.getGridCellSize());
        int gridY = (int) (y / Config.getGridCellSize());

        return IntStream.range(gridX, gridX + Config.getFish_Width() / Config.getGridCellSize())
                .boxed()
                .flatMap(i ->
                        IntStream.range(gridY, gridY + Config.getFish_height() / Config.getGridCellSize())
                                .mapToObj(j -> new Coordinate(i * Config.getGridCellSize(), j * Config.getGridCellSize())))
                .collect(Collectors.toSet());
    }

    public static void updateOccupiedSpace(double oldX, double oldY, double newX, double newY) {
        // Calculate old and new occupied coordinates
        Set<Coordinate> oldOccupiedCoordinates = Coordinate.calculateOccupiedSpace(oldX, oldY);
        Set<Coordinate> newOccupiedCoordinates = Coordinate.calculateOccupiedSpace(newX, newY);

        // Calculate the coordinates that need to be added and removed
        Set<Coordinate> coordinatesToRemove = oldOccupiedCoordinates.stream()
                .filter(coordinate -> !newOccupiedCoordinates.contains(coordinate))
                .collect(Collectors.toSet());

        Set<Coordinate> coordinatesToAdd = newOccupiedCoordinates.stream()
                .filter(coordinate -> !oldOccupiedCoordinates.contains(coordinate))
                .collect(Collectors.toSet());

        // Remove old occupied coordinates and add new occupied ones
        Config.getOccupiedCoordinates().removeAll(coordinatesToRemove);
        Config.getOccupiedCoordinates().addAll(coordinatesToAdd);
    }

}
