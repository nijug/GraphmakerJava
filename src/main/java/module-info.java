module com.example.graphmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.graphmaker to javafx.fxml;
    exports com.example.graphmaker;
}