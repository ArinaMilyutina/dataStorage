package com.example.datastorage.controller;

import com.example.datastorage.dto.ListDocumentDto;
import com.example.datastorage.dto.RequestDocumentDto;
import com.example.datastorage.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @PostMapping("/create-document")
    public ResponseEntity<Void> createDocument(
            @ModelAttribute RequestDocumentDto documentDto,
            @RequestPart("passportFile") MultipartFile passportFile,
            @RequestPart("applicationForm") MultipartFile applicationForm) throws IOException {
        documentService.createDocument(documentDto, passportFile, applicationForm);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/documents")
    public ResponseEntity<ListDocumentDto> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }
}

