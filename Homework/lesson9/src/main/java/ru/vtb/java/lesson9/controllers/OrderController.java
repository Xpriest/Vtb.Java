package ru.vtb.java.lesson9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.lesson9.models.Order;
import ru.vtb.java.lesson9.services.OrderService;

import java.util.List;

@RestController
@RequestMapping(name="/api/v1/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Order get(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id");

        return orderService.get(id);
    }

    @GetMapping("all")
    public List<Order> getAll() {
        return orderService.getAll();
    }

    @PostMapping("insert")
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody Order order) {
        orderService.insert(order);
    }

    @PostMapping("update")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody Order order) {
        orderService.update(order);
    }

    //@PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    //public void update(@RequestBody Order order) {
//        orderService.update(order);
    //}
}
