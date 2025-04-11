package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.time.LocalDateTime;

public class StaffingController {
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> idTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, LocalDateTime> dateTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> employeeIdTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> employeeNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> jobPositionTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> supervisorNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> assignationTypeTableColumn;

    //defino la lista enlazada interna
    private CircularDoublyLinkedList JobPositionList;
    private CircularLinkedList EmployeeList;
    private Alert alert; //para el manejo de alertas
    @javafx.fxml.FXML
    private TableView staffAssignmentTableview;

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.EmployeeList = util.Utility.getEmployeeList();
        this.JobPositionList = util.Utility.getJobPosition();

        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        supervisorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("SupervisorName"));
        jobPositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("JobPosition"));
        assignationTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("AssignationType"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        try {
            if (EmployeeList != null && !EmployeeList.isEmpty()) {
                for (int i = 1; i <= EmployeeList.size(); i++) {
                    staffAssignmentTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
                }
            }
            if (JobPositionList != null && !JobPositionList.isEmpty()) {
                for (int i = 1; i <= JobPositionList.size(); i++) {
                    staffAssignmentTableview.getItems().add((JobPosition) JobPositionList.getNode(i).data);
                }
            }
            //this.EmployeeTableView.setItems(observableList);
        } catch (ListException ex) {
            alert.setContentText("Employee list is empty");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.EmployeeList.clear();
        this.JobPositionList.clear();
        util.Utility.setEmployeeList(this.EmployeeList);
        util.Utility.setJobPosition(this.JobPositionList); //actualizo la lista general
        this.alert.setContentText("The list is clear");
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
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }


    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void addSortedOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void getFirstOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void getLastOnAction(ActionEvent actionEvent) {
    }

    private void updateTableView() throws ListException {
        this.staffAssignmentTableview.getItems().clear(); //clear table
        this.EmployeeList = util.Utility.getEmployeeList(); //cargo la lista
        this.JobPositionList = util.Utility.getJobPosition();
        //Carga la lista de empleados
        if (EmployeeList != null && !EmployeeList.isEmpty()) {
            for (int i = 1; i <= EmployeeList.size(); i++) {
                this.staffAssignmentTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
            }
        }

        //Carga la lista de posiciones
        if (JobPositionList != null && !JobPositionList.isEmpty()) {
            for (int i = 1; i <= JobPositionList.size(); i++) {
                this.staffAssignmentTableview.getItems().add((JobPosition) JobPositionList.getNode(i).data);
            }
        }
    }

}