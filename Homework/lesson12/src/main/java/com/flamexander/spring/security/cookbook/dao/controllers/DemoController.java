package com.flamexander.spring.security.cookbook.dao.controllers;

import com.flamexander.spring.security.cookbook.dao.entities.User;
import com.flamexander.spring.security.cookbook.dao.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// Две роли: user и admin. Роль user не расширена доп. правами доступа.
// Роль admin имеет 2 права доступа (rule1 и rule2).
// И есть еще третье право доступа (rule3), которое надо отдельно получить - ни одна из ролей его не предоставит.
// 4 пользователя: с ролью user, с ролью admin, с правом доступа rule1, с ролью user и уникальным правом rule3.
@RestController
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/unsecured")
    public String usecuredPage() {
        return "Авторизация не нужна.";
    }

    @GetMapping("/auth_page")
    public String authenticatedPage() {
        return "Это может только авторизованный пользователь.";
    }

    @GetMapping("/admin_page")
    public String adminPage() { return "Это может только пользователь с ролью admin."; }

    @GetMapping("/rule1_page")
    public String rule1Page() {
        return "Это может только пользователь с правом доступа rule1.";
    }

    @GetMapping("/rule3_page")
    public String rule3Page() {
        return "Это может только пользователь с правом доступа rule3.";
    }

    @GetMapping("/user_info")
    public String daoTestPage(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("Unable to find user by username: " + principal.getName()));
        return "Authenticated user info: " + user.getUsername() + " : " + user.getEmail();
    }
}
