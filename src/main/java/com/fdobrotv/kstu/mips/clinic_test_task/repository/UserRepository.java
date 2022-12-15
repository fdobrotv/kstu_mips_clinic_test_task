package com.fdobrotv.kstu.mips.clinic_test_task.repository;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Role;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Stream<UserEntity> findAllByRole(Role role);
    Stream<UserEntity> findAllBySpeciality(Speciality speciality);

    Optional<UserEntity> findByEmail(String email);
}
