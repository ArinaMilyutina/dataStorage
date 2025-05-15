package com.example.datastorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDocumentDto {
    private String passportNumber;
    private String documentType;
    private String comments;
    private String passportFile;
    private String applicationForm;
    private String name;
    private String username;
}