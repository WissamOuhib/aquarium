package aquarium.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


public class Config implements AquariumPaneObserver { //la config est singleton et observe les changements de la fenetre de l'aquarium
    @Getter
    private static final int gridCellSize = 10;
    @Getter
    private static final int Fish_Width = 100;
    @Getter
    private static final int Fish_height = 100;
    @Getter
    private static final double Fish_x_speed = Math.random() * 4 - 2; // Random value between -1 and 1
    @Getter
    private static final double Fish_y_speed = Math.random() * 4 - 2;
   // @Getter
   // private static int gridCellSize = 100;
    @Getter @Setter
    private static double Scene_Width = 1200;
    @Getter @Setter
    private static double Scene_height = 700;
    @Getter @Setter
    private static double aquarium_Width;
    @Getter @Setter
    private static double aquarium_height;
    @Getter @Setter
    private static int maxFishCount = 20; // nombre max de poissons
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

    public static boolean isCoordinateOccupied(double x, double y) {
        return occupiedCoordinates.contains(new Coordinate(x, y));
    }

    public static void addOccupiedCoordinate(double x, double y) {
        int gridX = (int) (x / gridCellSize);
        int gridY = (int) (y / gridCellSize);

        // Mark the occupied space for the fish's dimensions
        for (int i = gridX; i < gridX + Fish_Width / gridCellSize; i++) {
            for (int j = gridY; j < gridY + Fish_height / gridCellSize; j++) {
                occupiedCoordinates.add(new Coordinate(i * gridCellSize, j * gridCellSize));
            }
        }
    }

        public static boolean canSpawnMoreFish() {
        return spawnedFishCount < maxFishCount;
    }

    public static void incrementSpawnedFishCount() {
        spawnedFishCount++;
    }

}
