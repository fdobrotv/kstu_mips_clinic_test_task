package com.fdobrotv.kstu.mips.clinic_test_task.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class Staff {
    @NotNull
    UUID id;
    String fullName;
    String lastName;
    String email;
    Speciality speciality;
}
