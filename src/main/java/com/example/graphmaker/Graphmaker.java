package com.example.graphmaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Graphmaker extends Application {
    @Override
    public void start(Stage stage) throws IOException {// ustawienie stage i scenie, wymiary na sztywno zrobione żeby nie rozciągać bo nie jest responsywne
        FXMLLoader fxmlLoader = new FXMLLoader(Graphmaker.class.getResource("graphmaker-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Graphmaker");
        stage.setScene(scene);
        stage.setMaxWidth(1300);
        stage.setMaxHeight(760);
        stage.setMinWidth(1300);
        stage.setMinHeight(760);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}