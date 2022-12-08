package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientRegistrationService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;

import java.util.Locale;

@Route
public class PatientRegistrationView extends VerticalLayout {

    final
    PatientRegistrationService patientRegistrationService;

    public PatientRegistrationView(PatientRegistrationService patientRegistrationService) {
        this.patientRegistrationService = patientRegistrationService;

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

        Locale finnishLocale = new Locale("ru", "RU");
        DatePicker datePicker = new DatePicker();
        datePicker.setLabel("Date of birth");
        datePicker.setLocale(finnishLocale);
        datePicker.setRequiredIndicatorVisible(true);
        datePicker.setErrorMessage("This field is required");

        Button registerButton = new Button("Register");

        registerButton.addClickListener(clickEvent -> {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setName(nameTextField.getValue());
            patientDTO.setPhone(phoneTextField.getValue());
            patientDTO.setEmail(emailField.getValue());
            patientDTO.setLastName(lastNameTextField.getValue());
            patientDTO.setBirthDate(datePicker.getValue());

            patientRegistrationService.addPatient(patientDTO);

            registerButton.getUI().ifPresent(ui ->
                    ui.navigate(MainView.class));
        });

        add(nameTextField, lastNameTextField, datePicker, registerButton);
    }

}