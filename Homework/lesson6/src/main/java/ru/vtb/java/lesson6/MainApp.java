package ru.vtb.java.lesson6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Program started!");
        System.out.println("---------------------------------------------------");

        // 1. Создайте массив с набором слов, и с помощью Stream API найдите наиболее часто встречающееся;
        firstTask();
        System.out.println("---------------------------------------------------");

        // 2. Создайте массив объектов типа Сотрудник (с полями Имя, Возраст, Зарплата) и вычислите среднюю зарплату сотрудника;
        // 3. Напишите метод, способный найти в массиве сотрудников из п. 2 N самых старших сотрудников и отпечатает
        //    в консоль сообщение вида “N самых старших сотрудников зовут: имя1, имя2, имяN;”.
        secondAndThirdTasks();
        System.out.println("---------------------------------------------------");

        // 4. Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов,
        // и склеить их в одну строку с пробелом в качестве разделителя;
        fourthTask();
        System.out.println("---------------------------------------------------");

        // 5. Посчитать сумму четных чисел в пределах от 100 до 200 (включительно);
        fifthTask();
        System.out.println("---------------------------------------------------");

        // 6. Посчитать суммарную длину строк в одномерном массиве;
        sixthTask();
        System.out.println("---------------------------------------------------");

        // 7. Из массива слов получить первые три слова в алфавитном порядке;
        seventhTask();

        System.out.println("---------------------------------------------------");
        System.out.println("Program finished!");
    }

    private static void firstTask() {
        ArrayList<String> initData = new ArrayList<>(Arrays.asList("first", "second", "third", "fourth", "fifth", "first", "second", "sixth", "seventh",
                "eighth", "ninth", "second", "third", "fourth", "tenth", "tenth", "tenth", "first"));

        System.out.println("First Task.");
        // как без промежуточного определения max найти все самые часто встречающиеся так и не придумал
        var map = initData.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting())).entrySet();
        var max = map.stream().max(Map.Entry.comparingByValue()).get();
        map.stream().filter(m -> m.getValue() == max.getValue()).forEach(m -> System.out.println(m));
    }

    private static void secondAndThirdTasks() {
        ArrayList<Employee> initData = new ArrayList<>();
        initData.add(new Employee("Иван", 25, 26000));
        initData.add(new Employee("Петр", 35, 37000));
        initData.add(new Employee("Сидор", 45, 48000));
        initData.add(new Employee("Штирлиц1", 55, 71000));
        initData.add(new Employee("Штирлиц2", 55, 72000));
        initData.add(new Employee("Штирлиц3", 55, 73000));

        var average = initData.stream().map(m -> m.getSalary()).mapToLong(m -> m).average().getAsDouble();
        System.out.println("Second task. Average salary: " + average);

        var maxAge = initData.stream().max(Comparator.comparingInt(Employee::getAge)).get().getAge();
        var oldestEmployeeNames = initData.stream().filter(m -> m.getAge() == maxAge).map(m -> m.getName()).collect(Collectors.toList());
        var resultThirdTask = oldestEmployeeNames.stream().reduce(
                                     String.format("Third task. %d самых старших сотрудников зовут:", oldestEmployeeNames.size()),
                                                   (s, s2) -> s + " " + s2);
        System.out.println(resultThirdTask);
    }

    private static void fourthTask() {
        var initString = "Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;"
                         + " Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;"
                         + " Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;"
                         + " Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;"
                         + " Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;";

        initString = initString.replaceAll("[,/.;]", "");
        var resultFourthTask = Arrays.stream(initString.split("\\s")).filter(m -> m.length() > 5).reduce("", (s, s2) -> s + " " + s2);
        System.out.println("Fourth task. " + resultFourthTask);
    }

    private static void fifthTask() {
        var resultFifthTask = IntStream.rangeClosed(100, 200).filter(m -> m % 2 == 0).sum();
        System.out.println("Fifth task. " + resultFifthTask);
    }

    private static void sixthTask() {
        String[] initData = { "Ира", "Катя", "Настя", "Светлана", "Елена", "Татьяна", "Надежда", "Ксюша" };

        var resultSixthTask = Arrays.stream(initData).map(m -> m.length()).mapToInt(m -> m).sum();
        System.out.println("Sixth task. " + resultSixthTask);
    }

    private static void seventhTask() {
        String[] initData = { "Ира", "Катя", "Настя", "Светлана", "Елена", "Татьяна", "Надежда", "Ксюша" };

        List<String> resultSeventhTask = Arrays.stream(initData).sorted(String::compareTo).limit(3).collect(Collectors.toList());
        System.out.println("Seventh task. " + resultSeventhTask);
    }
}
