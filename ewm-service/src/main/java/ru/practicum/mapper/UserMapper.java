package main.java.ru.practicum.mapper;

import main.java.ru.practicum.persistence.entity.User;

import org.mapstruct.Mapper;

import ru.practicum.openapi.model.NewUserRequest;
import ru.practicum.openapi.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserDto toUserDto(User user);

    public User toUser(NewUserRequest newUserRequest);
}
