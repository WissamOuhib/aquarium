module com.aquarium.aquarium {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens aquarium to javafx.fxml;
    exports aquarium;
    exports aquarium.controller;
    opens aquarium.controller to javafx.fxml;
}