package com.example.datastorage.mapper;
import com.example.datastorage.dto.client.RequestClientDto;
import com.example.datastorage.entity.client.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    Client clientDtoToClient(RequestClientDto clientDto);
}