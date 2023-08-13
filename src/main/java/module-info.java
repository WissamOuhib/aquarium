module com.aquarium.aquarium {
    requires javafx.controls;
    requires javafx.fxml;


    opens aquarium to javafx.fxml;
    exports aquarium;
}