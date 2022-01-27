package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static utilities.sqlAppointments.*;

public class ReportTwoController implements Initializable {


    Parent scene;

    @FXML
    public TableView<Appointments> anikaTable;

    @FXML
    public TableColumn<Appointments, Integer> apptColOne;

    @FXML
    public TableColumn<Appointments, String> titleColOne;

    @FXML
    public TableColumn<Appointments, String> typeColOne;

    @FXML
    public TableColumn<Appointments, String> descriptionColOne;

    @FXML
    public TableColumn<Appointments, LocalDateTime> startColOne;

    @FXML
    public TableColumn<Appointments, LocalDateTime> endColOne;

    @FXML
    public TableColumn<Appointments, Integer> customerColOne;

    @FXML
    public TableView<Appointments> danielTable;

    @FXML
    public TableColumn<Appointments, Integer> apptColTwo;

    @FXML
    public TableColumn<Appointments, String> titleColTwo;

    @FXML
    public TableColumn<Appointments, String> typeColTwo;

    @FXML
    public TableColumn<Appointments, String> descriptionColTwo;

    @FXML
    public TableColumn<Appointments, LocalDateTime> startColTwo;

    @FXML
    public TableColumn<Appointments, LocalDateTime> endColTwo;

    @FXML
    public TableColumn<Appointments, Integer> customerColTwo;

    @FXML
    public TableView<Appointments> liTable;

    @FXML
    public TableColumn<Appointments, Integer> apptColThree;

    @FXML
    public TableColumn<Appointments, String> titleColThree;

    @FXML
    public TableColumn<Appointments, String> typeColThree;

    @FXML
    public TableColumn<Appointments, String> descriptionColThree;

    @FXML
    public TableColumn<Appointments, LocalDateTime> startColThree;

    @FXML
    public TableColumn<Appointments, LocalDateTime> endColThree;

    @FXML
    public TableColumn<Appointments, Integer> customerColThree;

    @Override
    public void initialize (URL url, ResourceBundle rb){

        getAppointmentsForReport();

        anikaTable.setItems(getAnikaAppointments());
        apptColOne.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColOne.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColOne.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColOne.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColOne.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColOne.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerColOne.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        danielTable.setItems(getDanielAppointments());
        apptColTwo.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColTwo.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColTwo.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColTwo.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColTwo.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColTwo.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerColTwo.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        liTable.setItems(getLiAppointments());
        apptColThree.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColThree.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColThree.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColThree.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColThree.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColThree.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerColThree.setCellValueFactory(new PropertyValueFactory<>("customerId"));


    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        anikaTable.getItems().clear();
        danielTable.getItems().clear();
        liTable.getItems().clear();
    }

}


















