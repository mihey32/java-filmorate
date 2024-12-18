package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private User user;
    private final UserController controller = new UserController();

    @Test
    void checkValidateСreateUser() {
        user = new User();
        user.setEmail("User@yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));

        controller.createUser(user);
        assertEquals(controller.getAllUsers().size(), 1);
    }

    @Test
    void checkValidateUserLogin() {
        user = new User();
        user.setEmail("User@yandex.ru");
        user.setLogin("ya ndex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(user)
        );
        assertEquals("Логин не может быть пустым и содержать пробелы", exception.getMessage());
    }

    @Test
    void checkValidateUserEmail() {
        user = new User();
        user.setEmail("Useryandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(user)
        );
        assertEquals("Должен быть указан корректный email", exception.getMessage());
    }

    @Test
    void checkDuplicateEmails() {
        user = new User();
        user.setEmail("User@yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));
        controller.createUser(user);

        User user1 = new User();
        user1.setEmail("User@yandex.ru");
        user1.setLogin("yandex1");
        user1.setName("Yandex1");
        user1.setBirthday(LocalDate.of(2000, 8, 10));

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.createUser(user1)
        );
        assertEquals("Этот имейл уже используется", exception.getMessage());
    }

    @Test
    void checkValidateUpdateUserName() {
        user = new User();
        user.setEmail("User@yandex.ru");
        user.setLogin("yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));

        controller.createUser(user);

        assertEquals(user.getName(),"yandex");
    }

    @Test
    void checkValidateUpdateUser() {
        user = new User();
        user.setEmail("User@yandex.ru");
        user.setLogin("yandex");
        user.setName("Yandex");
        user.setBirthday(LocalDate.of(2000, 8, 10));

        controller.createUser(user);

        user.setLogin("yandex1");
        user.setName("Yandex1");

        controller.updateUser(user);

        assertEquals(controller.getAllUsers().size(), 1);
    }
}
