package ru.vtb.java.lesson2.exceptions;

public class MyArrayDataException extends Exception {
    int x, y;

    public MyArrayDataException(int x, int y){
        super();

        this.x = x;
        this.y = y;
    }

    @Override
    public String getMessage() {
        return String.format("Element at [%d][%d] is not 'int' value.", x, y);
    }
}
