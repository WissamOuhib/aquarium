package aquarium.model;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

//Config is a singleton class. it observes aquariumPane changes
public class Config implements AquariumPaneObserver {
    @Getter
    private static final int gridCellSize = 10; // defines the proximity threshold for collision detection
    @Getter
    private static final int Fish_Width = 100;
    @Getter
    private static final int Fish_height = 100;
    @Getter @Setter
    private static double Scene_Width = 1200;
    @Getter @Setter
    private static double Scene_height = 700;
    @Getter @Setter
    private static double aquarium_Width;
    @Getter @Setter
    private static double aquarium_height;
    @Getter @Setter
    private static int maxFishCount = 20; // maximum number of fish to spawn
    @Getter @Setter
    private static int spawnedFishCount = 0;
    @Getter @Setter
    private static Set<Coordinate> occupiedCoordinates = new HashSet<>();


    private static Config Config_instance;
    private Config() {}
    public static Config getInstance() {
        if(Config_instance == null) {
            Config_instance = new Config();
        }
        return Config_instance;
    }

    // Method to update dimensions
    public static void updateAquariumDimensions(double width, double height) {
        setAquarium_Width(width);
        setAquarium_height(height);
    }

    @Override
    public void onConfigChanged(double width, double height) {
        updateAquariumDimensions(width, height);
    }

    public static void addOccupiedCoordinate(double x, double y) {
        int gridX = (int) (x / gridCellSize);
        int gridY = (int) (y / gridCellSize);

        // Mark the occupied space for the fish's dimensions
        IntStream.range(gridX, gridX + Fish_Width / gridCellSize)
                .forEach(i -> IntStream.range(gridY, gridY + Fish_height / gridCellSize)
                        .forEach(j -> occupiedCoordinates.add(new Coordinate(i * gridCellSize, j * gridCellSize))));
    }

    public static void freeOccupiedCoordinate(double x, double y) {
        int gridX = (int) (x / gridCellSize);
        int gridY = (int) (y / gridCellSize);

        // Free the occupied space for the fish's dimensions
        IntStream.range(gridX, gridX + Fish_Width / gridCellSize)
                .forEach(i -> IntStream.range(gridY, gridY + Fish_height / gridCellSize)
                        .forEach(j -> occupiedCoordinates.remove(new Coordinate(i * gridCellSize, j * gridCellSize))));
    }

    public static boolean canSpawnMoreFish() {
        return spawnedFishCount < maxFishCount;
    }

    public static void incrementSpawnedFishCount() {
        spawnedFishCount++;
    }

    public static void decrementSpawnedFishCount() {
        spawnedFishCount--;
    }

}
