package com.example.graphmaker;


import java.io.*;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Locale; // string.format używa lokalnego formatu zapisywania liczb więc jeśli nie ustawi się angielskiego to double są z , zamiast z .
import java.util.Scanner;


public class Graph {

    private int x;
    private int y;

    private double randB;

    private double randE;

    private int n;
    private ArrayList<LinkedList<Node>> graph; //tablica list sąsiadów

    private double findValue(LinkedList<Node> temp, int point) {
        for (int i= 0; i< temp.size(); i++) {
        if (temp.get(i).getPoint()==point) return temp.get(i).getValue();
        }
    return -1;
    }

    public void generate(int x, int y,double randB, double randE, int n) {
        this.x=x;
        this.y=y;
        this.randB=randB;
        this.randE=randE;
        this.n=n;
        graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                graph.add(new LinkedList<Node>()); // dodaje nowa liste

                if (i != 0) { // górny, dzięki temu warunkowi find_value szuka wartości tylko jeśli sąsiadujący węzeł już istnieje
                    graph.get(i * y + j).add(new Node((i - 1) * y + j, findValue(graph.get((i - 1) * y + j), i * y + j)));
                }
                if (j + 1 != y) { //prawy
                    graph.get(i * y + j).add(new Node((i * y + j + 1), Math.random()*(randE-randB)+randB));
                }
                if (j != 0) // lewt
                    graph.get(i * y + j).add(new Node(i * y + j - 1, findValue(graph.get(i * y + j - 1), i * y + j)));
                if (i + 1 != x) { //dolny
                    graph.get(i * y + j).add(new Node((i + 1) * y + j, Math.random()*(randE-randB)+randB));
                }
            }
        }
    }
    public void save(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("błąd");
        }
    }

    public void open(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            scanner.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            System.out.println("błąd");
        }

        this.x = scanner.nextInt();
        this.y = scanner.nextInt();
        scanner.nextLine();
        graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
        Scanner line = null;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                 graph.add(new LinkedList<Node>());
                line = new Scanner(scanner.nextLine());
                while (line.hasNext())
                    graph.get(i * y + j).add(new Node(Integer.parseInt(line.next()), Double.parseDouble(line.next())));
            }
        }

    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("%d %d \n", x, y));
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < graph.get(i * y + j).size(); k++) {
                    sb.append(String.format( Locale.US,"%d %f ", graph.get(i * y + j).get(k).getPoint(), graph.get(i * y + j).get(k).getValue()));
                }
                sb.append("\n");
            }

        }
        return sb.toString();
    }

}
