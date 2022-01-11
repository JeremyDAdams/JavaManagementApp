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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static utilities.sqlAppointments.contacts;
import static utilities.sqlAppointments.getAppointments;

public class ModifyAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    Appointments appointmentSelected = MainController.appointmentSelected;

    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    ObservableList<String> contactNames = FXCollections.observableArrayList();

    @FXML
    public TextField modifyIdTxt;

    @FXML
    public TextField modifyTitleTxt;

    @FXML
    public TextField modifyDescTxt;

    @FXML
    public TextField modifyLocationTxt;

    @FXML
    public TextField modifyTypeTxt;

    @FXML
    public TextField modifyCustIdTxt;

    @FXML
    public TextField modifyUserIdTxt;

    @FXML
    public DatePicker datePicker;

    @FXML
    public ComboBox startCombo;

    @FXML
    public ComboBox endCombo;

    @FXML
    public ComboBox contactCombo;

    public void initialize (URL url, ResourceBundle rb) {
        populateTimeCombos();

        for (Contacts contact : contacts) {
            contactNames.add(contact.getContactName());
        }
        startCombo.setItems(times);
        endCombo.setItems(times);
        contactCombo.setItems(contactNames);

        modifyIdTxt.setText(String.valueOf(appointmentSelected.getAppointmentId()));
        modifyTitleTxt.setText(appointmentSelected.getTitle());
        modifyDescTxt.setText(appointmentSelected.getDescription());
        modifyLocationTxt.setText(appointmentSelected.getLocation());
        modifyTypeTxt.setText(appointmentSelected.getType());
        modifyCustIdTxt.setText(String.valueOf(appointmentSelected.getCustomerId()));
        modifyUserIdTxt.setText(String.valueOf(appointmentSelected.getUserId()));
        datePicker.setValue(appointmentSelected.getStart().toLocalDate());
        startCombo.getSelectionModel().select(appointmentSelected.getStart().toLocalTime());
        endCombo.getSelectionModel().select(appointmentSelected.getEnd().toLocalTime());
        contactCombo.getSelectionModel().select(appointmentSelected.getContact());
    }

    public void populateTimeCombos() {

        LocalTime time = LocalTime.MIN;
        for(int i = 0; i <=47; i++) {
            times.add(time);
            time = time.plusMinutes(30);
        }
    }

    public void saveBtnClick(ActionEvent actionEvent) {
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        getAppointments();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
