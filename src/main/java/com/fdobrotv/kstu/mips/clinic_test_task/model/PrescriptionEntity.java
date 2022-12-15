package com.fdobrotv.kstu.mips.clinic_test_task.model;

import com.fdobrotv.kstu.mips.clinic_test_task.configuration.PostgreSQLEnumType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
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
@Table(schema = "clinic", name = "prescription")
public class PrescriptionEntity {
    @Id
    @GeneratedValue
    UUID id;

    UUID staffUserId;

    UUID userId;

    Instant createdAt;

    String text;
}
