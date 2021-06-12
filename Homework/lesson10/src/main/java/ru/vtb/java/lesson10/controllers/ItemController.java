package ru.vtb.java.lesson10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.lesson10.models.Item;
import ru.vtb.java.lesson10.services.ItemService;
import ru.vtb.java.lesson10.utils.AppError;
import ru.vtb.java.lesson10.utils.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<Item> findAllItems() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<Item> result = itemService.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Item with id: " + id + " not found"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("addorupdate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addOrUpdate(@RequestBody Item item) {
        if (item.getPrice() <= 0){
            return new ResponseEntity<>(new AppError(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value(), "Price has to be bigger then 0. " +
                    "Gotten price: " + item.getPrice()), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }

        var savedItem = itemService.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable Long id) {
        try {
            itemService.delete(id);
        }
        catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Can't delete item with id: " + id);
        }
    }
}
