package com.example.graphmaker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GraphmakerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}