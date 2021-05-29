package ru.vtb.java.lesson6;

public class Employee {
    public String getName() {
        return name;
    }

    private String name;

    public Integer getAge() {
        return age;
    }

    private Integer age;

    public long getSalary() {
        return salary;
    }

    private long salary;

    public Employee(String name, Integer age, long salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
