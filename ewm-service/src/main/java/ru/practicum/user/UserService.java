package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.openapi.model.GetUserRequest;
import ru.practicum.openapi.model.NewUserRequest;
import ru.practicum.openapi.model.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
/*    private final UserRepository userRepository;

    public UserDto addUser(NewUserRequest newUserRequest) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    public List<UserDto> getUsers(GetUserRequest getUserRequest) {

    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id=%d не найден", userId));
        }
        userRepository.deleteById(userId);
    }*/
}
