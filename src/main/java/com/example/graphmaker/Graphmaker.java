package com.example.graphmaker;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;



public class Graphmaker extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       View.mainStage(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}