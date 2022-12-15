package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.UserDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.PrescriptionEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.PrescriptionRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.TimeSlotRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.DoctorService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final PrescriptionRepository prescriptionRepository;

    public DoctorServiceImpl(UserRepository userRepository,
                             TimeSlotRepository timeSlotRepository,
                             PrescriptionRepository prescriptionRepository) {
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTOMapper::toDTO).get();
    }

    @Override
    public List<PatientDTO> getPatientList(UUID doctorId) {
        Stream<TimeSlotEntity> allByStaffUserIdAndDateGreaterThanEqual = timeSlotRepository
                        .findAllByStaffUserIdAndUserIdNotNullAndDateGreaterThanEqual(doctorId, LocalDate.now());
        return allByStaffUserIdAndDateGreaterThanEqual.map(timeSlotEntity -> {
            UserEntity userEntity = userRepository.findById(timeSlotEntity.getUserId()).get();
            return UserDTOMapper.toPatientDTO(userEntity);
        }).collect(Collectors.toList());
    }

    @Override
    public void savePrescription(String text, UUID doctorId, UUID patientId) {
        PrescriptionEntity prescriptionEntity = new PrescriptionEntity();
        prescriptionEntity.setCreatedAt(Instant.now());
        prescriptionEntity.setStaffUserId(doctorId);
        prescriptionEntity.setUserId(patientId);
        prescriptionEntity.setText(text);
        prescriptionRepository.save(prescriptionEntity);
    }
}
