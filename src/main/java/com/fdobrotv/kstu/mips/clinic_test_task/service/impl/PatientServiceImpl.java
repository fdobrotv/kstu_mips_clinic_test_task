package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.StaffDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.UserDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.WorkSlotMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.TimeSlotRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.AdministrationService;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Role.STAFF;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    public PatientServiceImpl(UserRepository userRepository, TimeSlotRepository timeSlotRepository) {
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public List<Staff> getStaffBySpeciality(Speciality speciality) {
        Stream<UserEntity> userEntityStream = userRepository.findAllBySpeciality(speciality);
        return userEntityStream.map(StaffDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkSlot> getTimeSlotsByStaffId(UUID staffId) {
        Stream<TimeSlotEntity> allByStaffUserId = timeSlotRepository.findAllByStaffUserId(staffId);
        return allByStaffUserId.map(WorkSlotMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void bookTimeSlot(UUID timeslotId, UUID userId) {
        Optional<TimeSlotEntity> timeSlotEntityOptional = timeSlotRepository.findById(timeslotId);
        TimeSlotEntity timeSlotEntity = timeSlotEntityOptional.get();
        timeSlotEntity.setUserId(userId);
        timeSlotRepository.save(timeSlotEntity);
    }

    @Override
    public UserDTO getUserByEmail(String email) throws AccessDeniedException {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(AccessDeniedException::new);
        return UserDTOMapper.toDTO(userEntity);
    }

    @Override
    public List<WorkSlotView> getTimeSlots(UUID userId) {
        Stream<TimeSlotEntity> timeSlotEntityStream = timeSlotRepository.findAllByUserId(userId);
        return timeSlotEntityStream.map(timeSlotEntity -> {
            UserEntity doctor = userRepository.findById(timeSlotEntity.getStaffUserId()).get();
            return WorkSlotMapper.toDTOView(timeSlotEntity, doctor);
        }).collect(Collectors.toList());
    }
}
