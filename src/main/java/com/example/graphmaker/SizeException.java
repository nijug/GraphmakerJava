package com.example.graphmaker;

public class SizeException extends Exception{

    public SizeException() {
    View.displayAlert("Wartości X i Y muszą być większe od 0");
    }
}
