package com.fdobrotv.kstu.mips.clinic_test_task.repository;

import com.fdobrotv.kstu.mips.clinic_test_task.model.PrescriptionEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface PrescriptionRepository extends CrudRepository<PrescriptionEntity, UUID> {
    Stream<PrescriptionEntity> findAllByUserId(UUID userId);
}
