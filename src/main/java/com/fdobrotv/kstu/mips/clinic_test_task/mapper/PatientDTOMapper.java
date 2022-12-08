package com.fdobrotv.kstu.mips.clinic_test_task.mapper;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.model.PatientEntity;

public class PatientDTOMapper {
    public static PatientEntity toEntity(PatientDTO patientDTO) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setName(patientDTO.getName());
        patientEntity.setPhone(patientDTO.getPhone());
        patientEntity.setEmail(patientDTO.getEmail());
        patientEntity.setLastName(patientDTO.getLastName());
        patientEntity.setBirthDate(patientDTO.getBirthDate());
        return patientEntity;
    }
}
