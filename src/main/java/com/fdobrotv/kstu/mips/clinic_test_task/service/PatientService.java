package com.fdobrotv.kstu.mips.clinic_test_task.service;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface PatientService {
    List<Staff> getStaffBySpeciality(Speciality speciality);

    List<WorkSlot> getTimeSlotsByStaffId(UUID staffId);

    void bookTimeSlot(UUID timeslotId, UUID userId);

    UserDTO getUserByEmail(String email) throws AccessDeniedException;

    List<WorkSlotView> getTimeSlots(UUID userId);
}
