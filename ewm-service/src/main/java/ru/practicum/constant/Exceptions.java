package main.java.ru.practicum.constant;

public class Exceptions {
    public static final String EXCEPTION_INTERNAL_SERVER = "Внутренняя ошибка сервера. Подождите несколько минут и попробуйте снова.";
    public static final String EXCEPTION_UNPROCESSABLE_ENTITY = "Недопустимый аргумент.";
    public static final String EXCEPTION_NOT_VALID = "Валидация не пройдена. Недопустимое значение.";
    public static final String EXCEPTION_NOT_READABLE = "Тело запроса не читаемо.";
    public static final String EXCEPTION_CONSTRAINT_VIOLATION = "Недопустимое значение.";
    public static final String EXCEPTION_NOT_FOUND = "Объект не найден, 404";
    public static final String EXCEPTION_NOT_FOUND_USER = "Пользователь с id=%d не найден";
    public static final String EXCEPTION_DATE_MISMATCH = "Дата не соответствует требованиям 409";
    public static final String EXCEPTION_LIMIT_EXCEEDED = "Лимит запросов превышен, код ошибки 409";
    public static final String EXCEPTION_NOT_RESPOND_STATUS = "Статус запросов должен быть PENDING код ошибки 409";
}
