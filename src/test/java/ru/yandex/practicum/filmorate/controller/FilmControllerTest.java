package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private Film film;
    private final FilmController controller = new FilmController();

    @Test
    void checkValidateAddFilm() {
        film = new Film();
        film.setName("nisi eiusmod");
        film.setDescription("adipisicing");
        film.setReleaseDate(LocalDate.of(1967, 3, 25));
        film.setDuration(100);

        controller.addFilm(film);

        assertEquals(controller.getAllFilm().size(), 1);
    }

    @Test
    void checkValidateFilmName() {
        film = new Film();
        film.setName("");
        film.setDescription("adipisicing");
        film.setReleaseDate(LocalDate.of(1967, 3, 25));
        film.setDuration(100);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.addFilm(film)
        );
        assertEquals("Название не может быть пустым", exception.getMessage());
    }

    @Test
    void checkValidateFilmDuration() {
        film = new Film();
        film.setName("nisi eiusmod");
        film.setDescription("adipisicing");
        film.setReleaseDate(LocalDate.of(1967, 3, 25));
        film.setDuration(-100);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.addFilm(film)
        );

        assertEquals("Продолжительность фильма должна быть положительным числом", exception.getMessage());
    }

    @Test
    void checkValidateFilmReleaseDate() {
        film = new Film();
        film.setName("nisi eiusmod");
        film.setDescription("adipisicing");
        film.setReleaseDate(LocalDate.of(1894, 3, 25));
        film.setDuration(100);

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.addFilm(film)
        );

        assertEquals("Дата релиза — не раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    void checkValidateUpdateUserName() {
        film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2020, 3, 25));
        film.setDuration(150);

        controller.addFilm(film);

        film.setName("newName");
        film.setDescription("newDescription");

        controller.updateFilm(film);
        assertEquals(controller.getAllFilm().size(), 1);
    }

}
