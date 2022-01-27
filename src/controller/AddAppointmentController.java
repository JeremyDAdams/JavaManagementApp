package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contacts;
import utilities.TimeInterface;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import static utilities.sqlAppointments.*;

public class AddAppointmentController implements Initializable {


    @FXML
    public TextField addTitleTxt;

    @FXML
    public TextField addDescTxt;

    @FXML
    public TextField addLocationTxt;

    @FXML
    public TextField addTypeTxt;

    @FXML
    public TextField addCustIdTxt;

    @FXML
    public TextField addUserIdTxt;

    @FXML
    public DatePicker datePicker;

    @FXML
    public ComboBox startCombo;

    @FXML
    public ComboBox endCombo;

    @FXML
    public ComboBox contactCombo;



    Stage stage;
    Parent scene;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    ObservableList<LocalTime> times = FXCollections.observableArrayList();
    ObservableList<String> contactNames = FXCollections.observableArrayList();


    public void initialize (URL url, ResourceBundle rb){
        //populateTimeCombos();

        time.timeCombo();
        contacts.clear();
        try {
            getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (Contacts contact : contacts) {
            contactNames.add(contact.getContactName());
        }
        startCombo.setItems(times);
        endCombo.setItems(times);
        contactCombo.setItems(contactNames);
    }

    public void populateTimeCombos() {

    }


    public void saveBtnClick(ActionEvent actionEvent) throws IOException, SQLException {
        String title = addTitleTxt.getText();
        String description = addDescTxt.getText();
        String location = addLocationTxt.getText();
        int contactId = 1000;
        for (Contacts contact : contacts) {
            if (contact.getContactName() == contactCombo.getValue().toString()) {
                contactId = contact.getContactId();
            }
        }
        String type = addTypeTxt.getText();
        LocalDate date = datePicker.getValue();

        String startTimeString = startCombo.getValue().toString();
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalDateTime startLDT = startTime.atDate(date);
        Timestamp start = Timestamp.valueOf(startLDT);

        String endTimeString = endCombo.getValue().toString();
        LocalTime endTime = LocalTime.parse(endTimeString);
        LocalDateTime endLDT = endTime.atDate(date);
        Timestamp end = Timestamp.valueOf(endLDT);

        int custId = Integer.parseInt(addCustIdTxt.getText());
        int userId = Integer.parseInt(addUserIdTxt.getText());

        saveAppointment(title, description, location, contactId, type, start, end, custId, userId);

        getAppointments();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        getAppointments();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    TimeInterface time = () -> {
        LocalTime time = LocalTime.MIN;
        for(int i = 0; i <=47; i++) {
            times.add(time);
            time = time.plusMinutes(30);
        }
    };

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

}
