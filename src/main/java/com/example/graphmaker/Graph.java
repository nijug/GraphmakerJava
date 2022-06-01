package com.example.graphmaker;
import java.io.*;
import java.util.*;


public class Graph {

    private int x;
    private int y;
    private double randB;
    private double randE;
    private int n;
    private ArrayList<LinkedList<Node>> graph; // graph zapisany tak samo jak w c

   public double findValue(LinkedList<Node> temp, int point) { // szukanie value, pierwszy argumet tablica sąsiadów, druga nr sąsiada
       for (int i= 0; i< temp.size(); i++) {
           if (temp.get(i).getPoint()==point) {
               return temp.get(i).getValue();
           }
       }
    return -1;
   }

    public void generate(int x, int y,double randB, double randE) {
        this.x=x;
        this.y=y;
        this.randB=randB;
        this.randE=randE;
        this.graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                this.graph.add(new LinkedList<Node>()); // dodaje nowa liste
                if (i != 0) { // górny
                    this.graph.get(i * this.y + j).add(new Node((i - 1) * this.y + j, findValue(this.graph.get((i - 1) * this.y + j), i * this.y + j)));
                }
                if (j + 1 != y) { //prawy
                    this.graph.get(i * this.y + j).add(new Node((i * this.y + j + 1), Double.parseDouble(String.format(Locale.US,"%.6f",Math.random()*(randE-randB)+randB ))));// maksymalnie 6 liczb po przecinku
                }
                if (j != 0) // lewy
                    this.graph.get(i * this.y + j).add(new Node(i * this.y + j - 1, findValue(this.graph.get(i * this.y + j - 1), i * y + j)));
                if (i + 1 != x) { //dolny
                    this.graph.get(i * this.y + j).add(new Node((i + 1) * this.y + j,  Double.parseDouble(String.format(Locale.US,"%.6f",Math.random()*(randE-randB)+randB ))));
                }
            }
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public LinkedList<Node> getList(int index) { //potrzebne do użycia getValue poza klasą graph
       return this.graph.get(index);
    }
    public void save(String content, File file) { // zapisuje do pliku, content to graph.toString, ifile to wybrany plik z file chooser
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            View.displayAlert("Błąd podczas zapisywania pliku");
        }
    }

    public void open(File file) { //scanner czyta plik
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            scanner.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            View.displayAlert("Błąd podczas otwierania pliku");
        }
        try {
            this.x = scanner.nextInt();
            this.y = scanner.nextInt();
            scanner.nextLine();
            this.graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
            Scanner line;
            for (int i = 0; i < this.x; i++) {
                for (int j = 0; j < this.y; j++) {
                    this.graph.add(new LinkedList<Node>());
                    line = new Scanner(scanner.nextLine());
                    while (line.hasNext()) {
                        this.graph.get(i * this.y + j).add(new Node(Integer.parseInt(line.next()), Double.parseDouble(line.next())));
                    }
                }
            }
        } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
            View.displayAlert("Nieprawidłowy format pliku");
        }

   }


    @Override
    public String toString() { // do debugowania
        StringBuilder sb = new StringBuilder(String.format("%d %d \n", this.x, this.y));
        for (int i = 0; i <this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                for (int k = 0; k < this.graph.get(i * this.y + j).size(); k++) {
                    sb.append(String.format( Locale.US,"%d %f ", this.graph.get(i * this.y + j).get(k).getPoint(), this.graph.get(i * this.y + j).get(k).getValue())); // locale us po to żeby liczby były z . a nie z ,
                }
                sb.append("\n");
            }

        }
        return sb.toString();
    }

}
