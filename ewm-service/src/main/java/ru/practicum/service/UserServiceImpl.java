package main.java.ru.practicum.service;

import lombok.RequiredArgsConstructor;

import main.java.ru.practicum.constant.Exceptions;
import main.java.ru.practicum.mapper.UserMapper;
import main.java.ru.practicum.persistence.entity.User;
import main.java.ru.practicum.dto.GetUsersRequest;
import main.java.ru.practicum.persistence.repository.UserRepository;

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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto addUser(NewUserRequest newUserRequest) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(newUserRequest)));
    }

    @Override
    public List<UserDto> getUsers(GetUsersRequest request) {
        Pageable pageable = PageRequest.of(request.getFrom() / request.getSize(), request.getSize());

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            List<User> users = userRepository.findAllByIdIn(request.getIds(), pageable);
            return users.stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            List<User> users = userRepository.findAll(pageable).getContent();
            return users.stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(Exceptions.EXCEPTION_NOT_FOUND_USER, userId));
        }
        userRepository.deleteById(userId);
    }
}
