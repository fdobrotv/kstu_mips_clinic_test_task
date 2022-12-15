package com.fdobrotv.kstu.mips.clinic_test_task.mapper;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;

import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Role.PATIENT;

public class UserDTOMapper {

    public static PatientDTO toPatientDTO(UserEntity userEntity) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(userEntity.getId());
        patientDTO.setName(userEntity.getName());
        patientDTO.setPhone(userEntity.getPhone());
        patientDTO.setEmail(userEntity.getEmail());
        patientDTO.setLastName(userEntity.getLastName());
        patientDTO.setBirthDate(userEntity.getBirthDate());
        return patientDTO;
    }
    public static UserEntity toEntity(PatientDTO patientDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(PATIENT);
        userEntity.setName(patientDTO.getName());
        userEntity.setPhone(patientDTO.getPhone());
        userEntity.setEmail(patientDTO.getEmail());
        userEntity.setLastName(patientDTO.getLastName());
        userEntity.setBirthDate(patientDTO.getBirthDate());
        return userEntity;
    }

    public static UserDTO toDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setName(userEntity.getName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setRole(userEntity.getRole());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setBirthDate(userEntity.getBirthDate());
        userDTO.setPhone(userEntity.getPhone());
        return userDTO;
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setRole(userDTO.getRole());
        userEntity.setSpeciality(userDTO.getSpeciality());
        userEntity.setName(userDTO.getName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBirthDate(userDTO.getBirthDate());
        return userEntity;
    }
}
