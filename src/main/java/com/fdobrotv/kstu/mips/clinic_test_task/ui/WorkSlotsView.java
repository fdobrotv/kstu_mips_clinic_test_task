package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Staff;
import com.fdobrotv.kstu.mips.clinic_test_task.dto.WorkSlot;
import com.fdobrotv.kstu.mips.clinic_test_task.service.AdministrationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

@Route
public class WorkSlotsView extends VerticalLayout {

    private final AdministrationService administrationService;
    private Staff staff;

    public WorkSlotsView(AdministrationService administrationService) {
        this.administrationService = administrationService;

        Text registrationTitle = new Text("Work slots:");



        add(registrationTitle);
    }

    public void setStaff(Staff staff) {
        Objects.requireNonNull(staff);
        Objects.requireNonNull(staff.getId());
        this.staff = staff;

        Button addWorkSlotButton = new Button("Add new work slot");
        addWorkSlotButton.addClickListener(clickEvent -> {
            addWorkSlotButton.getUI().flatMap(ui -> ui.navigate(AddWorkSlotView.class))
                    .ifPresent(addWorkSlotView -> addWorkSlotView.setStaff(staff));
        });

        Grid<WorkSlot> grid = new Grid<>(WorkSlot.class, false);
        Grid.Column<WorkSlot> dateColumn = grid.addColumn(WorkSlot::getDate);
        Grid.Column<WorkSlot> timeFromColumn = grid.addColumn(WorkSlot::getTimeFrom);
        Grid.Column<WorkSlot> timeToColumn = grid.addColumn(WorkSlot::getTimeTo);

        UUID userId = staff.getId();
        List<WorkSlot> workSlots = this.administrationService.getTimeSlots(userId);
        GridListDataView<WorkSlot> dataView = grid.setItems(workSlots);
        TimeSlotFilter timeSlotFilter = new TimeSlotFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(dateColumn).setComponent(
                createFilterHeader("Date", timeSlotFilter::setDate));
        headerRow.getCell(timeFromColumn).setComponent(
                createFilterHeader("Time from", timeSlotFilter::setTimeFrom));
        headerRow.getCell(timeToColumn).setComponent(
                createFilterHeader("Time to", timeSlotFilter::setTimeTo));

        add(addWorkSlotButton, grid);
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
        private final GridListDataView<WorkSlot> dataView;

        private LocalDate date;
        private LocalTime timeFrom;
        private LocalTime timeTo;

        public TimeSlotFilter(GridListDataView<WorkSlot> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
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

        public boolean test(WorkSlot workSlot) {
            boolean matchesFullName = matches(workSlot.getDate(), date);
            boolean matchesEmail = matches(workSlot.getTimeFrom(), timeFrom);
            boolean matchesProfession = matches(workSlot.getTimeTo(), timeTo);

            return matchesFullName && matchesEmail && matchesProfession;
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