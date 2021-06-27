package ru.vtb.java.lesson10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.java.lesson10.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
