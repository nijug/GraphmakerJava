package com.example.graphmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Graphmaker extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Graphmaker.class.getResource("graphmaker-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Graphmaker");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        Graph main = new Graph(3,3);
        main.generate(2.0, 3.0);
        System.out.println(main.toString());
        launch();
    }
}