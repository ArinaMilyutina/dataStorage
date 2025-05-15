package com.example.datastorage.service;

import com.example.datastorage.dto.client.ListClientResponse;
import com.example.datastorage.dto.client.RequestClientDto;
import com.example.datastorage.dto.client.ResponseClientDto;
import com.example.datastorage.entity.client.Client;
import com.example.datastorage.mapper.ClientMapper;
import com.example.datastorage.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ResponseClientDto createClient(RequestClientDto clientDto) {
        Client client = ClientMapper.INSTANCE.clientDtoToClient(clientDto);
        clientRepository.save(client);
        return ResponseClientDto.builder().name(client.getName()).surname(client.getSurname()).build();
    }

    public ListClientResponse findAll() {
        List<Client> clientList = clientRepository.findAll();
        return createListClientResponse(clientList);
    }

    private ResponseClientDto createdClientResponse(Client client) {
        return ResponseClientDto.builder()
                .name(client.getName())
                .surname(client.getSurname())
                .dateOfBirth(client.getDateOfBirth())
                .gender(String.valueOf(client.getGender()))
                .citizenship(client.getCitizenship())
                .passportSeries(client.getPassportSeries())
                .passportNumber(client.getPassportNumber())
                .passportStartDate(client.getPassportStartDate())
                .passportEndDate(client.getPassportEndDate())
                .email(client.getEmail())
                .number(client.getNumber())
                .build();
    }

    private ListClientResponse createListClientResponse(List<Client> clientList) {
        List<ResponseClientDto> clientResponses = clientList.stream()
                .map(this::createdClientResponse)
                .collect(Collectors.toList());
        return ListClientResponse.builder().clientDtoList(clientResponses).build();
    }

    public ListClientResponse findClientBySurname(String surname) {
        List<Client> clientList = clientRepository.findClientBySurname(surname);
        return createListClientResponse(clientList);
    }
    public Client findClientById(Long id) {
        Optional<Client> clientOptional= clientRepository.findById(id);
        Client client=clientOptional.get();
        return client;
    }

    @Transactional
    public void deleteClientByPassportNumber(String number) {
        clientRepository.deleteByPassportNumber(number);
    }

    public ResponseClientDto updateClientByPassportNumber(String passportNumber, RequestClientDto clientDto) {
        Optional<Client> clientOptional = clientRepository.findClientByPassportNumber(passportNumber);
        if (clientOptional.isEmpty()) {

        }
        Client client = ClientMapper.INSTANCE.clientDtoToClient(clientDto);
        client.setId(clientOptional.get().getId());
        Optional<Client> existingClient = clientRepository.findClientByPassportNumber(client.getPassportNumber());
        if (existingClient.isPresent()) {
        }
        Client updatedClient = clientRepository.save(client);
        return createdClientResponse(updatedClient);
    }
}
