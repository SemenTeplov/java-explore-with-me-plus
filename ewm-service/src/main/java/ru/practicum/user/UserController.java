package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {
    private final UserService userService;

    public UserDto addUser(NewUserRequest newUserRequest) {
        return userService.addUser(newUserRequest);
    }

    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return userService.getUsers(ids, from, size);
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
}
