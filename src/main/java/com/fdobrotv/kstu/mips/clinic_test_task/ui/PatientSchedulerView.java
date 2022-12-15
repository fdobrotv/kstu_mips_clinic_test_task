package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Objects;

@Route
public class PatientSchedulerView extends VerticalLayout {

    private final PatientService patientService;
    public PatientSchedulerView(PatientService patientService) {
        this.patientService = patientService;
    }

    public void setEmail(String email) {
        Objects.requireNonNull(email);

        Select<Speciality> select = new Select<>();
        select.setLabel("Speciality");
        select.setItems(Speciality.values());
        select.setPlaceholder("Select doctor speciality");
        select.setEmptySelectionAllowed(false);

        DatePicker datePicker = new DatePicker("Meeting date");
        datePicker.setHelperText("Mondays – Fridays only");

        Binder<Appointment> binder = new Binder<>(Appointment.class);
        binder.forField(datePicker).withValidator(localDate -> {
            int dayOfWeek = localDate.getDayOfWeek().getValue();
            boolean validWeekDay = dayOfWeek >= 1 && dayOfWeek <= 5;
            return validWeekDay;
        }, "Select a weekday").bind(Appointment::getStartDate,
                Appointment::setStartDate);

        Button scheduleButton = new Button("Schedule a visit");

        select.addValueChangeListener(valueChangeEvent -> {
            System.out.println("Value changeEvent: " + valueChangeEvent);

            Speciality value = valueChangeEvent.getValue();
            Objects.requireNonNull(value);
            List<Staff> staffList =
                    this.patientService.getStaffBySpeciality(value);

            Select<Staff> selectDoctor = new Select<>();
            selectDoctor.setItemLabelGenerator((Staff staff) -> staff.getLastName() + " " + staff.getFullName());
            selectDoctor.setLabel("Doctor");
            selectDoctor.setItems(staffList);
            selectDoctor.setPlaceholder("Select doctor");
            selectDoctor.setEmptySelectionAllowed(false);

            addComponentAtIndex(1, selectDoctor);

            selectDoctor.addValueChangeListener(doctorEventChange -> {

                List<WorkSlot> timeSlotStream =
                        this.patientService.getTimeSlotsByStaffId(doctorEventChange.getValue().getId());

                Select<WorkSlot> timeSlotSelect = new Select<>();
                timeSlotSelect.setItemLabelGenerator(workSlot ->
                        workSlot.getDate() + " " + workSlot.getTimeFrom() + " " + workSlot.getTimeTo());
                timeSlotSelect.setLabel("Time slot");
                timeSlotSelect.setItems(timeSlotStream);
                timeSlotSelect.setPlaceholder("Select time slot");
                timeSlotSelect.setEmptySelectionAllowed(false);

                addComponentAtIndex(2, timeSlotSelect);

                timeSlotSelect.addValueChangeListener(event -> {
                    add(scheduleButton);

                    scheduleButton.addClickListener(clickEvent -> {
                        UserDTO userDTO = null;
                        try {
                            userDTO = this.patientService.getUserByEmail(email);
                        } catch (AccessDeniedException e) {
                            throw new RuntimeException(e);
                        }
                        this.patientService.bookTimeSlot(event.getValue().getId(), userDTO.getId());
                        UserDTO finalUserDTO = userDTO;
                        scheduleButton.getUI().flatMap(ui -> ui.navigate(PatientCabinetView.class))
                                .ifPresent(patientCabinetView -> {
                            patientCabinetView.setUser(finalUserDTO);
                        });
                    });

                });
            });
        });

        add(select);
    }
}