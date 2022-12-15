package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.UserDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientRegistrationService;
import org.springframework.stereotype.Service;

@Service
public class PatientRegistrationServiceImpl implements PatientRegistrationService {
    private final UserRepository userRepository;

    public PatientRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addPatient(PatientDTO patientDTO) {
        UserEntity userEntity = UserDTOMapper.toEntity(patientDTO);
        userRepository.save(userEntity);
    }
}
