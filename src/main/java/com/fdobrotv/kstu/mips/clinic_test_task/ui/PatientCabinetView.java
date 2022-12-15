package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.UserDTO;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlot;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlotView;
import com.fdobrotv.kstu.mips.clinic_test_task.service.PatientService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Route
public class PatientCabinetView extends VerticalLayout {

    private final PatientService patientService;

    public PatientCabinetView(PatientService patientService) {
        this.patientService = patientService;

        Text welcomeText = new Text("Your cabinet, look at your appointments:");
        add(welcomeText);
    }

    public void setUser(UserDTO userDTO) {
        Grid<WorkSlotView> grid = new Grid<>(WorkSlotView.class, false);
        Grid.Column<WorkSlotView> doctorFio = grid.addColumn(WorkSlotView::getDoctorFIO);
        Grid.Column<WorkSlotView> dateColumn = grid.addColumn(WorkSlotView::getDate);
        Grid.Column<WorkSlotView> timeFromColumn = grid.addColumn(WorkSlotView::getTimeFrom);
        Grid.Column<WorkSlotView> timeToColumn = grid.addColumn(WorkSlotView::getTimeTo);

        UUID userId = userDTO.getId();
        List<WorkSlotView> workSlots = this.patientService.getTimeSlots(userId);
        GridListDataView<WorkSlotView> dataView = grid.setItems(workSlots);
        TimeSlotFilter timeSlotFilter = new TimeSlotFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(doctorFio).setComponent(
                createFilterHeader("Date", timeSlotFilter::setDoctorFIO));
        headerRow.getCell(dateColumn).setComponent(
                createFilterHeader("Date", timeSlotFilter::setDate));
        headerRow.getCell(timeFromColumn).setComponent(
                createFilterHeader("Time from", timeSlotFilter::setTimeFrom));
        headerRow.getCell(timeToColumn).setComponent(
                createFilterHeader("Time to", timeSlotFilter::setTimeTo));

        add(grid);
    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    private static class TimeSlotFilter {
        private final GridListDataView<WorkSlotView> dataView;

        private String doctorFIO;
        private LocalDate date;
        private LocalTime timeFrom;
        private LocalTime timeTo;

        public TimeSlotFilter(GridListDataView<WorkSlotView> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setDoctorFIO(String doctorFIO) {
            this.doctorFIO = doctorFIO;
            this.dataView.refreshAll();
        }

        public void setDate(String date) {
            LocalDate parsed = LocalDate.parse(date);
            this.date = parsed;
            this.dataView.refreshAll();
        }

        public void setTimeFrom(String timeFrom) {
            LocalTime parsed = LocalTime.parse(timeFrom);
            this.timeFrom = parsed;
            this.dataView.refreshAll();
        }

        public void setTimeTo(String timeTo) {
            LocalTime parsed = LocalTime.parse(timeTo);
            this.timeTo = parsed;
            this.dataView.refreshAll();
        }

        public boolean test(WorkSlotView workSlot) {
            boolean matchesDoctorFio = matches(workSlot.getDoctorFIO(), doctorFIO);
            boolean matchesDate = matches(workSlot.getDate(), date);
            boolean matchesEmail = matches(workSlot.getTimeFrom(), timeFrom);
            boolean matchesProfession = matches(workSlot.getTimeTo(), timeTo);

            return matchesDoctorFio && matchesDate && matchesEmail && matchesProfession;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || value
                    .toLowerCase().contains(searchTerm.toLowerCase());
        }
        private boolean matches(LocalDate value, LocalDate searchTerm) {
            return searchTerm == null || value
                    .equals(searchTerm);
        }

        private boolean matches(LocalTime value, LocalTime searchTerm) {
            return searchTerm == null || value
                    .equals(searchTerm);
        }


    }
}