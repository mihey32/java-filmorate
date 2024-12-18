package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = {"email"})
public class User {
    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
}
