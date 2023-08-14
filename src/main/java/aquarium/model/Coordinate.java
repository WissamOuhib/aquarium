package aquarium.model;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

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
        do {
            x = (int) generateRandomX(aquariumPane);
            y = (int) generateRandomY(aquariumPane);
        } while (hasOverlap(x, y));

        return new Coordinate(x,y);

        // Set the fish's position and other properties
    }

    private static double generateRandomX(Pane aquariumPane) {
        double x =  Math.random() * (aquariumPane.getWidth() - Config.getFish_Width());
        System.out.println("11111111111111111111111 "+aquariumPane.getWidth()+"   "+x);

        return (int)x;
    }

    private static double generateRandomY(Pane aquariumPane) {
        double y = Math.random() * (aquariumPane.getHeight() - Config.getFish_height());
        System.out.println("22222222222222222222222 "+aquariumPane.getHeight()+"   "+y);

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

}
