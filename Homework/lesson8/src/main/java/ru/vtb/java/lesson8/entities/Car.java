package ru.vtb.java.lesson8.entities;

import javax.persistence.*;

@Entity
@Table(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brandName;
    private Long horsePower;

    public Car() {}

    public Car(String brandName, Long horsePower) {
        this.brandName = brandName;
        this.horsePower = horsePower;
    }

    public void print() {
        System.out.println(String.format("id = %d, brandName = %s, horsePower = %d", id, brandName, horsePower));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(Long horsePower) {
        this.horsePower = horsePower;
    }
}
