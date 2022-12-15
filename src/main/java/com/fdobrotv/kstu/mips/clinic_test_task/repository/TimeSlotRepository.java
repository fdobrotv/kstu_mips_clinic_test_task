package com.fdobrotv.kstu.mips.clinic_test_task.repository;

import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlotEntity, UUID> {
    Stream<TimeSlotEntity> findAllByStaffUserId(UUID staffId);
    Stream<TimeSlotEntity> findAllByUserId(UUID userId);

    Stream<TimeSlotEntity> findAllByStaffUserIdAndUserIdNotNullAndDateGreaterThanEqual(UUID staffId, LocalDate now);
}
