package com.example.graphmaker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    public void onGenerateClick(ActionEvent event)
    {
        x=Integer.parseInt(textX.getText());
        y=Integer.parseInt(textY.getText());
        randB=Double.parseDouble(textRandB.getText());
        randE=Double.parseDouble(textRandE.getText());
        n=Integer.parseInt(textN.getText());
        main.generate(x,y,randB,randE,n);
        System.out.println(main);
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


}