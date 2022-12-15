package com.fdobrotv.kstu.mips.clinic_test_task.ui;

import com.fdobrotv.kstu.mips.clinic_test_task.dto.Staff;
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
import lombok.extern.java.Log;

import java.util.List;
import java.util.function.Consumer;

@Log
@Route
public class AdministratorView extends VerticalLayout {

    final AdministrationService administrationService;

    public AdministratorView(AdministrationService administrationService) {
        this.administrationService = administrationService;

        Text registrationTitle = new Text("Administrator view:");

        Button createUserButton = new Button("Create user");
        createUserButton.addClickListener(clickEvent -> {
            createUserButton.getUI().ifPresent(ui ->
                    ui.navigate(CreateUserView.class));
        });

//        Crud staffCrud = new Crud<>(Person.class, createEditor());

        Grid<Staff> grid = new Grid<>(Staff.class, false);
        Grid.Column<Staff> nameColumn = grid.addColumn(Staff::getFullName);
        Grid.Column<Staff> emailColumn = grid.addColumn(Staff::getEmail);
        Grid.Column<Staff> professionColumn = grid
                .addColumn(Staff::getSpeciality);

        List<Staff> staff = this.administrationService.getStaff();
        GridListDataView<Staff> dataView = grid.setItems(staff);
        PersonFilter personFilter = new PersonFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nameColumn).setComponent(
                createFilterHeader("Name", personFilter::setFullName));
        headerRow.getCell(emailColumn).setComponent(
                createFilterHeader("Email", personFilter::setEmail));
        headerRow.getCell(professionColumn).setComponent(
                createFilterHeader("Profession", personFilter::setProfession));

        grid.addItemDoubleClickListener(event -> {
            Staff staff1 = event.getItem();
            log.info(staff1.toString());

            grid.getUI().flatMap(ui -> ui.navigate(WorkSlotsView.class))
                    .ifPresent(editor -> editor.setStaff(staff1));
        });

        add(registrationTitle, createUserButton, grid);
    }

//    private CrudEditor<Person> createEditor() {
//        TextField firstName = new TextField("First name");
//        TextField lastName = new TextField("Last name");
//        EmailField email = new EmailField("Email");
//        ComboBox<String> profession = new ComboBox<>("Profession");
//        List<String> professions =
//                Arrays.stream(MedicalSpeciality.values()).map(Objects::toString).collect(Collectors.toList());
//        profession.setItems(professions);
//
//        FormLayout form = new FormLayout(firstName, lastName, email,
//                profession);
//        form.setColspan(email, 2);
//        form.setColspan(profession, 2);
//        form.setMaxWidth("480px");
//        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
//                new FormLayout.ResponsiveStep("30em", 2));
//
//        Binder<Person> binder = new Binder<>(Person.class);
//        binder.forField(firstName).asRequired().bind(Person::getFirstName,
//                Person::setFirstName);
//        binder.forField(lastName).asRequired().bind(Person::getLastName,
//                Person::setLastName);
//        binder.forField(email).asRequired().bind(Person::getEmail,
//                Person::setEmail);
//        binder.forField(profession).asRequired().bind(Person::getProfession,
//                Person::setProfession);
//
//        return new BinderCrudEditor<>(binder, form);
//    }


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

    private static class PersonFilter {
        private final GridListDataView<Staff> dataView;

        private String fullName;
        private String email;
        private String profession;

        public PersonFilter(GridListDataView<Staff> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
            this.dataView.refreshAll();
        }

        public void setEmail(String email) {
            this.email = email;
            this.dataView.refreshAll();
        }

        public void setProfession(String profession) {
            this.profession = profession;
            this.dataView.refreshAll();
        }

        public boolean test(Staff staff) {
            boolean matchesFullName = matches(staff.getFullName(), fullName);
            boolean matchesEmail = matches(staff.getEmail(), email);
            boolean matchesProfession = matches(staff.getSpeciality().toString(),
                    profession);

            return matchesFullName && matchesEmail && matchesProfession;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || value
                    .toLowerCase().contains(searchTerm.toLowerCase());
        }
    }

}