package com.example.datastorage.mapper;

import com.example.datastorage.dto.RequestDocumentDto;
import com.example.datastorage.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import org.mapstruct.Mapping;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);

    @Mapping(target = "passportFile", expression = "java(decodeBase64(requestDocumentDto.getPassportFile()))")
    @Mapping(target = "applicationForm", expression = "java(decodeBase64(requestDocumentDto.getApplicationForm()))")
    Document documentDtoToDocument(RequestDocumentDto requestDocumentDto);

    default byte[] decodeBase64(String base64String) {
        if (base64String == null) {
            return null;
        }
        return java.util.Base64.getDecoder().decode(base64String);
    }
}
