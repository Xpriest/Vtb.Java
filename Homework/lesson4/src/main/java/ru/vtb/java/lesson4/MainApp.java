package ru.vtb.java.lesson4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Program started!");

        var firstMethodResult = firstMethod(100000000);
        System.out.println(String.format("First method. Full time: %d ms", firstMethodResult));
        // First method. Full time: 15258 ms

        var size = 100000000;
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        var theadsCount = 5;
        var secondMethodResult = secondMethod(arr, theadsCount);
        System.out.println(String.format("Second method. ThreadsCount: %d, full time: %d ms.", theadsCount, secondMethodResult));
        // Second method. ThreadsCount: 5, full time: 4215 ms.
        // Все-равно небыстро.

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }


    // создает массив, обсчитывает его в одном потоке
    private static Long firstMethod(int arraySize) {
        float[] arr = new float[arraySize];
        Arrays.fill(arr, 1f);

        long fullTime = System.currentTimeMillis();
        for (var i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        var result = System.currentTimeMillis() - fullTime;
        return result;
    }

    private static Long secondMethod(float[] arr, int threadsCount) throws InterruptedException {
        var calculatorExecutor = Executors.newFixedThreadPool(threadsCount);

        // Логика: в цикле запустим потоки, каждый из которых будет работать с кусочком массива.
        // Кусочки (начальный и конечный индексы) вычислим перед запуском каждого потока.
        // Даже если в индексах чуть запутаюсь (first = last след итерации) - пофиг. Два раза где-то вычислится значение - на времени,
        // которое надо посчитать, не скажется.
        var splitSize = Math.round(arr.length / threadsCount);
        int firstIndex = 0, lastIndex = 0;
        long fullTime = System.currentTimeMillis();
        for (var threadNumber = 0; threadNumber < threadsCount; threadNumber++) {
            firstIndex = lastIndex;
            lastIndex = (threadNumber + 1 == threadsCount ? arr.length : firstIndex + splitSize);

            // поток запускаем
            int finalFirstIndex = firstIndex;
            int finalLastIndex = lastIndex;
            calculatorExecutor.execute(() -> {
                for (var i = finalFirstIndex; i < finalLastIndex; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            });
        }

        calculatorExecutor.shutdown();
        // Может прослушал. Но без этого (awaitTermination) прога просто пролетала, не дожидаясь выполнения потоков.
        try {
            calculatorExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        var result = System.currentTimeMillis() - fullTime;
        return result;
    }
}