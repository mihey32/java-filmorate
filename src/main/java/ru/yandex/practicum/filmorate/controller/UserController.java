package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validation.ValidationServiceImp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    ValidationServiceImp validationService = new ValidationServiceImp();
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User newUser) {
        log.info("Получен запрос на создание нового пользователя: {}", newUser);
        // проверяем выполнение необходимых условий
        validationService.validate(newUser, users);
        newUser.setId(getNextId());
        // сохраняем нового пользователя в памяти приложения
        users.put(newUser.getId(), newUser);
        log.info("Новый пользователь успешно создан: {}", newUser);
        return newUser;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User updateUser) {
        log.info("Получен запрос на обновление пользователя: {}", updateUser);
        validationService.validateUpdate(updateUser, users);
        users.put(updateUser.getId(), updateUser);
        log.info("Пользователь успешно обнавлен: {}", updateUser);
        return updateUser;
    }

    // вспомогательные метод
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
