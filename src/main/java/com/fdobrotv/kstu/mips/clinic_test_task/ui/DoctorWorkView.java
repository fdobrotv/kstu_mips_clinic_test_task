package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.PatientDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.Speciality;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.service.DoctorService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Objects;

@Route
public class DoctorWorkView extends VerticalLayout {

    private final DoctorService doctorService;

    public DoctorWorkView(DoctorService doctorService) {
        this.doctorService = doctorService;

        Text workViewTitle = new Text("Work view:");

        add(workViewTitle);
    }

    public void setEmail(String email) {
        UserDTO doctor = doctorService.getUserByEmail(email);

        Objects.requireNonNull(doctor.getId());
        List<PatientDTO> patients = doctorService.getPatientList(doctor.getId());

        Select<PatientDTO> select = new Select<>();
        select.setLabel("Patient");
        select.setItems(patients);
        select.setItemLabelGenerator((PatientDTO patientDTO) -> patientDTO.getName() + " " + doctor.getLastName());
        select.setPlaceholder("Select patient");
        select.setEmptySelectionAllowed(false);

        add(select);

        TextArea textArea = new TextArea();
        textArea.setLabel("Prescription");
        int charLimit = 500;
        textArea.setMaxLength(charLimit);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        textArea.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });
        textArea.setValue("Paracetamol 10mg, Validol 30mg and other!");

        Button savePrescriptionButton = new Button("Save prescription");
        savePrescriptionButton.addClickListener(clickEvent -> {
            Objects.requireNonNull(doctor.getId());
            Objects.requireNonNull(select.getValue().getId());
            this.doctorService.savePrescription(textArea.getValue(), doctor.getId(), select.getValue().getId());
            savePrescriptionButton.getUI().ifPresent(ui ->
                    ui.navigate(MainView.class));
        });

        select.addValueChangeListener(event -> {
            add(textArea, savePrescriptionButton);
        });
    }
}