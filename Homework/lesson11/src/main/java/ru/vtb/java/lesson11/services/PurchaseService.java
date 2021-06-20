package ru.vtb.java.lesson11.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.java.lesson11.models.Item;
import ru.vtb.java.lesson11.models.Purchase;
import ru.vtb.java.lesson11.models.User;
import ru.vtb.java.lesson11.repositories.ItemRepository;
import ru.vtb.java.lesson11.repositories.PurchaseRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ItemRepository itemRepository;

    public Purchase addPurchase(Purchase purchase) {
        var itemFromDb = itemRepository.findById(purchase.getItemId());
        if (!itemFromDb.isPresent())
            throw new NullPointerException("No items with id: " + purchase.getItemId());

        purchase.setPrice(itemFromDb.get().getPrice());
        purchase.setPurchaseDate(new Date());

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> findAllItemsForUser(long userId) {
        return purchaseRepository.findPurchasesByUserId(userId);
    }

    public List<User> findAllUsersForItem(Long itemId) {
        return purchaseRepository.findAllUsersForItem(itemId);
    }
}
