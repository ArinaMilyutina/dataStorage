package com.example.datastorage.controller;

import com.example.datastorage.dto.client.ListClientResponse;
import com.example.datastorage.dto.client.RequestClientDto;
import com.example.datastorage.dto.client.ResponseClientDto;
import com.example.datastorage.entity.user.User;
import com.example.datastorage.service.ClientService;
import com.example.datastorage.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;

    @PostMapping("/create-client")
    public ResponseEntity<ResponseClientDto> createClient(@Valid @RequestBody RequestClientDto clientDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = (String) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByUsername(currentUsername);
        User user = currentUser.get();
        clientDto.setUserId(user.getId());
        return ResponseEntity.ok(clientService.createClient(clientDto));
    }


    @GetMapping("/clients")
    public ResponseEntity<ListClientResponse> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }


    @GetMapping("/client-by-surname/{surname}")
    public ResponseEntity<ListClientResponse> findClientBySurname(@PathVariable String surname) {
        return ResponseEntity.ok(clientService.findClientBySurname(surname));
    }

    @DeleteMapping("/delete-client/{number}")
    public ResponseEntity<String> deleteClient(@PathVariable String number) {
        clientService.deleteClientByPassportNumber(number);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-client/{number}")
    public ResponseEntity<ResponseClientDto> updateClient(@PathVariable String number, @RequestBody RequestClientDto clientDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = (String) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByUsername(currentUsername);
        User user = currentUser.get();
        clientDto.setUserId(user.getId());
        return ResponseEntity.ok(clientService.updateClientByPassportNumber(number, clientDto));
    }
}
