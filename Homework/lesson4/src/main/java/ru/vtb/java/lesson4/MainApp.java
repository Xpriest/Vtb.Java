package ru.vtb.java.lesson4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Program started!");

        firstMethod(100000000);
        // First method. Full time: 15078 ms

        var size = 100000000;
        float[] arr = new float[size];
        Arrays.fill(arr, 1f);
        var theadsCount = 10;
        secondMethod(arr, theadsCount);
        // Second method. ThreadsCount: 10, full time: 4623 ms.
        // Время все-равно приличное.

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }


    // создает массив, обсчитывает его в одном потоке
    private static void firstMethod(int arraySize) {
        float[] arr = new float[arraySize];
        Arrays.fill(arr, 1f);

        long fullTime = System.currentTimeMillis();
        for (var i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        var result = System.currentTimeMillis() - fullTime;
        System.out.println(String.format("First method. Full time: %d ms", result));
    }

    private static void secondMethod(float[] arr, int threadsCount) {
        long fullTime = System.currentTimeMillis();

        var calculatorExecutor = Executors.newFixedThreadPool(threadsCount);

        var splitSize = Math.round(arr.length / threadsCount);
        int firstIndex = 0, lastIndex = 0;
        for (var threadNumber = 0; threadNumber < threadsCount; threadNumber++) {
            firstIndex = lastIndex;
            lastIndex = (threadNumber + 1 == threadsCount ? arr.length : firstIndex + splitSize);

            // потоки запускаем
            int finelThreadNumber = threadNumber;
            int finalFirstIndex = firstIndex;
            int finalLastIndex = lastIndex;
            calculatorExecutor.execute(() -> {
                // на всякий посмотреть, что потоки нужные данные получили
                System.out.println(finelThreadNumber + " | " + finalFirstIndex + " | " + finalLastIndex);

                for (var i = finalFirstIndex; i < finalLastIndex; i++) {
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            });
        }

        calculatorExecutor.shutdown();
        try {
            calculatorExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        var result = System.currentTimeMillis() - fullTime;
        System.out.println(String.format("Second method. ThreadsCount: %d, full time: %d ms.", threadsCount, result));
    }
}