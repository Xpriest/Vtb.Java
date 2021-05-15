package ru.vtb.java.lesson1;

import ru.vtb.java.lesson1.vehicles.baseVehicle;

public class Person {
    String name;
    // это вот null по умолчанию имела=)
    Boolean isMoving;
    baseVehicle vehicle;

    public Person(String name, baseVehicle vehicle) {
        this.name = name;
        this.vehicle = vehicle;
        this.isMoving = false;
    }

    public void StartMoving() {
        if (this.isMoving)
        {
            System.out.println(this.name + " is already moving on " + getVehicleInfo());
            return;
        }

        this.isMoving = true;
        System.out.println(this.name + " started to move on " + getVehicleInfo());
    }

    public void WhoIam(){
        System.out.println("I am a person named " + this.name);
    }

    public void StopMoving() {
        if (!this.isMoving)
        {
            System.out.println(this.name + " is already stopped.");
            return;
        }

        this.isMoving = false;
        System.out.println(this.name + " stopped moving on " + getVehicleInfo());
    }

    public void State() {
        if (this.isMoving)
            System.out.println(this.name + " is moving on " + getVehicleInfo());
        else
            System.out.println(this.name + " is parked.");
    }

    private String getVehicleInfo(){
        String result = "Vehicle: " + this.vehicle.getVType() + "; Name: " + this.vehicle.getName();
        return result;
    }
}
