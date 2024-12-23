package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validation.ValidationServiceImp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final ValidationServiceImp validationService = new ValidationServiceImp();
    private final Map<Long, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getAllFilm() {
        return films.values();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film newFilm) {
        log.info("Получен запрос на создание нового пользователя: {}", newFilm);
        validationService.validate(newFilm);
        newFilm.setId(getNextId());
        films.put(newFilm.getId(), newFilm);
        log.info("Новый фильм успешно добавлен: {}", newFilm);
        return newFilm;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film updateFilm) {
        log.info("Получен запрос на создание нового пользователя: {}", updateFilm);
        validationService.validateUpdate(updateFilm, films);
        films.put(updateFilm.getId(), updateFilm);
        log.info("Новый фильм успешно добавлен: {}", updateFilm);
        return updateFilm;

    }

    // вспомогательные метод
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}