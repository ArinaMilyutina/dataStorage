package com.example.datastorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDocumentDto {
    private String passportNumber;
    private Long clientId;
    private String documentType;
    private String comments;
}
