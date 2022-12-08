package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.PatientDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.PatientEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.PatientRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService {
    private final PatientRepository patientRepository;

    public PatientRegistrationServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void addPatient(PatientDTO patientDTO) {
        PatientEntity patientEntity = PatientDTOMapper.toEntity(patientDTO);
        patientRepository.save(patientEntity);
    }
}
