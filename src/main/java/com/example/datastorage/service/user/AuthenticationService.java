package com.example.datastorage.service.user;

import com.example.datastorage.dto.user.RequestAuthUserDto;
import com.example.datastorage.dto.user.ResponseAuthUserDto;
import com.example.datastorage.entity.user.User;
import com.example.datastorage.exception.IncorrectPasswordException;
import com.example.datastorage.exception.NotFoundException;
import com.example.datastorage.jwt.JWTTokenProvider;
import com.example.datastorage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private static final String INCORRECT_PASSWORD = "Incorrect password!!!";
    private static final String USER_NOT_FOUND = "User not found!!!";

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public ResponseAuthUserDto authenticate(RequestAuthUserDto authUserDto) throws NotFoundException {
        User userToAuthenticate = UserMapper.INSTANCE.loginUserDtoToUser(authUserDto);
        Optional<User> byUsername = userService.findByUsername(userToAuthenticate.getUsername());
        if (byUsername.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        User user = byUsername.get();
        if (passwordEncoder.matches(authUserDto.getPassword(), user.getPassword())) {
            return ResponseAuthUserDto.builder().token(jwtTokenProvider.generateToken(user)).build();
        }
        throw new IncorrectPasswordException(INCORRECT_PASSWORD);
    }
}



