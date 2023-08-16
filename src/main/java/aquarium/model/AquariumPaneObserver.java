package aquarium.model;

public interface AquariumPaneObserver {
    /*
    * the main purpose of having AquariumPaneObserver interface
    * is to handle aquariumPane dimensions changes
    * and ensure that fish still swim within aquarium's borders
    * */
    void onConfigChanged(double aquarium_width, double aquarium_height);
}
