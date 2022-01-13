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
import model.Appointments;
import model.Contacts;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static utilities.Validation.*;
import static utilities.sqlAppointments.*;

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

    public void saveBtnClick(ActionEvent actionEvent) throws SQLException, IOException {

        String title = modifyTitleTxt.getText();
        String description = modifyDescTxt.getText();
        String location = modifyLocationTxt.getText();
        int contactId = 1000;
        for (Contacts contact : contacts) {
            if (contact.getContactName() == contactCombo.getValue().toString()) {
                contactId = contact.getContactId();
            }
        }
        String type = modifyTypeTxt.getText();
        LocalDate date = datePicker.getValue();

        String startTimeString = startCombo.getValue().toString();
        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalDateTime startLDT = startTime.atDate(date);
        Timestamp start = Timestamp.valueOf(startLDT);

        String endTimeString = endCombo.getValue().toString();
        LocalTime endTime = LocalTime.parse(endTimeString);
        LocalDateTime endLDT = endTime.atDate(date);
        Timestamp end = Timestamp.valueOf(endLDT);

        int custId = Integer.parseInt(modifyCustIdTxt.getText());
        int userId = Integer.parseInt(modifyUserIdTxt.getText());
        int apptId = Integer.parseInt(modifyIdTxt.getText());

        boolean boolTest = validBusinessHours(startLDT, endLDT, date);
        System.out.println(boolTest);
        if (!(validBusinessHours(startLDT, endLDT, date))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Appointment times must be between 8:00 and 22:00 EST");
            alert.showAndWait();
        } else if (!(noAppointmentOverlap(startLDT, endLDT, custId, apptId))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Appointment time must not overlap with existing appointment for this customer.");
            alert.showAndWait();
        } else {
            modifyAppointment(title, description, location, contactId, type, start, end, custId, userId, apptId);
            getAppointments();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        getAppointments();
        contactCombo.setItems(FXCollections.observableArrayList());
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
