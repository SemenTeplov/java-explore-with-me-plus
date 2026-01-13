package main.java.ru.practicum.constant;

public class Exceptions {
    public static final String NOT_FOUND_COMPLETION = "Подборка событий не найдена";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "Некорректный запрос.";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "Нарушение целостности данных";
    public static final String EXCEPTION_INTERNAL_SERVER = "Внутренняя ошибка сервера. Подождите несколько минут и попробуйте снова.";
    public static final String EXCEPTION_NOT_VALID = "Валидация не пройдена. Недопустимое значение.";
    public static final String EXCEPTION_NOT_READABLE = "Тело запроса не читаемо.";
    public static final String EXCEPTION_NOT_FOUND = "Объект не найден, 404";
    public static final String EXCEPTION_NOT_FOUND_USER = "Пользователь с id=%d не найден";
    public static final String EXCEPTION_DATE_MISMATCH = "Дата не соответствует требованиям 409";
    public static final String EXCEPTION_LIMIT_EXCEEDED = "Лимит запросов превышен, код ошибки 409";
    public static final String EXCEPTION_NOT_RESPOND_STATUS = "Статус запросов должен быть PENDING код ошибки 409";
    public static final String EXCEPTION_EVENT_NOT_FOUND = "Событие с id=%d не найдено";
    public static final String EXCEPTION_REQUEST_EXIST = "Запрос уже существует";
    public static final String EXCEPTION_REQUEST_INITIATOR_OWN = "Инициатор не может отправлять запросы на собственное мероприятие.";
    public static final String EXCEPTION_REQUEST_NOT_PUBLISHED = "Участие возможно только в опубликованном мероприятии.";
    public static final String EXCEPTION_REQUEST_LIMIT = "Достигнут лимит участников.";
    public static final String EXCEPTION_USER_NOT_INITIATOR = "Пользователь не инициатор события";
    public static final String EXCEPTION_NOT_MEET_RULES = "Не соответствет требованиям.";
    public static final String EXCEPTION_NOT_ILLEGAL_ARGUMENT = "Используется не допустимое значение";
    public static final String EXCEPTION_CONFLICT_CATEGORY = "Категория с именем %s уже существует";
    public static final String EXCEPTION_CANT_DELETE_CATEGORY = "Невозможно удалить категорию, так как с ней связаны события";
    public static final String EXCEPTION_CONFLICT_EMAIL = "Пользователь с email %s уже существует";
    public static final String EXCEPTION_CANT_UPDATE_PUBLISHED = "Нельзя изменить опубликованное событие";
    public static final String EXCEPTION_VALID_EMAIL = "Email не может быть пустым или состоять только из пробелов";
    public static final String EXCEPTION_VALID_NAME = "Имя не может быть пустым или состоять только из пробелов";
}
