package ru.yandex.practicum.filmorate.validation;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class ValidationServiceImp implements ValidationService {

    @Override
    public void validate(User user, Map<Long, User> usersMap) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Должен быть указан корректный email");
        }

        if (checkEmail(user, usersMap.values())) {
            throw new ValidationException("Этот имейл уже используется");
        }

        if (user.getLogin() == null || user.getLogin().isEmpty() || checkLogin(user)) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }

    }

    @Override
    public void validateUpdate(User user, Map<Long, User> usersMap) {
        if (user.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        if (!usersMap.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с id " + user.getId() + " не найден");
        } else {
            validate(user, usersMap);
        }
    }

    @Override
    public void validate(Film film) {

        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Длина описания не может быть больше 200 символов");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        }
    }

    @Override
    public void validateUpdate(Film film, Map<Long, Film> films) {
        if (film.getId() == null) {
            throw new ValidationException("Id должен быть указан");
        }

        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Фильм с id " + film.getId() + " не найден");
        } else {
            validate(film);
        }
    }

    private boolean checkEmail(User user, Collection<User> userCollection) {
        boolean check = false;
        for (User user1 : userCollection) {
            if (user1.getEmail().equals(user.getEmail())) {
                if (user1 == user) {
                    continue;
                }
                check = true;
                break;
            }
        }
        return check;
    }

    private boolean checkLogin(User user) {
        //String trimmedLogin = user.getLogin().trim();
        String[] parts = user.getLogin().split("\\s+");
        //return !trimmedLogin.equals(user.getLogin());
        return parts.length > 1;
    }

}
