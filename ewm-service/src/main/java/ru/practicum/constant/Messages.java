package main.java.ru.practicum.constant;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class Messages {
    public static final String GET_COMPILATIONS = "Пришел запрос на получение подборки событий";
    public static final String GET_COMPILATION = "Пришел запрос на получение подборки события по id {}";
    public static final String NOT_FOUND_COMPLETION = "Подборка событий по id не найдено";
    public static final String METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION = "Ошибка валидации";
    public static final String EXCEPTION = "Внутренняя ошибка сервера";
    public static final String SAVE_COMPILATION = "Пришел запрос на сохранение подборки события {}";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "Не удалось выполнить SQL запрос";
    public static final String DELETE_COMPILATION = "Пришел запрос на удаление подборки события {}";
    public static final String UPDATE_COMPILATION = "Пришел запрос на обновление подборки события {}";
}
