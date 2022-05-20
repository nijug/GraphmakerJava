package com.example.graphmaker;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Locale; // string.format używa lokalnego formatu zapisywania liczb więc jeśli nie ustawi się angielskiego to double są z , zamiast z .

public class Graph {

    private int x;
    private int y;
    private ArrayList<LinkedList<Node>> graph; //tablica list sąsiadów

    public Graph(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private double findValue(LinkedList<Node> temp, int point) {
        for (int i= 0; i< temp.size(); i++) {
        if (temp.get(i).getPoint()==point) return temp.get(i).getValue();
        }
    return -1;
    }

    public void generate(double rand1, double rand2) {
        graph = new ArrayList<LinkedList<Node>>(x * y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                graph.add(new LinkedList<Node>()); // dodaje nowa liste

                if (i != 0) { // górny, dzięki temu warunkowi find_value szuka wartości tylko jeśli sąsiadujący węzeł już istnieje
                    graph.get(i * y + j).add(new Node(findValue(graph.get((i - 1) * y + j), i * y + j), (i - 1) * y + j));
                }
                if (j + 1 != y) { //prawy
                    graph.get(i * y + j).add(new Node(Math.random()*(rand2-rand1)+rand1, (i * y + j + 1)));
                }
                if (j != 0) // lewt
                    graph.get(i * y + j).add(new Node(findValue(graph.get(i * y + j - 1), i * y + j), i * y + j - 1));
                if (i + 1 != x) { //dolny
                    graph.get(i * y + j).add(new Node(Math.random()*(rand2-rand1)+rand1, (i + 1) * y + j));
                }
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
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
