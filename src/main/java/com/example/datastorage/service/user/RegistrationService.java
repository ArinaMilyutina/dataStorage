package com.example.datastorage.service.user;

import com.example.datastorage.dto.user.RequestRegUserDto;
import com.example.datastorage.dto.user.ResponseRegUserDto;
import com.example.datastorage.entity.user.User;
import com.example.datastorage.mapper.UserMapper;
import com.example.datastorage.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private UserService userService;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ResponseRegUserDto regUser(RequestRegUserDto regUserDto) {
        User user = UserMapper.INSTANCE.regUserDtoToUser(regUserDto);
        userService.checkUsernameAvailability(user.getUsername());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userService.createUser(user);

        return ResponseRegUserDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}