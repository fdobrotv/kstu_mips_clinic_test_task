package com.fdobrotv.kstu.mips.clinic_test_task.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class WorkSlotView {
    UUID id;
    String doctorFIO;
    LocalDate date;
    LocalTime timeFrom;
    LocalTime timeTo;
}
