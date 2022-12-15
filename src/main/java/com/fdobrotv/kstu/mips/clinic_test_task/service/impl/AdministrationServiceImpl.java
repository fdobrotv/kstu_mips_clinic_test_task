package com.fdobrotv.kstu.mips.clinic_test_task.service.impl;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Staff;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlot;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.StaffDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.UserDTOMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.mapper.WorkSlotMapper;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.TimeSlotRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.service.AdministrationService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Role.*;

@Service
@Transactional
public class AdministrationServiceImpl implements AdministrationService {
    private final UserRepository userRepository;
    private final TimeSlotRepository timeSlotRepository;

    public AdministrationServiceImpl(UserRepository userRepository, TimeSlotRepository timeSlotRepository) {
        this.userRepository = userRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        UserEntity userEntity = UserDTOMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }

    @Override
    public void removeUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Staff> getStaff() {
        Stream<UserEntity> userEntityStream = userRepository.findAllByRole(STAFF);
        return userEntityStream.map(StaffDTOMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<WorkSlot> getTimeSlots(UUID userId) {
        Stream<TimeSlotEntity> allByStaffUserId = timeSlotRepository.findAllByStaffUserId(userId);

        return allByStaffUserId.map(WorkSlotMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addWorkSlot(WorkSlot workSlot) {
        TimeSlotEntity timeSlotEntity = WorkSlotMapper.toEntity(workSlot);

        timeSlotRepository.save(timeSlotEntity);
    }
}
