package crud.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Long id) {
        super("Пользователь с" + id + " не найден");
    }

    public NotFoundException(String name, String surname) {
        super("Пользователь по имени " + name + " " + surname + " не найден");
    }

    public NotFoundException(String s) {
        super("Пользователь c реквизитом " + s + " не найден");
    }
}