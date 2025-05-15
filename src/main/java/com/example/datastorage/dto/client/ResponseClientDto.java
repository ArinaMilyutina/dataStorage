package com.example.datastorage.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseClientDto {
    private String surname;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String citizenship;
    private String passportSeries;
    private String passportNumber;
    private String passportStartDate;
    private String passportEndDate;
    private String email;
    private String number;
}
