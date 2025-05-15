package com.example.datastorage.service.user;

import com.example.datastorage.dto.user.ListUserDto;
import com.example.datastorage.dto.user.ResponseRegUserDto;
import com.example.datastorage.entity.user.User;
import com.example.datastorage.exception.AlreadyExistsException;
import com.example.datastorage.exception.NotFoundException;
import com.example.datastorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "User not found !!!";
    private static final String USER_ALREADY_EXISTS = "Username already exists!!!";

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }


    public void checkUsernameAvailability(String username) {
        Optional<User> byUsername = findByUsername(username);
        if (byUsername.isPresent()) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS);
        }
    }

    public void deleteUserByUsername(String username) {
        Optional<User> byUsername = findByUsername(username);
        if (byUsername.isPresent()) {
            throw new AlreadyExistsException(USER_ALREADY_EXISTS);
        }
        userRepository.deleteByUsername(username);
    }

    public ListUserDto findAll() {
        List<User> userList = userRepository.findAll();
        return createListUserResponse(userList);
    }

    private ResponseRegUserDto createdUserResponse(User user) {
        return ResponseRegUserDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }

    private ListUserDto createListUserResponse(List<User> userList) {
        List<ResponseRegUserDto> userDtoList = userList.stream()
                .map(this::createdUserResponse)
                .collect(Collectors.toList());
        return ListUserDto.builder().userDtoList(userDtoList).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> byUsername = userRepository.findUserByUsername(username);
        if (byUsername.isPresent()) {
            return byUsername.get();
        }
        throw new NotFoundException(USER_NOT_FOUND);
    }
}
