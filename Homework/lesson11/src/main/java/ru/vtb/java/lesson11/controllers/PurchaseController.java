package ru.vtb.java.lesson11.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.lesson11.models.Purchase;
import ru.vtb.java.lesson11.models.User;
import ru.vtb.java.lesson11.services.PurchaseService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping("buy")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addPurchase(@RequestBody Purchase purchase) {
        var savedPurchase = purchaseService.addPurchase(purchase);
        return new ResponseEntity<>(savedPurchase, HttpStatus.OK);
    }

    @GetMapping("byuserid/{userId}")
    public List<Purchase> findAllItemsForUser(@PathVariable long userId) {
        return purchaseService.findAllItemsForUser(userId);
    }

    @GetMapping("byitemid/{itemId}")
    public List<User> findAllUsersForItem(@PathVariable long itemId) {
        return purchaseService.findAllUsersForItem(itemId);
    }
}
