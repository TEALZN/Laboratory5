package controller;

import domain.CircularLinkedList;
import domain.JobPosition;
import domain.ListException;
import domain.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Optional;
import java.util.Random;

import static util.Utility.random;

public class JobPositionController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> idTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> descriptionTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, Double> hourlyWageTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> totalHoursTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, Double> monthlyWageTableColumn;

    //defino la lista enlazada interna
    private CircularLinkedList EmployeeList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    private TableView jobPositionTableview;

    @javafx.fxml.FXML
    public void initialize() {

        //cargamos la lista general
        this.EmployeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        hourlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("HourlyWage"));
        monthlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("MonthlyWage"));
        totalHoursTableColumn.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));

        try{

            if (EmployeeList != null && !EmployeeList.isEmpty()) {
                Random random = new Random();
                for (int i = 1; i <= EmployeeList.size(); i++) {
                    JobPosition jobPosition = (JobPosition) EmployeeList.getNode(i).data;
                    double hoursWorked = 40 + (50 - 40) * random.nextDouble();
                    jobPosition.setTotalHours(hoursWorked);
                    jobPositionTableview.getItems().add(jobPosition);
                }
            }


        }catch(ListException e){
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

    @FXML
    void addSortedOnAction(ActionEvent event) {


    }

    @FXML
    void addFirstOnAction(ActionEvent event) {


    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Contains Element");
        dialog.setHeaderText("Enter the element to check:");
        dialog.setContentText("Element:");

        dialog.showAndWait().ifPresent(elementToCheck -> {
            try {
                boolean contains = EmployeeList.contains(elementToCheck);
                alert.setContentText("Does the list contain '" + elementToCheck + "'? " + (contains ? "Yes" : "No"));
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } catch (ListException e) {
                alert.setContentText("Error: " + e.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        });
    }



    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            int size = EmployeeList.size();
            alert.setContentText("The size of the list is: " + size);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);


        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {

        util.FXUtility.loadPage("ucr.lab.HelloApplication", "JobPositionAddController", bp);
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remove Element");
        dialog.setHeaderText("Enter the element to remove:");
        dialog.setContentText("Element:");

        dialog.showAndWait().ifPresent(elementToRemove -> {
            try {
                EmployeeList.remove(elementToRemove);
                alert.setContentText("Element '" + elementToRemove + "' has been removed (if it existed).");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.showAndWait();
            } catch (ListException e) {
                alert.setContentText("Error: " + e.getMessage());
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        });

    }


    @javafx.fxml.FXML
    public void getFirstOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            Object firstElement = EmployeeList.getFirst();
            alert.setContentText("The first element is: " + firstElement);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

    }

    @javafx.fxml.FXML
    public void removeFirstOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            Object removedElement = EmployeeList.removeFirst();
            alert.setContentText("The first element '" + removedElement + "' has been removed.");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

    }

    @javafx.fxml.FXML
    public void getLastOnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            Object lastElement = EmployeeList.getLast();
            alert.setContentText("The last element is: " + lastElement);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

    }

    private void updateTableView() throws ListException {

        this.jobPositionTableview.getItems().clear();
        this.EmployeeList = util.Utility.getEmployeeList();
        if (EmployeeList != null && !EmployeeList.isEmpty()) {
            Random random = new Random();
            for (int i = 1; i <= EmployeeList.size(); i++) {
                JobPosition jobPosition = (JobPosition) EmployeeList.getNode(i).data;
                double hoursWorked = 40 + (50 - 40) * random.nextDouble();
                jobPosition.setTotalHours(hoursWorked);
                this.jobPositionTableview.getItems().add(jobPosition);
            }
        }
    }

    }

