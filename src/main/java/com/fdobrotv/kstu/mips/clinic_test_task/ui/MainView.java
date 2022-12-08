package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView() {
        Text welcomeText = new Text("Welcome to our Clinic!");

        Button registerButton = new Button("Register as a client");
        registerButton.addClickListener(clickEvent -> {
            registerButton.getUI().ifPresent(ui ->
                    ui.navigate(PatientRegistrationView.class));
        });

        Button scheduleButton = new Button("Schedule a visit");
        scheduleButton.addClickListener(clickEvent -> {
            registerButton.getUI().ifPresent(ui ->
                    ui.navigate(PatientSchedulerView.class));
        });

        add(welcomeText, registerButton, scheduleButton);
    }

}