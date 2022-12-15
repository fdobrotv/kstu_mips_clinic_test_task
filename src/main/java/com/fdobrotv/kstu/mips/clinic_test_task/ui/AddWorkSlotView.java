package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.*;
import com.fdobrotv.kstu.mips.clinic_test_task.service.AdministrationService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Route
public class AddWorkSlotView extends VerticalLayout {

    private final AdministrationService administrationService;
    private Staff staff;

    public AddWorkSlotView(AdministrationService administrationService) {
        this.administrationService = administrationService;

        Text createWorkSlotTitle = new Text("Add work slot:");

        add(createWorkSlotTitle);
    }

    public void setStaff(Staff staff) {
        this.staff = staff;

        Locale ruLocale = new Locale("ru", "RU");
        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Date");
        datePicker.setLocale(ruLocale);
        datePicker.setRequiredIndicatorVisible(true);
        datePicker.setErrorMessage("This field is required");

        TimePicker timeFromPicker = new TimePicker();
        timeFromPicker.setLabel("Time from");
        timeFromPicker.setLocale(ruLocale);
        timeFromPicker.setRequiredIndicatorVisible(true);
        timeFromPicker.setErrorMessage("This field is required");

        TimePicker timeToPicker = new TimePicker();
        timeToPicker.setLabel("Time from");
        timeToPicker.setLocale(ruLocale);
        timeToPicker.setRequiredIndicatorVisible(true);
        timeToPicker.setErrorMessage("This field is required");

        Button createWorkSlotButton = new Button("Create work slot");

        createWorkSlotButton.addClickListener(clickEvent -> {
            WorkSlot workSlot = new WorkSlot();

            Objects.requireNonNull(staff.getId());
            workSlot.setStaffUserId(staff.getId());
            workSlot.setDate(datePicker.getValue());
            workSlot.setTimeFrom(timeFromPicker.getValue());
            workSlot.setTimeTo(timeToPicker.getValue());

            this.administrationService.addWorkSlot(workSlot);

            createWorkSlotButton.getUI().flatMap(ui -> ui.navigate(WorkSlotsView.class))
                    .ifPresent(workSlotsView -> workSlotsView.setStaff(staff));
        });

        add(datePicker, timeFromPicker, timeToPicker, createWorkSlotButton);
    }

}