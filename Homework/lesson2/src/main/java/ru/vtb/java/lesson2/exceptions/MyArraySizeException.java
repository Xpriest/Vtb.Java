package ru.vtb.java.lesson2.exceptions;

public class MyArraySizeException extends Exception {

    public MyArraySizeException() {
        super("Array has to be 4x4");
    }

    public MyArraySizeException(String inputMessage) {
        super(inputMessage);
    }
}
