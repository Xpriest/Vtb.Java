package ru.vtb.java.lesson3.thirdtask;

import ru.vtb.java.lesson3.thirdtask.fruits.Fruit;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    public ArrayList<T> getFruits() {
        return fruits;
    }

    private ArrayList<T> fruits;

    public Box() {
        fruits = new ArrayList<>();
    }

    public Box(ArrayList<T> fruits) {
        this.fruits = fruits;
    }

    public void addFruits(T...inputFruits) {
        fruits.addAll(Arrays.asList(inputFruits));
    }

    public Float getWeight() {
        if (fruits.size() == 0)
             return  0f;

        var weight = fruits.get(0).getWeight();
        var result = fruits.size() * weight;
        return result;
    }

    public boolean compare(Box<? extends Fruit> inputBox) {
        if (inputBox.getWeight().compareTo(this.getWeight()) == 0)
            return true;

        return false;
    }

    public void showFruits() {
        System.out.println(Arrays.toString(fruits.toArray()));
    }

    public void clear() {
        fruits.clear();
    }
}
