package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class LoginForbiddenView extends VerticalLayout {

    public LoginForbiddenView() {
        Text welcomeText = new Text("Login forbidden!");
        add(welcomeText);
    }

}