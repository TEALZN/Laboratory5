package controller;

import domain.CircularDoublyLinkedList;
import domain.ListException;
import domain.Employee;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

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
    private CircularDoublyLinkedList JobPositionList;
    private Alert alert; //para el manejo de alertas
    @javafx.fxml.FXML
    private TableView jobPositionTableview;

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.JobPositionList = util.Utility.getJobPositionList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        hourlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("HourlyWage"));
        monthlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("MonthlyWage"));
        totalHoursTableColumn.setCellValueFactory(new PropertyValueFactory<>("TotalHours"));
        try{
            if(JobPositionList !=null && !JobPositionList.isEmpty()){
                for(int i = 1; i<= JobPositionList.size(); i++) {
                    jobPositionTableview.getItems().add((Employee) JobPositionList.getNode(i).data);
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
        this.JobPositionList.clear();
        util.Utility.setJobPositionList(this.JobPositionList); //actualizo la lista general
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

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "job-positionAdd.fxml", bp);
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

    private void updateTableView() throws ListException {
        this.jobPositionTableview.getItems().clear(); //clear table
        this.JobPositionList = util.Utility.getJobPositionList(); //cargo la lista
        if(JobPositionList !=null && !JobPositionList.isEmpty()){
            for(int i = 1; i<= JobPositionList.size(); i++) {
                this.jobPositionTableview.getItems().add((Employee) JobPositionList.getNode(i).data);
            }
        }
    }

}
