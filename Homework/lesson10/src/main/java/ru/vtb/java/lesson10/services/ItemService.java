package ru.vtb.java.lesson10.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.java.lesson10.models.Item;
import ru.vtb.java.lesson10.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
