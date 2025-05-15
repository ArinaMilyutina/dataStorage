package com.example.datastorage.repository;

import com.example.datastorage.entity.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findClientBySurname(String surname);

    void deleteByPassportNumber(String number);

    Client findClientById(Long id);

    Optional<Client> findClientByPassportNumber(String passportNumber);
}
