package ru.vtb.java.lesson8.entities;

import javax.persistence.*;

@Entity
@Table(name = "Planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String producer;
    private Float flightHeight;

    public Plane(Long id, String producer, Float flightHeight) {
        this.id = id;
        this.producer = producer;
        this.flightHeight = flightHeight;
    }

    public Plane() {}

    public void print() {
        System.out.println(String.format("id = %d, producer = %s, flightHeight = %s", id, producer, flightHeight));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Float getFlightHeight() {
        return flightHeight;
    }

    public void setFlightHeight(Float flightHeight) {
        this.flightHeight = flightHeight;
    }
}

