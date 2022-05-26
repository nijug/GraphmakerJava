package com.example.graphmaker;

import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class View {

    public static void displayAlert(String message) // wyświetla alert z wiadomościa message
    {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }


  public static Circle drawCircle(int i, int j) { // i oraz j odpowiadją pozycji x i y na anchorpane - każdy wiersz/rząd jest co 15 pikseli
        Circle circle = new Circle();
        circle.setCenterX((j*15)+20);
        circle.setCenterY((i*15)+20);
        circle.setRadius(5);
        circle.setFill(Color.WHITE);
        return circle;
    }

    public static Line drawRightLine(int i, int j, double value) {
        Line line = new Line();
        line.setStartX((j*15)+26);// 26 bo inaczej linia wchodzi kawałek na koło
        line.setStartY((i*15)+20);
        line.setEndX((j*15)+35);
        line.setEndY((i*15)+20);
        java.awt.Color color = generateColor(value); // generuje kolor hue
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/255.0); // konwertuje na rgb
        line.setStroke(fxColor);
        line.setStrokeWidth(3);
        return line;
    }


    public static Line drawDownLine(int i, int j,double value) {
        Line line = new Line();
        line.setStartX((j*15)+20);
        line.setStartY((i*15)+26);
        line.setEndX((j*15)+20);
        line.setEndY((i*15)+35);
        java.awt.Color color = generateColor(value);
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()/255.0);
        line.setStroke(fxColor);
        line.setStrokeWidth(3);
        return line;
    }
    private static java.awt.Color generateColor(double value) // generuje kolor dla róznych wartości odpowiadający palecie hue - wartości na począku zakresu czerwone na końcu magenta
        {

            double minHue = 300f/255;
            double maxHue = 0;
            double hue = (((value*maxHue + (1-value)*minHue))*10000)%minHue;
            return new java.awt.Color(java.awt.Color.HSBtoRGB((float) hue, 1, 0.5f));
        }
}
