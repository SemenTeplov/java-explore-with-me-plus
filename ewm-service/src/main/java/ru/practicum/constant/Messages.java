package main.java.ru.practicum.constant;

public class Messages {
    public static final String INFORMATION_ADDED = "Информация сохранена";
    public static final String POST_HIT_REQUEST = "POST /hit: app={}, uri={}, ip={}";
    public static final String GET_STATS_REQUEST = "GET /stats: start={}, end={}, uris={}, unique={}";
    public static final String SAVE_HIT_EXCEPTION = "Ошибка при сохранении hit";
    public static final String DATE_EXCEPTION = "Дата От может быть только после даты До";
    public static final String MESSAGE_INTERNAL_SERVER = "Внутренняя ошибка сервера: {}";
    public static final String MESSAGE_UNPROCESSABLE_ENTITY = "Некорректный аргумент: {}";
    public static final String MESSAGE_NOT_VALID = "Значение не прошло валидацию: {}";
    public static final String MESSAGE_NOT_READABLE = "Тело запроса не читаемо: {}";
    public static final String MESSAGE_CONSTRAINT_VIOLATION = "Недопустимое значение: {}";
    public static final String MESSAGE_CATEGORY_NOT_FOUND = "Категория с id=%d не найдена";
    public static final String MESSAGE_NOT_FOUND = "Требуемый объект не был найден.";
    public static final String MESSAGE_ADD_CATEGORIES = "POST /admin/categories with request: {}";
    public static final String MESSAGE_DELETE_CATEGORIES = "DELETE /admin/categories/{}";
    public static final String MESSAGE_GET_CATEGORIES = "GET /categories";
    public static final String MESSAGE_GET_CATEGORY = "GET /categories/{}";
    public static final String MESSAGE_UPDATE_CATEGORY = "PATCH /admin/categories/{} with request: {}";
    public static final String MESSAGE_DELETE_USER = "DELETE /admin.users/{}";
    public static final String MESSAGE_GET_USERS = "GET /admin/users?ids={}&from={}&size={}";
    public static final String MESSAGE_REGISTER_USER = "POST /admin/users with request: {}";
    public static final String MESSAGE_ADD_EVENT = "POST /users/{}/events users with request: {}";
    public static final String MESSAGE_DATE_MISMATCH = "Дата не соответствует требованиям";
}
