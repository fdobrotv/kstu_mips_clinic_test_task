package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

@Route
public class PatientSchedulerView extends VerticalLayout {

    public PatientSchedulerView() {
        Select<String> select = new Select<>();
        select.setLabel("Speciality");
        select.setItems("DENTIST", "THERAPIST");
        select.setPlaceholder("Select doctor speciali");
        select.setEmptySelectionAllowed(false);

        select.addValueChangeListener(valueChangeEvent -> {
            System.out.println("Value changeEvent: "+ valueChangeEvent);
        });

        add(select);
    }

}