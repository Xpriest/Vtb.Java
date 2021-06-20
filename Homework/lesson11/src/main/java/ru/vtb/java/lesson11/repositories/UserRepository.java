package ru.vtb.java.lesson11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vtb.java.lesson11.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
