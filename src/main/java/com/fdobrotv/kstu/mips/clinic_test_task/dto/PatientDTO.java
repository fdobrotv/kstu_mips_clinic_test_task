package com.fdobrotv.kstu.mips.clinic_test_task.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    String email;
    String phone;
    String name;
    String lastName;
    LocalDate birthDate;
}
