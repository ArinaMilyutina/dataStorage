package com.example.datastorage.entity.client;

import com.example.datastorage.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surname;
    private String name;
    private String dateOfBirth;
    private Gender gender;
    private String citizenship;
    private String passportSeries;
    private String passportNumber;
    private String passportStartDate;
    private String passportEndDate;
    private String email;
    private String number;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
