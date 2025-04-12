package controller;

import domain.CircularLinkedList;
import domain.ListException;
import domain.Employee;
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
    private TableView employeeTableview;

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.EmployeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("First Name"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("Birthday"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        try{
            if(EmployeeList!=null && !EmployeeList.isEmpty()){
                for(int i=1; i<=EmployeeList.size(); i++) {
                    employeeTableview.getItems().add((Employee) EmployeeList.getNode(i).data);
                }
            }
            //this.EmployeeTableView.setItems(observableList);
        }catch(ListException ex){
            alert.setContentText("Employee list is empty");
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
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
    }

    public void setParentController(EmployeeController controller) {
        // No necesita implementación, solo para mantener la referencia
    }

    public void setParentReference(EmployeeController controller) {
        // No necesita implementación
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }

    @javafx.fxml.FXML
    public void addFirstOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addFirstEmployee.fxml", bp);
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
    public void removeFirstOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void getLastOnAction(ActionEvent actionEvent) {
    }

    public void updateTableView() throws ListException {
        this.employeeTableview.getItems().clear();
        this.EmployeeList = util.Utility.getEmployeeList();
        if(EmployeeList!=null && !EmployeeList.isEmpty()){
            for(int i=1; i<=EmployeeList.size(); i++) {
                this.employeeTableview.getItems().add(EmployeeList.getNode(i).data);
            }
        }
    }

}