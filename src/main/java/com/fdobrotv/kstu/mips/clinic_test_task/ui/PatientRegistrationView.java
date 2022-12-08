package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;

@Route
public class PatientRegistrationView extends VerticalLayout {

    public PatientRegistrationView() {
        Text registrationTitle = new Text("Registration:");
        add(registrationTitle);

        EmailField emailField = new EmailField();
        emailField.setLabel("Email address");
        add(emailField);

        TextField phoneTextField = new TextField();
        phoneTextField.setLabel("Phone number");
        phoneTextField.setHelperText("Include country and area prefixes");
        phoneTextField.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);
        phoneTextField.setWidth("15em");
        phoneTextField.setRequiredIndicatorVisible(true);
        add(phoneTextField);

        TextField nameTextField = new TextField();
        nameTextField.setLabel("Name");
        nameTextField.setRequiredIndicatorVisible(true);
        nameTextField.setErrorMessage("This field is required");

        TextField lastNameTextField = new TextField();
        lastNameTextField.setLabel("Last name");
        lastNameTextField.setRequiredIndicatorVisible(true);
        lastNameTextField.setErrorMessage("This field is required");

        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Date of birth");

        Button registerButton = new Button("Register");

        registerButton.addClickListener(clickEvent -> {
            registerButton.getUI().ifPresent(ui ->
                    ui.navigate(MainView.class));
        });

        add(nameTextField, lastNameTextField, datePicker, registerButton);

    }

}