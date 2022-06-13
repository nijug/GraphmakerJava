package com.example.graphmaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.File;

// kontroler pośredniczy między view( funkcje do rysowania i plik fxml) i  modelem (graph)
public class GraphmakerController {

    private int x;
    private int y;
    private double randB;
    private double randE;
    private int n;
    private int first;
    private int second;

    private Graph main = new Graph(); // przechowywanie grafu

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
    private TextField textF; // numer pierwszego wierzchołka
    @FXML
    private TextField textS; // numer drugiego wierzchołka
    @FXML
    private Button buttonFind; // wyszukiwanie ścieżki między wierzchołkami
    @FXML
    private Button buttonGenerate;
    @FXML
    private Button buttonOpen;
    @FXML
    private Button buttonSave;
    @FXML
    private ScrollPane ScrollPane; // okno ze scrollem
    @FXML
    private AnchorPane canva; //czaarne tło do rysowania, dziecko scrollpane

    @FXML
    private void onGenerateClick(ActionEvent event) throws IllegalArgumentException, SizeException,RandException,DivideException { // pobiera dane z gui, wyrzuca exception jak są nieporawne

        try {
            x = Integer.parseInt(textX.getText());
            y = Integer.parseInt(textY.getText());
            if (x<1 || y <1)
                throw new SizeException();
            randB = Double.parseDouble(textRandB.getText());
            randE = Double.parseDouble(textRandE.getText());
            if (randB>randE || randB<0 || randE<0 || randB==randE)
                throw new RandException();
            n = Integer.parseInt(textN.getText());
            if (n<0)
                throw new DivideException();
            main.generate(x, y, randB, randE);
            System.out.println(main);
            drawGraph();
        } catch (IllegalArgumentException e) {
            View.displayAlert("Nie podano wszystkich wartości, lub wartości są w nieodpowiednim formacie");
        }

    }

    @FXML
    private void onOpenClick(ActionEvent event) { // okienko otwierania
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            main.open(selectedFile);
            System.out.println(main);
            drawGraph();
        }
    }

    @FXML
    private void onSaveClick(ActionEvent event) { //okienko zapisywania
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            main.save(main.toString(), selectedFile);
        }
    }

    @FXML
    private void onFindClick(ActionEvent event) { // wyszukiwanie ścieżki między grafami
                first = Integer.parseInt(textF.getText());
                second = Integer.parseInt(textS.getText());
                //if (first < 1 || second < 1)
                //    throw new SizeException();
                System.out.println(main.dijkstra(first, second/*, x, y*/));
                //drawPath();

        }

    @FXML
    private void drawGraph() { // stąd wywoływać metody związane z rysowaniem
        canva.getChildren().clear(); //wszystko co rysowane na czarnym tle jest dzieckiem canva(anchorpane)
        for (int i = 0; i < main.getX(); i++) { // x to wysokość
            for (int j = 0; j < main.getY(); j++) { // y to szerokość
                canva.getChildren().add(View.drawCircle(j, i));
                if (j != main.getY() - 1) {
                    canva.getChildren().add(View.drawRightLine(j, i, main.findValue(main.getList(i * main.getY() + j), i * main.getY() + j + 1))); // value jest potrzebne żeby linia miała odpowiedni kolor
                }
                if (i != main.getX() - 1) {
                    canva.getChildren().add(View.drawDownLine(j, i, main.findValue(main.getList(i * main.getY() + j), (i + 1) * main.getY() + j)));
                }
            }
        }
    }

    @FXML
    private void drawPath() { // metoda rysująca ścieżkę
        canva.getChildren(); //wszystko co rysowane na czarnym tle jest dzieckiem canva(anchorpane)
        Box last = main.is_listed(second);
        Box source = main.is_listed(first);
        Box current = last;
        int i, j;
        int prev_nb, current_nb;
        while(current.getPrev()!=source){
            current_nb = current.getNumber();
            prev_nb = current.getPrev().getNumber();
            i = current_nb / y;
            j = current_nb % y;
            if(prev_nb == current_nb - x) //gora
                canva.getChildren().add(View.drawUpLine(j, i, main.findValue(main.getList(i * main.getY() + j), (i + 1) * main.getY() + j)));
            else if(prev_nb == current_nb + 1) //prawo
                canva.getChildren().add(View.drawRightLine(j, i, main.findValue(main.getList(i * main.getY() + j), i * main.getY() + j + 1))); // value jest potrzebne żeby linia miała odpowiedni kolor
            else if(prev_nb == current_nb - 1) // lewo
                canva.getChildren().add(View.drawLeftLine(j, i, main.findValue(main.getList(i * main.getY() + j), i * main.getY() + j + 1))); // value jest potrzebne żeby linia miała odpowiedni kolor
            else if(prev_nb == current_nb + x) // dół
                canva.getChildren().add(View.drawDownLine(j, i, main.findValue(main.getList(i * main.getY() + j), (i + 1) * main.getY() + j)));
        }
        main.clearQueues();
    }
}