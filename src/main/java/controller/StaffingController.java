package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.ResourceBundle;

public class StaffingController implements Initializable {
    @FXML
    private BorderPane bp;
    @FXML
    private TableColumn<Staffing, Integer> idTableColumn;
    @FXML
    private TableColumn<Staffing, LocalDateTime> dateTableColumn;
    @FXML
    private TableColumn<Staffing, Integer> employeeIdTableColumn;
    @FXML
    private TableColumn<Staffing, String> employeeNameTableColumn;
    @FXML
    private TableColumn<Staffing, String> jobPositionTableColumn;
    @FXML
    private TableColumn<Staffing, String> supervisorNameTableColumn;
    @FXML
    private TableColumn<Staffing, String> assignationTypeTableColumn;

    private CircularDoublyLinkedList jobPositionList;
    private CircularLinkedList employeeList;
    private CircularLinkedList staffingList = new CircularLinkedList(); // Lista para almacenar Staffing
    private Alert alert;
    @FXML
    private TableView<Staffing> staffAssignmentTableview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.employeeList = Staffing.Utility.getEmployeeList();
        this.jobPositionList = Staffing.Utility.getJobPositionList(); // Corrected getter

        alert = util.FXUtility.alert("Staffing List", "Display Staffing");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        jobPositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("jobPosition"));
        supervisorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("supervisor"));
        assignationTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentType"));

        // Agregar datos quemados para probar la TableView
        staffingList.addLast(new Staffing(1, LocalDateTime.of(2025, Month.APRIL, 10, 8, 0), 101, "Ana Pérez", "Developer", "Carlos López", "Nuevo Ingreso"));
        staffingList.addLast(new Staffing(2, LocalDateTime.of(2025, Month.APRIL, 9, 10, 30), 102, "Juan Rodríguez", "Analyst", "Laura Gómez", "Transferencia"));
        staffingList.addLast(new Staffing(3, LocalDateTime.of(2025, Month.APRIL, 11, 14, 15), 103, "María Vargas", "Manager", "Pedro Ruiz", "Promoción"));

        loadStaffingData();
    }

    public void addStaffing(Staffing staffing) {
        staffingList.addLast(staffing);
        updateTableView();
    }

    public TableView<Staffing> getStaffAssignmentTableview() {
        return staffAssignmentTableview;
    }

    public BorderPane getBorderPane() {
        return bp;
    }

    public CircularLinkedList getStaffingList() {
        return staffingList;
    }

    public void loadStaffingData() {
        staffAssignmentTableview.getItems().clear();
        try {
            for (int i = 1; i <= staffingList.size(); i++) {
                staffAssignmentTableview.getItems().add((Staffing) staffingList.getNode(i).data);
            }
        } catch (ListException e) {
            alert.setContentText("Error loading staffing data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void clearOnAction(ActionEvent actionEvent) {
        staffingList.clear();
        updateTableView();
        this.alert.setContentText("The staffing list is clear");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();
    }

    @FXML
    public void sizeOnAction(ActionEvent actionEvent) throws ListException {
        alert.setContentText("Staffing list size: " + staffingList.size());
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.showAndWait();
    }

    @FXML
    public void removeOnAction(ActionEvent actionEvent) {
        Staffing selectedStaffing = staffAssignmentTableview.getSelectionModel().getSelectedItem();
        if (selectedStaffing != null) {
            try {
                staffingList.remove(selectedStaffing);
                updateTableView();
                alert.setContentText("Staffing record removed.");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } catch (ListException e) {
                alert.setContentText("Error removing staffing record: " + e.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Please select a staffing record to remove.");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.showAndWait();
        }
    }

    private void updateTableView() {
        staffAssignmentTableview.getItems().clear();
        try {
            for (int i = 1; i <= staffingList.size(); i++) {
                staffAssignmentTableview.getItems().add((Staffing) staffingList.getNode(i).data);
            }
        } catch (ListException e) {
            alert.setContentText("Error updating table view: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void addAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addStaff.fxml", bp);
    }
    @FXML
    public void sortAssigOnAction(ActionEvent actionEvent) {
        FXCollections.sort(staffAssignmentTableview.getItems(), Comparator.comparing(Staffing::getAssignmentType));
    }

    @FXML
    public void sortJobOnAction(ActionEvent actionEvent) {
        FXCollections.sort(staffAssignmentTableview.getItems(), Comparator.comparing(Staffing::getJobPosition));
    }

    @FXML
    public void sortNameOnAction(ActionEvent actionEvent) {
        FXCollections.sort(staffAssignmentTableview.getItems(), Comparator.comparing(Staffing::getEmployeeName));
    }

    @FXML
    public void sortIdOnAction(ActionEvent actionEvent) {
        FXCollections.sort(staffAssignmentTableview.getItems(), Comparator.comparingInt(Staffing::getEmployeeId));
    }


}