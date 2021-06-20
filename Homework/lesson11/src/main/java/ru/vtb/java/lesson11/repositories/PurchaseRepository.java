package ru.vtb.java.lesson11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.vtb.java.lesson11.models.Purchase;
import ru.vtb.java.lesson11.models.User;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findPurchasesByUserId(long userId);

    @Query("select distinct u from Purchase p " +
           "inner join User u on p.userId = u.id " +
           "where p.itemId = ?1")
    List<User> findAllUsersForItem(long userId);
}
