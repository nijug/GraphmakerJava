package com.example.graphmaker;

import javafx.scene.effect.Light;

public class Node {
    double value;
    int point;

    public Node(double value, int point){
        this.value=value;
        this.point=point;
    }

    public double getValue(){
        return value;
    }
    public int getPoint() {
        return point;
    }
}

