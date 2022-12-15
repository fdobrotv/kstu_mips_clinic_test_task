package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Role;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.service.AdministrationService;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientRegistrationService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Route
public class CreateUserView extends VerticalLayout {

    final AdministrationService administrationService;

    public CreateUserView(AdministrationService administrationService) {
        this.administrationService = administrationService;

        Text createUserTitle = new Text("Create user:");

        Select<String> selectRole = new Select<>();
        selectRole.setLabel("Role");
        List<String> roles =
                Arrays.stream(Role.values()).map(Enum::toString).collect(Collectors.toList());
        selectRole.setItems(roles);
        selectRole.setPlaceholder("Select role");
        selectRole.setEmptySelectionAllowed(false);

        Select<String> selectSpeciality = new Select<>();
        selectSpeciality.setLabel("Speciality");
        List<String> specialities =
                Arrays.stream(Speciality.values()).map(Enum::toString).collect(Collectors.toList());
        selectSpeciality.setItems(specialities);
        selectSpeciality.setPlaceholder("Select doctor speciality");
        selectSpeciality.setEmptySelectionAllowed(false);

        selectRole.addValueChangeListener(event -> {
            String value = event.getValue();
            if (Role.valueOf(value).equals(Role.STAFF)) {
                addComponentAtIndex(2, selectSpeciality);
            } else {
                remove(selectSpeciality);
            }
        });

        EmailField emailField = new EmailField();
        emailField.setLabel("Email address");

        TextField phoneTextField = new TextField();
        phoneTextField.setLabel("Phone number");
        phoneTextField.setHelperText("Include country and area prefixes");
        phoneTextField.addThemeVariants(TextFieldVariant.LUMO_HELPER_ABOVE_FIELD);
        phoneTextField.setWidth("15em");
        phoneTextField.setRequiredIndicatorVisible(true);

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

        Button createUserButton = new Button("Create user");

        createUserButton.addClickListener(clickEvent -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(Role.valueOf(selectRole.getValue()));
            userDTO.setSpeciality(Speciality.valueOf(selectSpeciality.getValue()));
            userDTO.setName(nameTextField.getValue());
            userDTO.setPhone(phoneTextField.getValue());
            userDTO.setEmail(emailField.getValue());
            userDTO.setLastName(lastNameTextField.getValue());
            userDTO.setBirthDate(datePicker.getValue());

            this.administrationService.addUser(userDTO);

            createUserButton.getUI().ifPresent(ui ->
                    ui.navigate(AdministratorView.class));
        });

        add(createUserTitle, selectRole, emailField, phoneTextField, nameTextField,
                lastNameTextField, datePicker, createUserButton);
    }

}