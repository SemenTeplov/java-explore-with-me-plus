package main.java.ru.practicum.user;

import lombok.RequiredArgsConstructor;
import main.java.ru.practicum.user.dto.GetUsersRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import main.java.ru.practicum.exception.NotFoundException;
import ru.practicum.openapi.model.NewUserRequest;
import ru.practicum.openapi.model.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto addUser(NewUserRequest newUserRequest) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(newUserRequest)));
    }

    public List<UserDto> getUsers(GetUsersRequest request) {
        Pageable pageable = PageRequest.of(request.getFrom() / request.getSize(), request.getSize());

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            List<User> users = userRepository.findAllByIdIn(request.getIds(), pageable);
            return users.stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            List<User> users = userRepository.findAll(pageable).getContent();
            return users.stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Пользователь с id=%d не найден", userId));
        }
        userRepository.deleteById(userId);
    }
}
