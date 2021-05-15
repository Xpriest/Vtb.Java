package ru.vtb.java.lesson2.testers;

import java.util.ArrayList;
import java.util.LinkedList;

// Тестер для ArrayList'а
public class ArrayListTester {

    // Тестим получение среднего элемента из ArrayList'а
    public void testGetFromArrayList(int dimension) {
        ArrayList<Integer> arL = fillArrayList(dimension);

        long startTime = System.currentTimeMillis();
        var elemetnIndex = dimension / 2;
        for (var i = 0; i < 10000; i++) {
            arL.get(elemetnIndex);
        }
        var timeDifference = (System.currentTimeMillis() - startTime);
        System.out.println(String.format(" - ArrayList with size %d: %d ms", dimension, timeDifference));
    }

    // Тестим удаление половины элементов из ArrayList'а
    public void testDeleteFromArrayList(int dimension) {
        ArrayList<Integer> arL = fillArrayList(dimension);

        long startTime = System.currentTimeMillis();
        var deleteIndex = dimension / 2;
        for (var i = 0; i < dimension / 2; i++) {
            arL.remove(deleteIndex);
        }
        var timeDifference = (System.currentTimeMillis() - startTime);
        System.out.println(String.format(" - ArrayList with size %d: %d ms", dimension, timeDifference));
    }

    // Заполняем ArrayList
    private static ArrayList<Integer> fillArrayList(int dimension) {
        ArrayList<Integer> result = new ArrayList<Integer>(dimension);
        for (var i = 0; i < dimension; i++) {
            result.add(i);
        }

        return result;
    }
}
