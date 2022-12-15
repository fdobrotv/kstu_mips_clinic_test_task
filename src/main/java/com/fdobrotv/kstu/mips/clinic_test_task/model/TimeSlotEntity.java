package com.fdobrotv.kstu.mips.clinic_test_task.model;

import com.fdobrotv.kstu.mips.clinic_test_task.configuration.PostgreSQLEnumType;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Role;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(schema = "clinic", name = "time_slot")
public class TimeSlotEntity {
    @Id
    @GeneratedValue
    UUID id;

    UUID staffUserId;

    UUID userId;

    LocalDate date;

    LocalTime timeFrom;

    LocalTime timeTo;
}
