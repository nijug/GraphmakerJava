package com.example.graphmaker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;




public class View {
    private static Stage stage;
    private static Scene scene;
    public static void mainStage(Stage s) throws IOException  { // ustawienie stage i scenie, wymiary na sztywno zrobione żeby nie rozciągać bo nie jest responsywne
        stage=s;
        FXMLLoader fxmlLoader = new FXMLLoader(Graphmaker.class.getResource("graphmaker-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Graphmaker");
        stage.setScene(scene);
        stage.setMaxWidth(1300);
        stage.setMaxHeight(760);
        stage.setMinWidth(1300);
        stage.setMinHeight(760);
        stage.show();
    }
    public static void displayAlert(String message) // wyświetla alert z wiadomościa message
    {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }


  public static Circle drawCircle(int x, int y) { // i oraz y odpowiadją pozycji x i y na anchorpane - każdy wiersz/rząd jest co 15 pikseli
        Circle circle = new Circle();
        circle.setCenterX((x*15)+20);
        circle.setCenterY((y*15)+20);
        circle.setRadius(5);
        circle.setFill(Color.WHITE);
        return circle;
    }

    public static Line drawRightLine(int x, int y, double value) {
        Line line = new Line();
        line.setStartX((x*15)+26);// 26 bo inaczej linia wchodzi kawałek na koło
        line.setStartY((y*15)+20);
        line.setEndX((x*15)+35);
        line.setEndY((y*15)+20);
        line.setStrokeWidth(3);
        return generateLineColor(line, value);
    }


    public static Line drawDownLine( int x,int y,double value) {
        Line line = new Line();
        line.setStartX((x*15)+20);
        line.setStartY((y*15)+26);
        line.setEndX((x*15)+20);
        line.setEndY((y*15)+35);
        line.setStrokeWidth(3);
        return generateLineColor(line, value);
    }

    public static Line drawLeftLine(int x, int y, double value) {
        Line line = new Line();
        line.setStartX((x*15)-26);// 26 bo inaczej linia wchodzi kawałek na koło
        line.setStartY((y*15)+20);
        line.setEndX((x*15)-35);
        line.setEndY((y*15)+20);
        line.setStrokeWidth(3);
        return generateLineColor(line, value);
    }

    public static Line drawUpLine( int x,int y,double value) {
        Line line = new Line();
        line.setStartX((x*15)+20);
        line.setStartY((y*15)-26);
        line.setEndX((x*15)+20);
        line.setEndY((y*15)-35);
        line.setStrokeWidth(3);
        return generateLineColor(line, value);
    }
    private static Line generateLineColor(Line line, double value) // generuje kolor dla róznych wartości odpowiadający palecie hue - wartości na począku zakresu czerwone na końcu magenta
        {

            double minHue = 300f/255;
            double maxHue = 0;
            double hue = (((value*maxHue + (1-value)*minHue))*10000)%minHue;
            java.awt.Color color = new java.awt.Color(java.awt.Color.HSBtoRGB((float) hue, 1, 0.5f));
            javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/255.0);
            line.setStroke(fxColor);
            return line;
        }



}
