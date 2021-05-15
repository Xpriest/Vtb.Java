package ru.vtb.java.lesson1.vehicles;

public abstract class baseVehicle {
    private String name;
    private String vType;

    public String getName(){
        return this.name;
    }

    public String getVType(){
        return this.vType;
    }

    public baseVehicle(String name, String vType){
        this.name = name;
        this.vType = vType;
    }
}
