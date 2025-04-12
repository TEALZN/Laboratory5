package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AddStaffingController implements Initializable {

    @FXML
    private TextField id; // fx:id="id" en addStaff.fxml
    @FXML
    private DatePicker date; // fx:id="date" en addStaff.fxml
    @FXML
    private ComboBox<Employee> employeeBox; // fx:id="employeeBox"
    @FXML
    private ComboBox<JobPosition> jobBox; // fx:id="jobBox"
    @FXML
    private ComboBox<String> supervisorBox; // fx:id="supervisorBox"
    @FXML
    private ComboBox<String> assigBox; // fx:id="assigBox"
    @FXML
    private Button add; // fx:id="add" con onAction="#addOnAction" (¡Ojo con el nombre!)
    @FXML
    private Button clean; // No tiene fx:id en el FXML que proporcionaste
    @FXML
    private Button close; // No tiene fx:id en el FXML que proporcionaste

    private CircularLinkedList employeeList;
    private CircularDoublyLinkedList jobPositionList;
    private Staffing result;
    private StaffingController staffingController;
    private BorderPane mainBorderPane; // Referencia al BorderPane principal

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        result = null;
        configureComboBoxes();
        populateComboBoxes();
        date.setValue(LocalDate.now(ZoneId.of("America/Costa_Rica")));
        supervisorBox.setItems(FXCollections.observableArrayList("Chaves Gabriela", "Castro Carlos", "Pérez Juan", "Ruiz Verónica", "Vargas María"));
        assigBox.setItems(FXCollections.observableArrayList("Promoción", "Transferencia", "Nuevo ingreso", "Temporal", "Otro"));

        // Establecer el ID inicial basado en el tamaño de la lista en StaffingController
        if (staffingController != null && staffingController.getStaffingList() != null) {
            try {
                id.setText(String.valueOf(staffingController.getStaffingList().size() + 1));
            } catch (ListException e) {
                id.setText("1"); // Si hay un error al obtener el tamaño, empezar en 1
            }
        } else {
            id.setText("1"); // Valor por defecto si staffingController o la lista son null
        }
    }

    private void configureComboBoxes() {
        employeeBox.setCellFactory(param -> new EmployeeListCell());
        employeeBox.setButtonCell(new EmployeeListCell());

        jobBox.setCellFactory(param -> new JobPositionListCell());
        jobBox.setButtonCell(new JobPositionListCell());
    }

    public void setEmployeeList(CircularLinkedList employeeList) {
        this.employeeList = employeeList;
        populateComboBox(employeeBox, employeeList, "Employee");
    }

    public void setJobPositionList(CircularDoublyLinkedList jobPositionList) {
        this.jobPositionList = jobPositionList;
        populateComboBox(jobBox, jobPositionList, "Job Position");
    }

    public void setStaffingController(StaffingController controller) {
        this.staffingController = controller;
        this.mainBorderPane = controller.getBorderPane(); // Obtener la referencia al BorderPane
    }

    private <T> void populateComboBox(ComboBox<T> comboBox, CircularLinkedList list, String type) {
        comboBox.getItems().clear();

        if (list == null || list.isEmpty()) {
            showAlert("Info", "No " + type + "s available", Alert.AlertType.INFORMATION);
            return;
        }

        try {
            for (int i = 1; i <= list.size(); i++) {
                Node node = list.getNode(i);
                if (node != null && node.data != null) {
                    comboBox.getItems().add((T) node.data);
                }
            }
        } catch (ListException e) {
            showAlert("Error", "Error loading " + type.toLowerCase() + "s: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private <T> void populateComboBox(ComboBox<T> comboBox, CircularDoublyLinkedList list, String type) {
        comboBox.getItems().clear();

        if (list == null || list.isEmpty()) {
            showAlert("Info", "No " + type + "s available", Alert.AlertType.INFORMATION);
            return;
        }

        try {
            for (int i = 1; i <= list.size(); i++) {
                Node node = list.getNode(i);
                if (node != null && node.data != null) {
                    comboBox.getItems().add((T) node.data);
                }
            }
        } catch (ListException e) {
            showAlert("Error", "Error loading " + type.toLowerCase() + "s: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    private void populateComboBoxes() {
        populateComboBox(employeeBox, employeeList, "Employee");
        populateComboBox(jobBox, jobPositionList, "Job Position");
    }

    @FXML
    private void handleAdd() { // Este método debería guardar los datos
        if (!validateInput()) {
            return;
        }

        try {
            int staffingId = Integer.parseInt(id.getText());
            Employee selectedEmployee = employeeBox.getValue();
            JobPosition selectedJobPosition = jobBox.getValue();
            String supervisorName = supervisorBox.getValue();
            String assignmentType = assigBox.getValue();
            LocalDate selectedDate = date.getValue();

            result = new Staffing(
                    staffingId,
                    selectedDate.atStartOfDay(),
                    selectedEmployee.getId(),
                    selectedEmployee.getFirstName() + " " + selectedEmployee.getLastName(),
                    selectedJobPosition.getDescription(),
                    supervisorName,
                    assignmentType
            );

            if (staffingController != null) {
                staffingController.addStaffing(result);
            }

            returnToStaffingView();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid ID format. Please enter a number.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Failed to create staffing record: " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cleanOnAction(ActionEvent actionEvent) {
        id.clear();
        employeeBox.setValue(null);
        jobBox.setValue(null);
        supervisorBox.setValue(null);
        assigBox.setValue(null);
        date.setValue(LocalDate.now(ZoneId.of("America/Costa_Rica")));
    }

    @FXML
    private void closeOnAction(ActionEvent actionEvent) {
        returnToStaffingView();
    }

    private void returnToStaffingView() {
        if (mainBorderPane != null && staffingController != null) {
            util.FXUtility.loadPage(staffingController.getClass().getName(), "staffing.fxml", mainBorderPane);
            staffingController.loadStaffingData(); // Recargar los datos en la tabla
        }
        // No cerramos la ventana, solo volvemos a la vista principal
    }

    private boolean validateInput() {
        if (id.getText().trim().isEmpty()) {
            showAlert("Validation Error", "ID is required", Alert.AlertType.ERROR);
            id.requestFocus();
            return false;
        }
        try {
            int staffingId = Integer.parseInt(id.getText());
            if (staffingId <= 0) {
                showAlert("Validation Error", "ID must be positive", Alert.AlertType.ERROR);
                id.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "ID must be a valid number", Alert.AlertType.ERROR);
            id.requestFocus();
            return false;
        }
        if (employeeBox.getValue() == null) {
            showAlert("Validation Error", "Please select an employee", Alert.AlertType.ERROR);
            employeeBox.requestFocus();
            return false;
        }
        if (jobBox.getValue() == null) {
            showAlert("Validation Error", "Please select a job position", Alert.AlertType.ERROR);
            jobBox.requestFocus();
            return false;
        }
        if (supervisorBox.getValue() == null || supervisorBox.getValue().isEmpty()) {
            showAlert("Validation Error", "Please select a supervisor", Alert.AlertType.ERROR);
            supervisorBox.requestFocus();
            return false;
        }
        if (assigBox.getValue() == null || assigBox.getValue().isEmpty()) {
            showAlert("Validation Error", "Please select an assignment type", Alert.AlertType.ERROR);
            assigBox.requestFocus();
            return false;
        }
        if (date.getValue() == null) {
            showAlert("Validation Error", "Please select a register date", Alert.AlertType.ERROR);
            date.requestFocus();
            return false;
        }
        return true;
    }

    public Staffing getResult() {
        return result;
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static class EmployeeListCell extends javafx.scene.control.ListCell<Employee> {
        @Override
        protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? "" : item.getFirstName() + " " + item.getLastName() + " (" + item.getId() + ")");
        }
    }

    private static class JobPositionListCell extends javafx.scene.control.ListCell<JobPosition> {
        @Override
        protected void updateItem(JobPosition item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty || item == null ? "" : item.getDescription() + " (" + item.getId() + ")");
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) { // Este método ahora vuelve a la vista principal
        returnToStaffingView();
    }
}