package main.java.ru.practicum.user;

import lombok.experimental.UtilityClass;
import main.java.ru.practicum.user.dto.UserShortDto;
import ru.practicum.openapi.model.NewUserRequest;
import ru.practicum.openapi.model.UserDto;

@UtilityClass
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getEmail(),
                user.getId(),
                user.getName()
        );
    }

    public UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public User toUser(NewUserRequest newUserRequest) {
        return new User(
                newUserRequest.getName(),
                newUserRequest.getEmail()
        );
    }
}
