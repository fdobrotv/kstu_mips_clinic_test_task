package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.LoginDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.exception.AccessDeniedException;
import com.fdobrotv.kstu.mips.clinic_test_task.service.LoginService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route
public class LoginView extends VerticalLayout {

    private final LoginService loginService;

    public LoginView(LoginService loginService) {
        this.loginService = loginService;

        Text registrationTitle = new Text("Logging in:");
        add(registrationTitle);

        EmailField emailField = new EmailField();
        emailField.setLabel("Email address");
        add(emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        add(passwordField);

        Button loginButton = new Button("Log in");

        loginButton.addClickListener(clickEvent -> {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(emailField.getValue());
            loginDTO.setPassword(passwordField.getValue());

            UserDTO login;
            try {
                login = loginService.login(loginDTO);
            } catch (AccessDeniedException e) {
                loginButton.getUI().ifPresent(ui -> ui.navigate(LoginForbiddenView.class));
                throw new RuntimeException(e);
            }

            final Class<? extends Component> navigationClass = switch (login.getRole()) {
                case ADMINISTRATOR -> AdministratorView.class;
                case STAFF -> DoctorWorkView.class;
                case PATIENT -> PatientSchedulerView.class;
                default -> MainView.class;
            };
            loginButton.getUI().ifPresent(ui -> ui.navigate(navigationClass).ifPresent(component -> {
                if (component instanceof PatientSchedulerView) {
                    PatientSchedulerView patientSchedulerView = (PatientSchedulerView) component;
                    patientSchedulerView.setEmail(emailField.getValue());
                } else if (component instanceof DoctorWorkView) {
                    DoctorWorkView doctorWorkView = (DoctorWorkView) component;
                    doctorWorkView.setEmail(emailField.getValue());
                }
            }));
        });

        add(emailField, passwordField, loginButton);
    }

}