package aquarium.model;

public interface AquariumPaneObserver {
    /*
    * l'interet de cette interface est de pouvoir gérer les changements des dimensions de l'acquarium
    * suite à une redimension de la fenêtre, afin que les poissons nagent toujours dans les limites de l'aquarium
    * */
    void onConfigChanged(double aquarium_width, double aquarium_height);
}
