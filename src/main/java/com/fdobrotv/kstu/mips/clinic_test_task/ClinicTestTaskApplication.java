package com.fdobrotv.kstu.mips.clinic_test_task;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Role;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import com.fdobrotv.kstu.mips.clinic_test_task.model.TimeSlotEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.model.UserEntity;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.TimeSlotRepository;
import com.fdobrotv.kstu.mips.clinic_test_task.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Role.*;
import static com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality.THERAPIST;

@Log
@SpringBootApplication
public class ClinicTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicTestTaskApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(UserRepository repository, TimeSlotRepository timeSlotRepository) {
		return (args) -> {
			UserEntity adminUserEntity = UserEntity.builder()
					.role(ADMINISTRATOR)
					.name("Админ")
					.lastName("Великоадмиский")
					.email("admin@gmail.com")
					.birthDate(LocalDate.of(1968, 10, 16))
					.phone("+77774441234")
					.build();

			UserEntity doctorUserEntity = UserEntity.builder()
					.role(STAFF)
					.speciality(THERAPIST)
					.name("Владислав")
					.lastName("Великорукий")
					.email("doctor@gmail.com")
					.birthDate(LocalDate.of(1964, 5, 4))
					.phone("+77774441235")
					.build();

			UserEntity clientUserEntity = UserEntity.builder()
					.role(PATIENT)
					.name("Артём")
					.lastName("Фисташкович")
					.email("client@gmail.com")
					.birthDate(LocalDate.of(1989, 2, 17))
					.phone("+77774441236")
					.build();

			List<UserEntity> users = List.of(adminUserEntity, clientUserEntity);
			repository.saveAll(users);

			UserEntity doctorEntity = repository.save(doctorUserEntity);

			TimeSlotEntity timeSlotEntity = new TimeSlotEntity();
			timeSlotEntity.setStaffUserId(doctorEntity.getId());
			timeSlotEntity.setDate(LocalDate.of(2022, 12, 20));
			timeSlotEntity.setTimeFrom(LocalTime.of(14, 0));
			timeSlotEntity.setTimeTo(LocalTime.of(15, 0));

			timeSlotRepository.save(timeSlotEntity);
		};
	}

}
