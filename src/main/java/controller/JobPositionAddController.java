package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.JobPosition;
import javafx.scene.control.Alert;

import java.awt.*;
import java.awt.event.ActionEvent;

public class JobPositionAddController {


    @javafx.fxml.FXML
    private Button add;
    @javafx.fxml.FXML
    private Button closeButton;
    @javafx.fxml.FXML
    private Button CleanButton;
    private CircularLinkedList JobPositionList;
    private JobPosition jobPosition;
    private Alert alert;

    @javafx.fxml.FXML
    public void initialize() {

    }


    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {

        this.JobPositionList.add(jobPosition);
        this.alert.setContentText("The Job-Position has been added");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();


    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {

        this.JobPositionList.clear();
        this.alert.setContentText("The Job-Position was deleted");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();


    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {



    }

}