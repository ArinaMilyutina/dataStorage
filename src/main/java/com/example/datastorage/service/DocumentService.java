package com.example.datastorage.service;

import com.example.datastorage.dto.ListDocumentDto;
import com.example.datastorage.dto.RequestDocumentDto;
import com.example.datastorage.dto.ResponseDocumentDto;
import com.example.datastorage.entity.Document;
import com.example.datastorage.entity.client.Client;
import com.example.datastorage.mapper.DocumentMapper;
import com.example.datastorage.repository.ClientRepository;
import com.example.datastorage.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Transactional
    public void createDocument(RequestDocumentDto documentDto, MultipartFile passportFile, MultipartFile applicationForm) throws IOException {
        Optional<Client> optionalClient = clientRepository.findClientByPassportNumber(documentDto.getPassportNumber());
        Client client = optionalClient.orElseThrow(() -> new RuntimeException("Client not found"));
        Document document = Document.builder()
                .clientId(client.getId())
                .documentType(documentDto.getDocumentType())
                .passportFile(passportFile.getBytes())
                .applicationForm(applicationForm.getBytes())
                .comments(documentDto.getComments())
                .build();
        documentRepository.save(document);
    }
    public ListDocumentDto findAll() {
        List<Document> documentList = documentRepository.findAll();
        return createListDocumentResponse(documentList);
    }

    public class Base64ImageConverterUtil {
        public String convertImageToBase64(byte[] image) {
            return Base64.getEncoder().encodeToString(image);
        }
    }


    private ResponseDocumentDto createdDocumentResponse(Document document) {
        Client client=clientRepository.findClientById(document.getClientId());
        return ResponseDocumentDto.builder()
                .passportNumber(client.getPassportNumber())
                .documentType(document.getDocumentType())
                .comments(document.getComments())
                .passportFile(encodeBase64(document.getPassportFile()))
                .applicationForm(encodeBase64(document.getApplicationForm()))
                .name(client.getName())
                .username(client.getSurname())
                .build();
    }

    private ListDocumentDto createListDocumentResponse(List<Document> documentList) {
        List<ResponseDocumentDto> documentDtoList = documentList.stream()
                .map(this::createdDocumentResponse)
                .collect(Collectors.toList());

        return ListDocumentDto.builder()
                .documentDtoList(documentDtoList)
                .build();
    }

    private String encodeBase64(byte[] file) {
        return file != null ? Base64.getEncoder().encodeToString(file) : null;
    }

}
