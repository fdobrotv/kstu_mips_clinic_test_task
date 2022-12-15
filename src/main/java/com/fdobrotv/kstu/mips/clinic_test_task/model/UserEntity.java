package com.fdobrotv.kstu.mips.clinic_test_task.model;

import com.fdobrotv.kstu.mips.clinic_test_task.configuration.PostgreSQLEnumType;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Role;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Table(schema = "clinic", name = "user")
public class UserEntity {
    @Id
    @GeneratedValue
    UUID id;

    @Type( type = "pgsql_enum" )
    @Enumerated(EnumType.STRING)
    Role role;

    @Type( type = "pgsql_enum" )
    @Enumerated(EnumType.STRING)
    Speciality speciality;

    String email;
    String phone;
    String name;
    String lastName;
    LocalDate birthDate;
}
