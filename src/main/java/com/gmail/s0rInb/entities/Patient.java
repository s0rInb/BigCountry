package com.gmail.s0rInb.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Created by s0rInb on 11.07.2017.
 */
@Entity
@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 320)
    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @Size(max = 320)
    @NotNull
    @Column(name="surname", nullable = false)
    private String lastName;

    @Size(max = 320)
    @Column(name="patronymic")
    private String patronymic;

    @Column(name = "sex")
    private LocalDate birthday;

    @Column(name = "info_consent")
    private Boolean infoConsent;

    @Column(name = "info_consent_date")
    private LocalDate infoConsentDate;

    @Column(name = "phone")
    @Size(max = 30)
    private String phone;
    @Size(max = 120)
    @Column(name = "email")
    private String email;

    @Size(max = 520)
    @Column(name = "address")
    private String address;

}
