package com.example.graphmaker;

public class Node {
    double value;
    int point;

    public Node(int point, double value){
        this.point=point;
        this.value=value;

    }

    public double getValue(){
        return value;
    }
    public int getPoint() {
        return point;
    }
}

