package aquarium.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Config implements AquariumPaneObserver {
    @Getter
    private static final double Fish_Width = 100;
    @Getter
    private static final double Fish_height = 100;
    @Getter @Setter
    private static double Scene_Width = 1200;
    @Getter @Setter
    private static double Scene_height = 700;
    @Getter @Setter
    private static double aquarium_Width;
    @Getter @Setter
    private static double aquarium_height;

    private static Config Config_instance;
    private Config() {

    }
    public static Config getInstance() {
        if(Config_instance == null) Config_instance = new Config();
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
}
