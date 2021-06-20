package ru.vtb.java.lesson11.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userid")
    private long userId;

    @Column(name = "itemid")
    private long itemId;

    @Column(name = "price")
    private int price;

    @Column(name = "purchasedate")
    private Date purchaseDate;
}
