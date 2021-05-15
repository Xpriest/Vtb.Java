package ru.vtb.java.lesson2;

import ru.vtb.java.lesson2.exceptions.MyArrayDataException;
import ru.vtb.java.lesson2.exceptions.MyArraySizeException;
import ru.vtb.java.lesson2.testers.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainApp {
    public static void main(String[] args) {
        // 1-3 пункты
        System.out.println("First part.");

        try {

            String[][] testArray = fillArray();
            var summingOfArrayResult = summingOfArray(testArray);
            System.out.println("Sum of all elements of the array is " + summingOfArrayResult);
        } catch (MyArraySizeException ex) {
            System.out.println(ex.getMessage());
            // вот то, что не могу дальше исключение кинуть из-за 'Checked' - подбешивает)
            // throw  ex;
        } catch (MyArrayDataException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("---------------------------------------------------");

        // пункты 3-4
        System.out.println("Second part.");

        var arrayListTester = new ArrayListTester();
        var linkedListTester = new LinkedListTester();
        System.out.println("10000 tries for getting element in different lists:");
        arrayListTester.testGetFromArrayList(10);
        arrayListTester.testGetFromArrayList(1000);
        arrayListTester.testGetFromArrayList(100000);
        arrayListTester.testGetFromArrayList(10000000);
        linkedListTester.testGetFromLinkedList(10);
        linkedListTester.testGetFromLinkedList(1000);
        linkedListTester.testGetFromLinkedList(100000);
        //linkedListTester.testGetFromLinkedList(1000000);

        // по 10000 обращений
        //               10   |  1000   |   100000   |   10000000
        // ArrayList     1ms  |   0ms   |     0ms    |      0ms
        // LinkedList    0ms  |   8ms   |    1178ms  |   не дождался; проверил на 1.000.000 - 18622ms

        System.out.println("Delete half of the element from lists:");
        arrayListTester.testDeleteFromArrayList(100);
        arrayListTester.testDeleteFromArrayList(10000);
        arrayListTester.testDeleteFromArrayList(100000);
        linkedListTester.testDeleteFromLinkedList(100);
        linkedListTester.testDeleteFromLinkedList(10000);
        linkedListTester.testDeleteFromLinkedList(100000);
        // удаляем половину элементов
        //               100   |  10000   |   100000
        // ArrayList     0ms   |   3ms    |    256ms
        // LinkedList    0ms   |   26ms   |    2249ms


        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }

    // Суммируем массив.
    // А коменты для метода только так пишутся? Или как-то получше можно оформить?
    public static int summingOfArray(String[][] inputArray) throws MyArraySizeException, MyArrayDataException {
        if (inputArray.length != 4 | inputArray[0].length != 4) {
            throw new MyArraySizeException();
        }

        int result = 0;
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
                try {
                    var tempInt = Integer.parseInt(inputArray[i][j]);
                    result += tempInt;
                } catch (NumberFormatException ex) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }

        return result;
    }

    // Массив заполняем.
    private static String[][] fillArray() {
        var result = new String[4][4];
        result[0][0] = "1";
        result[0][1] = "2";
        result[0][2] = "3";
        result[0][3] = "4";

        result[1][0] = "2";
        result[1][1] = "3";
        result[1][2] = "4";
        result[1][3] = "5";

        result[2][0] = "3";
        result[2][1] = "4";
        result[2][2] = "5";
        result[2][3] = "6";

        result[3][0] = "4";
        result[3][1] = "5";
        result[3][2] = "6";
        result[3][3] = "7";

        return result;
    }
}
