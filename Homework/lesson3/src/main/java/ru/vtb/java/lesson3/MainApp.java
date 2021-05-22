package ru.vtb.java.lesson3;

import ru.vtb.java.lesson3.thirdtask.Box;
import ru.vtb.java.lesson3.thirdtask.fruits.Apple;
import ru.vtb.java.lesson3.thirdtask.fruits.Fruit;
import ru.vtb.java.lesson3.thirdtask.fruits.Orange;

import java.sql.Array;
import java.util.*;

public class MainApp {
    public static void main(String[] args) throws Exception {
        // 1 пункт
        System.out.println("First task:");
        Integer[] intArray = { 1,2,3,4,5,6,7,8,9,10 };
        swapElementsOfTheArray(intArray, 2, 5);
        System.out.println(Arrays.toString(intArray));

        String[] stringArray = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
        swapElementsOfTheArray(stringArray, 4, 8);
        System.out.println(Arrays.toString(stringArray));

        System.out.println("---------------------------------------------------");

        // 2 пункт
        System.out.println("Second task:");
        String[] stringArray2 = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M" };
        var convertArrayToArrayListResult = convertArrayToArrayList(stringArray2);
        System.out.println( convertArrayToArrayListResult.getClass().getName());

        System.out.println("---------------------------------------------------");

        // 3 пункт
        System.out.println("Third task:");
        Box<Apple> boxOfApples1 = new Box<>();
        boxOfApples1.addFruits(new Apple(), new Apple(), new Apple()); //3
        Box<Apple> boxOfApples2 = new Box<>();
        boxOfApples2.addFruits(new Apple(), new Apple(), new Apple(), new Apple()); //4
        Box<Orange> boxOfOranges1 = new Box<>();
        boxOfOranges1.addFruits(new Orange(), new Orange()); //3

        System.out.println("Weight of boxOfApples1: " + boxOfApples1.getWeight());

        System.out.println("boxOfApples2 ?= boxOfApples1: " + boxOfApples2.compare(boxOfApples1));
        System.out.println("boxOfApples1 ?= boxOfOranges1: " + boxOfApples1.compare(boxOfOranges1));

        transferAllFruits(boxOfApples1, boxOfApples2);
        System.out.println("Transfer fruits from box2 to box1: ");
        boxOfApples1.showFruits();
        boxOfApples2.showFruits();

        System.out.println("---------------------------------------------------");

        // 4 пункт
        System.out.println("Fourth task:");
        String[] stringArray3 = { "Apple", "Orange", "Orange", "Apple", "Banana", "Cherry", "Cherry", "Lime", "Pear",
                                  "Mango", "Peach", "Mango", "Pineapple", "Melon", "Cherry", "Coconut", "Banana",
                                  "Watermelon", "Strawberry", "Kiwi" };
        doFourthTask(stringArray3);

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }

    // Меняем два элемента массива местами
    private static <T> void swapElementsOfTheArray(T[] inputArray, int firstIndex, int secondIndex) {
        T tempT = inputArray[firstIndex];
        inputArray[firstIndex] = inputArray[secondIndex];
        inputArray[secondIndex] = tempT;
    }

    // конвертим Array в ArrayList
    private static <T> ArrayList<T> convertArrayToArrayList(T[] inputArray) {
        var list = Arrays.asList(inputArray);
        var result = new ArrayList<>(list);
        return result;
    }

    // выполняем 4-й таск (посчитать кол-во вхождений в массив)
    private static void doFourthTask(String[] inputArray) {
        Map<String, Integer> resultMap = new HashMap<>();
        for (var singleInput: inputArray) {
            var singleInputCount = resultMap.get(singleInput);
            if (singleInputCount == null) {
                resultMap.put(singleInput, 1);
            }
            else {
                resultMap.put(singleInput, ++singleInputCount);
            }
        }

        System.out.println(resultMap);
    }

    // пересыпаем все фрукты из одной коробки в другую
    private static <T extends Fruit> void transferAllFruits(Box<T> inputBox, Box<T> outputBox) {
        var box2Array = new Fruit[outputBox.getFruits().size()];
        box2Array = outputBox.getFruits().toArray(box2Array);

        inputBox.addFruits((T[]) box2Array);

        outputBox.clear();
    }
}
