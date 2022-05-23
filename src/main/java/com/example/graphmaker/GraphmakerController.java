package com.example.graphmaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import java.io.File;


public class GraphmakerController {

    private int x;
    private int y;
    private double randB;
    private double randE;
    private int n;

    private Graph main = new Graph();

    @FXML
    private TextField textX;
    @FXML
    private TextField textY;
    @FXML
    private TextField textRandB;
    @FXML
    private TextField textRandE;
    @FXML
    private TextField textN; // ile ma być podziałów grafu
    @FXML
    private Button buttonGenerate;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonSave;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private Pane canva;
    @FXML
    public void onGenerateClick(ActionEvent event)
    {
        canva.getChildren().clear();
        x=Integer.parseInt(textX.getText());
        y=Integer.parseInt(textY.getText());
        randB=Double.parseDouble(textRandB.getText());
        randE=Double.parseDouble(textRandE.getText());
        n=Integer.parseInt(textN.getText());
        main.generate(x,y,randB,randE,n);
        System.out.println(main);
        canvas();
    }
    @FXML
    public void onOpenClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt");

        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            main.open(selectedFile);

        }

        System.out.println(main);
    }
    @FXML
    public void onSaveClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt");

        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            main.save(main.toString(), selectedFile);
        }

    }
    @FXML
    private void canvas()
    {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                canva.getChildren().add(drawCircle(i,j));
                if (j != y-1){
                    canva.getChildren().add(drawRightLine(i,j));
                }
                if (i!=x-1){
                    canva.getChildren().add(drawDownLine(i,j));
                }
            }
        }
        ScrollPane.setContent(canva);


    }

    private Circle drawCircle(int i, int j)
    {
        Circle circle = new Circle();
        circle.setCenterX((j*15)+20);
        circle.setCenterY((i*15)+20);
        circle.setRadius(5);
        circle.setFill(Color.WHITE);
        return circle;
    }

    private Line drawRightLine(int i, int j)
    {
        Line line = new Line();
        line.setStartX((j*15)+26);
        line.setStartY((i*15)+20);
        line.setEndX((j*15)+35);
        line.setEndY((i*15)+20);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(3);

        return line;
    }


    private Line drawDownLine(int i, int j)
    {
        Line line = new Line();
        line.setStartX((j*15)+20);
        line.setStartY((i*15)+26);
        line.setEndX((j*15)+20);
        line.setEndY((i*15)+35);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(3);

        return line;
    }
}