package ru.vtb.java.lesson9.repositories.entities;

import javax.persistence.*;

@Entity
@Table(name = "Orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public OrderEntity() {}

    public OrderEntity(String title) {
        this.title = title;
    }

    public void print() {
        System.out.println(String.format("id = %d, title = %s", id, title));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}