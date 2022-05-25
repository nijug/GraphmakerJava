package com.example.graphmaker;

public class RandException extends Exception{

    public RandException() {
        View.displayAlert("Początek zakresu nie może być większy od końca zakresu, obie liczby muszą być dodatnie");
    }
}
