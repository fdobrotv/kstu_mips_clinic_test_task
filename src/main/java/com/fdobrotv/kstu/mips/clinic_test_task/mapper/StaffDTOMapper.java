package com.fdobrotv.kstu.mips.clinic_test_task.mapper;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Staff;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;

import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Role.PATIENT;

public class StaffDTOMapper {

    public static Staff toDTO(UserEntity userEntity) {
        Staff staff = new Staff();
        staff.setId(userEntity.getId());
        staff.setSpeciality(userEntity.getSpeciality());
        staff.setFullName(userEntity.getName());
        staff.setLastName(userEntity.getLastName());
        staff.setEmail(userEntity.getEmail());
        return staff;
    }
}
