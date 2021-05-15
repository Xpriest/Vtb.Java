package ru.vtb.java.lesson1;

import ru.vtb.java.lesson1.vehicles.*;

import java.util.Random;

public class MainApp {
    public static void main(String[] args) {
        baseVehicle[] vehicles = new baseVehicle[]
                {
                    new skateboard("Skate1"),
                    new bike("Bike1"),
                    new bike("Bike2"),
                    new car("Car1")
                };

        Person[] persons = new Person[4];
        for (Integer i = 0; i < 4; i++) {
            persons[i] = new Person("Person" + i.toString(), vehicles[i]);
        }

        for (Person singlePerson: persons) {
            singlePerson.WhoIam();

            singlePerson.StartMoving();
            singlePerson.State();
            singlePerson.StartMoving();
            singlePerson.StopMoving();
            singlePerson.State();
        }

        System.out.println("Program finished!");
    }
}
