package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface ValidationService {

    void validate(User user, Map<Long, User> usersMap);
    void validateUpdate(User user, Map<Long, User> usersMap);

    void validate(Film film);

    void validateUpdate(Film film, Map<Long, Film> films);
}
