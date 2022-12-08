package com.fdobrotv.kstu.mips.clinic_test_task.repository;

import com.fdobrotv.kstu.mips.clinic_test_task.model.PatientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends CrudRepository<PatientEntity, UUID> {
}
