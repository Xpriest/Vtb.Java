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
        System.out.println(String.format("First method. Full time: %d ms. Adding distinct values time: %d ms",
                firstMethodResult[0], firstMethodResult[1]));
        // First method. Full time: 46660 ms. Adding distinct values time: 5828 ms

        var size = 100000000;
        float[] arr = new float[size];
        var theadsCount = 30;
        var secondMethodResult = secondMethod(arr, theadsCount);
        System.out.println(String.format("Second method. Full time: %d ms. Adding distinct values time: %d ms",
                secondMethodResult[0], secondMethodResult[1]));
        // 7 потоков. Second method. Full time: 32062 ms. Adding distinct values time: 16909 ms
        // 30 потоков. Second method. Full time: 42317 ms. Adding distinct values time: 52762 ms!!!1!11АДЫН1!

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
        // Резюме.
        // 1. Значение в 1 и 2 случаях считается за условно равное время. Дальше проверка (которая к вычислениям не имеет
        //    отношения). В итоге время на новые значения в 1 и 2 случае должно быть одно и то же.
        //    Но см. п.3

        // 2. Почему разница в работе кода в целом незначительная (46 и 32 сек соотв.) - коллекция дублей общая в случае потоков.
        //    Там наверняка zynchronized (lock1), так что они тут как в ~1 поток работают с ней + п.3 опять же.

        // 3. Почему время непосредственно вычислений так увеличилось в случае 2. При 30 потоках вообще дичь...
        //    Предположу, что при 1 потоке условно значение считается за 1ms. А при 30 за условно 2ms (потоков много, комп тормозит).
        //    Поэтому и общее время работы потоков вырастает (п.2). И время непосредственно вычислений станет неприличным (п.1).

        // Ну либо я что-то глобально не так сделал=)
    }


    // создает массив, обсчитывает его в одном потоке
    private static Long[] firstMethod(int arraySize) {
        float[] arr = new float[arraySize];

        Arrays.fill(arr, 1f);

        HashSet<Float> existedValuesSet = new HashSet();

        long resultTime = 0;
        long fullTime = System.currentTimeMillis();
        long operationExecutionTime;
        for (var i = 0; i < arr.length; i++) {
            operationExecutionTime = System.currentTimeMillis();
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            operationExecutionTime = System.currentTimeMillis() - operationExecutionTime;

            // он их добавляет, считая хэш. Т.е. и с ~примерными float'ами должен справиться.
            if (existedValuesSet.add(arr[i])) {
                resultTime += operationExecutionTime;
            }
        }

        return new Long[] { System.currentTimeMillis() - fullTime, resultTime };
    }

    private static Long[] secondMethod(float[] arr, int threadsCount) throws InterruptedException {
        long[] result = new long[threadsCount];
        Arrays.fill(result, 0);
        long fullTime = System.currentTimeMillis();

        var calculatorExecutor = Executors.newFixedThreadPool(threadsCount);

        // Логика: в цикле запустим потоки, каждый из которых будет работать с кусочком массива.
        // Кусочки (начальный и конечный индексы) вычислим перед запуском каждого потока.
        // Даже если в индексах чуть запутаюсь (first = last след итерации) - пофиг. Два раза где-то вычислится значение - на времени,
        // которое надо посчитать, не скажется.
        var splitSize = Math.round(arr.length / threadsCount);
        int firstIndex = 0, lastIndex = 0;
        // вроде потокобезопасная
        Set<Float> existedValuesSet = ConcurrentHashMap.newKeySet();
        for (var threadNumber = 0; threadNumber < threadsCount; threadNumber++) {
            firstIndex = lastIndex;
            lastIndex = (threadNumber + 1 == threadsCount ? arr.length : firstIndex + splitSize);

            // поток запускаем
            int finalThreadNumber = threadNumber;
            int finalFirstIndex = firstIndex;
            int finalLastIndex = lastIndex;
            calculatorExecutor.execute(() -> {
                long operationExecutionTime;
                for (var i = finalFirstIndex; i < finalLastIndex; i++) {
                    arr[i] = 1f;

                    operationExecutionTime = System.currentTimeMillis();
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                    operationExecutionTime = System.currentTimeMillis() - operationExecutionTime;

                    if (existedValuesSet.add(arr[i])) {
                        result[finalThreadNumber] += operationExecutionTime;
                    }
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

        return new Long[] { System.currentTimeMillis() - fullTime, Arrays.stream(result).sum() };
    }
}