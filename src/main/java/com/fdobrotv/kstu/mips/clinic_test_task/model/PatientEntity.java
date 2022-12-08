package com.fdobrotv.kstu.mips.clinic_test_task.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(schema = "clinic", name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue
    UUID id;
    String email;
    String phone;
    String name;
    String lastName;
    LocalDate birthDate;
}
