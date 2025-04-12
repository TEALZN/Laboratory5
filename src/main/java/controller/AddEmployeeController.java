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
            // Inicializar la lista de empleados (si es necesario)
            CircularLinkedList employeeList = util.Utility.getEmployeeList();

            // Inicializar componentes adicionales si es necesario
            datePicker.setValue(LocalDate.now()); // Establecer fecha actual por defecto

        } catch (Exception e) {
            util.FXUtility.alert("Error", "Error al inicializar: " + e.getMessage())
                    .showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void onKeyTypeAgeValidation(Event event) {
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        try {
            // Validar campos
            if (idEmployeeTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty() ||
                    firstNameTextField.getText().isEmpty() ||
                    datePicker.getValue() == null) {

                FXUtility.alert("Error", "Por favor complete todos los campos");
                return;
            }

            // Obtener valores
            Integer id = Integer.valueOf(idEmployeeTextField.getText());
            String lastName = lastNameTextField.getText();
            String firstName = firstNameTextField.getText();
            String title = titleTextField.getText();
            LocalDate localDate = datePicker.getValue();
            Date birthday = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Crear nuevo empleado
            Employee newEmployee = new Employee(id, lastName, firstName, title, birthday);

            // Agregar a la lista
            CircularLinkedList employeeList = util.Utility.getEmployeeList();
            if (employeeList != null) {
                employeeList.add(newEmployee);
                FXUtility.alert("Éxito", "Empleado agregado correctamente");

                // Cerrar la ventana
                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException e) {
            FXUtility.alert("Error", "El ID debe ser numérico");
        } catch (Exception e) {
            FXUtility.alert("Error", "Ocurrió un error: " + e.getMessage());
        }
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