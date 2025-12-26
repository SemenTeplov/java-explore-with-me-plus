package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.openapi.model.GetUserRequest;
import ru.practicum.openapi.model.NewUserRequest;
import ru.practicum.openapi.model.UserDto;
import ru.practicum.openapi.api.UserApi;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    @Override
    public ResponseEntity<Void> _deleteUser(Long userId) {
        log.info("DELETE /admin.users/{}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<UserDto>> _getUsers(
            List<Long> ids,
            Integer from,
            Integer size) {

        log.info("GET /admin/users?ids={}&from={}&size={}", ids, from, size);

        List<UserDto> users = userService.getUsers(ids, from, size);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDto> _registerUser(NewUserRequest newUserRequest) {
        log.info("POST /admin/users with request: {}", newUserRequest);
        UserDto userDto = userService.addUser(newUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
