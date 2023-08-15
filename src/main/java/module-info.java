module aquarium {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires javafx.media;

    opens aquarium to javafx.fxml;
    exports aquarium;
    exports aquarium.controller;
    opens aquarium.controller to javafx.fxml;
}