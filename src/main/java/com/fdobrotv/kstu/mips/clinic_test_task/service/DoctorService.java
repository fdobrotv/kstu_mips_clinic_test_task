package com.fdobrotv.kstu.mips.clinic_test_task.service;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    UserDTO getUserByEmail(String email);

    List<PatientDTO> getPatientList(UUID doctorId);

    void savePrescription(String text, UUID doctorId, UUID patientId);
}
