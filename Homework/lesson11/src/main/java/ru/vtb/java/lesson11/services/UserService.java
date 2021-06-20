package ru.vtb.java.lesson11.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.java.lesson11.models.User;
import ru.vtb.java.lesson11.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
