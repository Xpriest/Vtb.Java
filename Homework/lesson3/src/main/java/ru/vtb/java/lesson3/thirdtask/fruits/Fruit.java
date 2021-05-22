package ru.vtb.java.lesson3.thirdtask.fruits;

public abstract class Fruit {
    private Float weight;

    public Float getWeight() {
        return weight;
    }

    public Fruit(Float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + weight;
    }
}
