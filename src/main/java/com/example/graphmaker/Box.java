package com.example.graphmaker;

class Box implements Comparable<Box>{
    private int number;
    private double length;
    private Box prev;

    public Box(int number, double length){
        this.number = number;
        this.length = length;
        this.prev = null;
    }

    public void setPrev(Box prev){
        this.prev = prev;
    }

    public Box getPrev(){
        return this.prev;
    }

    public void setLength(double length){
        this.length = length;
    }

    public double getLength(){
        return this.length;
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber(){
        return this.number;
    }

    @Override
    public int compareTo(Box bx) {
        if(length < bx.length)
            return -1;
        else if(length > bx.length)
            return 1;
        return 0;
    }

}
