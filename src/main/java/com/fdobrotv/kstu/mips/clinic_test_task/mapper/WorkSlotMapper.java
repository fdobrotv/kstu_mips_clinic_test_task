package com.fdobrotv.kstu.mips.clinic_test_task.mapper;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlot;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlotView;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;

import java.util.Objects;

public class WorkSlotMapper {
    public static WorkSlot toDTO(TimeSlotEntity timeSlotEntity) {
        WorkSlot workSlot = new WorkSlot();
        workSlot.setId(timeSlotEntity.getId());
        workSlot.setStaffUserId(timeSlotEntity.getStaffUserId());
        workSlot.setDate(timeSlotEntity.getDate());
        workSlot.setTimeFrom(timeSlotEntity.getTimeFrom());
        workSlot.setTimeTo(timeSlotEntity.getTimeTo());
        return workSlot;
    }

    public static TimeSlotEntity toEntity(WorkSlot workSlot) {
        TimeSlotEntity timeSlotEntity = new TimeSlotEntity();

        Objects.requireNonNull(workSlot.getStaffUserId());
        timeSlotEntity.setStaffUserId(workSlot.getStaffUserId());
        timeSlotEntity.setDate(workSlot.getDate());
        timeSlotEntity.setTimeFrom(workSlot.getTimeFrom());
        timeSlotEntity.setTimeTo(workSlot.getTimeTo());
        return timeSlotEntity;
    }

    public static WorkSlotView toDTOView(TimeSlotEntity timeSlotEntity, UserEntity userEntity) {
        WorkSlotView workSlotView = new WorkSlotView();
        workSlotView.setId(timeSlotEntity.getId());
        workSlotView.setDoctorFIO(userEntity.getLastName() + " " + userEntity.getName());
        workSlotView.setDate(timeSlotEntity.getDate());
        workSlotView.setTimeFrom(timeSlotEntity.getTimeFrom());
        workSlotView.setTimeTo(timeSlotEntity.getTimeTo());
        return workSlotView;
    }
}
