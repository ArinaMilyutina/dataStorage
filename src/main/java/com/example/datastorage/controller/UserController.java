package com.example.datastorage.controller;

import com.example.datastorage.dto.user.*;
import com.example.datastorage.entity.user.Role;
import com.example.datastorage.exception.NotFoundException;
import com.example.datastorage.service.user.AuthenticationService;
import com.example.datastorage.service.user.RegistrationService;
import com.example.datastorage.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public ResponseEntity<ResponseRegUserDto> registrationUser(@Valid @RequestBody RequestRegUserDto userDto) {
        userDto.setRoles(Set.of(Role.USER));
        return ResponseEntity.ok(registrationService.regUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseAuthUserDto> loginUser(@RequestBody RequestAuthUserDto authUserDto) throws NotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(authUserDto));
    }

    @DeleteMapping("/delete-user/{username}")
    public ResponseEntity<Void> deleteUser(@RequestParam String username) throws NotFoundException {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<ListUserDto> findAll() throws NotFoundException {
        return ResponseEntity.ok(userService.findAll());
    }
}
