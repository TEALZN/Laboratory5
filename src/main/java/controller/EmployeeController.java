package controller;

import domain.CircularLinkedList;
import domain.ListException;
import domain.Employee;
import domain.Staffing;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.FXUtility;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;

public class EmployeeController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> idTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> lastNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> firstNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> titleTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> birthdayTableColumn;

    //defino la lista enlazada interna
    private CircularLinkedList EmployeeList;
    private Alert alert; //para el manejo de alertas
    @javafx.fxml.FXML
    private TableView<Employee> employeeTableview; // Con tipo genérico <Employee>

    @javafx.fxml.FXML
    public void initialize() {
        try {
            // Inicialización básica
            this.EmployeeList = util.Utility.getEmployeeList();
            alert = util.FXUtility.alert("Employee List", "Display Employee");
            alert.setAlertType(Alert.AlertType.ERROR);

            // Configurar columnas
            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            // Cargar datos
            employeeTableview.getItems().clear();
            if (EmployeeList != null && !EmployeeList.isEmpty()) {
                for (int i = 1; i <= EmployeeList.size(); i++) {
                    employeeTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
                }
            } else {
                alert.setContentText("Employee list is empty");
                alert.showAndWait();
            }
        } catch (ListException ex) {
            alert.setContentText("Error: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.EmployeeList.clear();
        util.Utility.setEmployeeList(this.EmployeeList); //actualizo la lista general
        this.alert.setContentText("The list was deleted");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();
        try {
            updateTableView(); //actualiza el contenido del tableview
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        try {
            // 1. Obtener el empleado seleccionado en la tabla
            Employee selectedEmployee = employeeTableview.getSelectionModel().getSelectedItem();

            // 2. Verificar si hay selección
            if (selectedEmployee == null) {
                util.FXUtility.alert("Advertencia", "Por favor seleccione un empleado de la tabla")
                        .showAndWait();
                return;
            }

            // 3. Verificar si la lista contiene al empleado
            boolean exists = EmployeeList.contains(selectedEmployee);

            // 4. Mostrar resultado
            String message = exists
                    ? "El empleado existe en la lista"
                    : "El empleado NO existe en la lista";

            Alert alert = util.FXUtility.alert("Resultado de Búsqueda", message);
            alert.setAlertType(exists ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
            alert.showAndWait();

        } catch (ListException e) {
            util.FXUtility.alert("Error", "Error al verificar la lista: " + e.getMessage())
                    .showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) throws ListException {
        alert.setContentText("Staffing list size: " + EmployeeList.size());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }

    @javafx.fxml.FXML
    public void sortIdOnAction(ActionEvent actionEvent) {
        FXCollections.sort(employeeTableview.getItems(), Comparator.comparingInt(Employee::getId));
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        Employee selectedEmployee = employeeTableview.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            try {
                EmployeeList.remove(selectedEmployee);
                updateTableView();
                alert.setContentText("Employee record removed.");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } catch (ListException e) {
                alert.setContentText("Error removing employee record: " + e.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Please select a employee record to remove.");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sortNameOnAction(ActionEvent actionEvent) {
        FXCollections.sort(employeeTableview.getItems(),
                Comparator.comparing(Employee::getLastName));
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        try {
            Employee current = employeeTableview.getSelectionModel().getSelectedItem();
            if (current == null) {
                FXUtility.alert("Advertencia", "Seleccione un empleado primero").showAndWait();
                return;
            }

            int index = employeeTableview.getSelectionModel().getSelectedIndex();
            if (index > 0) {
                employeeTableview.getSelectionModel().select(index - 1);
            }
        } catch (Exception e) {
            FXUtility.alert("Error", "No se pudo navegar: " + e.getMessage()).showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        try {
            if (EmployeeList.isEmpty()) {
                FXUtility.alert("Información", "La lista está vacía").showAndWait();
                return;
            }

            EmployeeList.removeLast();
            updateTableView();
            FXUtility.alert("Éxito", "Último empleado eliminado").showAndWait();

        } catch (ListException e) {
            FXUtility.alert("Error", "Error al eliminar: " + e.getMessage()).showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        try {
            Employee current = employeeTableview.getSelectionModel().getSelectedItem();
            if (current == null) {
                FXUtility.alert("Advertencia", "Seleccione un empleado primero").showAndWait();
                return;
            }

            int index = employeeTableview.getSelectionModel().getSelectedIndex();
            if (index < employeeTableview.getItems().size() - 1) {
                employeeTableview.getSelectionModel().select(index + 1);
            }
        } catch (Exception e) {
            FXUtility.alert("Error", "No se pudo navegar: " + e.getMessage()).showAndWait();
        }
    }

    public void updateTableView() throws ListException {
        this.employeeTableview.getItems().clear();
        this.EmployeeList = util.Utility.getEmployeeList();
        if(EmployeeList!=null && !EmployeeList.isEmpty()){
            for(int i=1; i<=EmployeeList.size(); i++) {
                this.employeeTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
            }
        }
    }

}