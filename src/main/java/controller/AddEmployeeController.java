package controller;

import domain.CircularLinkedList;
import domain.Employee;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import util.FXUtility;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static util.Utility.getEmployeeList;

public class AddEmployeeController {

    @FXML private TextField idEmployeeTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField firstNameTextField;
    @FXML private TextField titleTextField;
    @FXML private DatePicker datePicker;
    @FXML private BorderPane bp;
    @FXML private javafx.scene.control.Button addButton;
    @FXML private javafx.scene.control.Button clearButton;
    @FXML private javafx.scene.control.Button closeButton;
    private CircularLinkedList employeeList;



    @javafx.fxml.FXML
    public void initialize() {
        try {
            // Inicializar componentes
            datePicker.setValue(LocalDate.now()); // Fecha actual por defecto
            idEmployeeTextField.setPromptText("Ingrese ID numérico");

            // Inicializar lista (aunque no se usa directamente aquí)
            this.employeeList = util.Utility.getEmployeeList();

        } catch (Exception e) {
            util.FXUtility.alert("Error", "Error al inicializar: " + e.getMessage())
                    .showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        try {
            // 1. Validar campos
            if (idEmployeeTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty() ||
                    firstNameTextField.getText().isEmpty()) {

                showAlert(Alert.AlertType.ERROR, "Error", "Complete todos los campos");
                return;
            }

            // 2. Crear empleado
            Employee newEmployee = new Employee(
                    Integer.valueOf(idEmployeeTextField.getText()),
                    lastNameTextField.getText(),
                    firstNameTextField.getText(),
                    titleTextField.getText(),
                    Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
            );

            // 3. Agregar a lista
            util.Utility.getEmployeeList().add(newEmployee);

            // 4. Mostrar confirmación
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Empleado agregado");

            // 5. Cerrar ventana ACTUAL
            Stage currentStage = (Stage) addButton.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Método auxiliar para alertas
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        idEmployeeTextField.clear();
        lastNameTextField.clear();
        firstNameTextField.clear();
        titleTextField.clear();
        datePicker.setValue(null);
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        FXUtility.loadPage("ucr.lab.HelloApplication", "employee.fxml", bp);
    }
}