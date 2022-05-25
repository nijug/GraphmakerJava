package com.example.graphmaker;

public class DivideException extends  Exception{
    public DivideException() {
        View.displayAlert("Nie można podać ujemnej liczby podziałów");
    }
}

