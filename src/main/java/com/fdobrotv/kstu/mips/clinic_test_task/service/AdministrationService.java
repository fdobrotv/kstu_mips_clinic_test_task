package com.fdobrotv.kstu.mips.clinic_test_task.service;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Staff;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlot;

import java.util.List;
import java.util.UUID;

public interface AdministrationService {
    void addUser(UserDTO userDTO);
    void removeUser(UUID id);

    List<Staff> getStaff();

    List<WorkSlot> getTimeSlots(UUID userId);

    void addWorkSlot(WorkSlot workSlot);
}
