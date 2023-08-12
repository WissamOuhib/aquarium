module com.aquarium.aquarium {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.aquariumapp to javafx.fxml;
    exports com.aquariumapp;
}