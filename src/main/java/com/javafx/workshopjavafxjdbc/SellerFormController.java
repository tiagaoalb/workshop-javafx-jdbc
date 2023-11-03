package com.javafx.workshopjavafxjdbc;

import com.javafx.workshopjavafxjdbc.db.DbException;
import com.javafx.workshopjavafxjdbc.listeners.DataChangeListener;
import com.javafx.workshopjavafxjdbc.model.entities.Department;
import com.javafx.workshopjavafxjdbc.model.entities.Seller;
import com.javafx.workshopjavafxjdbc.model.exceptions.ValidationException;
import com.javafx.workshopjavafxjdbc.model.services.DepartmentService;
import com.javafx.workshopjavafxjdbc.model.services.SellerService;
import com.javafx.workshopjavafxjdbc.util.Alerts;
import com.javafx.workshopjavafxjdbc.util.Constraints;
import com.javafx.workshopjavafxjdbc.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import lombok.Setter;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private static final String NAME_FIELD = "name";

    private static final String EMAIL_FIELD = "email";

    private static final String BASE_SALARY_FIELD = "baseSalary";

    private static final String BIRTH_DATE_FIELD = "birthDate";

    @Setter
    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setServices(SellerService service, DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity is null");
        }
        if (service == null) {
            throw new IllegalStateException("Service is null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        String fieldErrorMsg = "Field can't be empty";

        if (txtName.getText() == null || txtName.getText().trim().isEmpty()) {
            exception.addError(NAME_FIELD, fieldErrorMsg);
        }
        obj.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().isEmpty()) {
            exception.addError(EMAIL_FIELD, fieldErrorMsg);
        }
        obj.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null) {
            exception.addError(BIRTH_DATE_FIELD, fieldErrorMsg);
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().isEmpty()) {
            exception.addError(BASE_SALARY_FIELD, fieldErrorMsg);
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble((txtBaseSalary));
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity is null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        if (entity.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects() {
        if (departmentService == null) {
            throw new IllegalStateException("DepartmentService is null");
        }
        List<Department> list = departmentService.findAll();
        ObservableList<Department> observableList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(observableList);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorName.setText(fields.contains(NAME_FIELD) ? errors.get(NAME_FIELD) : "");
        labelErrorEmail.setText(fields.contains(EMAIL_FIELD) ? errors.get(EMAIL_FIELD) : "");
        labelErrorBaseSalary.setText(fields.contains(BASE_SALARY_FIELD) ? errors.get(BASE_SALARY_FIELD) : "");
        labelErrorBirthDate.setText(fields.contains(BIRTH_DATE_FIELD) ? errors.get(BIRTH_DATE_FIELD) : "");
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }
}
