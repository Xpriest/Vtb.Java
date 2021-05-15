package ru.vtb.java.lesson2.testers;

import java.util.LinkedList;

// Тестер для LinkedList'а
public class LinkedListTester {
    // Тестим получение среднего элемента из LinkedList'а
    public void testGetFromLinkedList(int dimension) {
        LinkedList<Integer> llL = fillLinkedList(dimension);

        long startTime = System.currentTimeMillis();
        var elemetnIndex = dimension / 2;
        for (var i = 0; i < 10000; i++) {
            llL.get(elemetnIndex);
        }
        var timeDifference = (System.currentTimeMillis() - startTime);
        System.out.println(String.format(" - LinkedList with size %d: %d ms", dimension, timeDifference));
    }

    // Тестим удаление половины элементов из LinkedList'а
    public void testDeleteFromLinkedList(int dimension) {
        LinkedList<Integer> llL = fillLinkedList(dimension);

        long startTime = System.currentTimeMillis();
        var deleteIndex = dimension / 2;
        for (var i = 0; i < dimension / 2; i++) {
            llL.remove(deleteIndex);
        }
        var timeDifference = (System.currentTimeMillis() - startTime);
        System.out.println(String.format(" - LinkedList with size %d: %d ms", dimension, timeDifference));
    }

    // Заполняем LinkedList
    private LinkedList<Integer> fillLinkedList(int dimension) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        for (var i = 0; i < dimension; i++) {
            result.add(i);
        }

        return result;
    }
}
