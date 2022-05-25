package com.example.graphmaker;

public class Node { //do przechowywania dwóch typów danych w jednym indeksie listy
    double value;
    int point;

    public Node(int point, double value){
        this.point=point;
        this.value=value;
    }

    public double getValue() {
        return value;
    }
    public int getPoint() {
        return point;
    }
}

