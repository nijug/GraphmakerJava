module com.example.graphmaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphmaker to javafx.fxml;
    exports com.example.graphmaker;
}