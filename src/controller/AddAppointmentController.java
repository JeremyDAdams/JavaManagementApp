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

import static utilities.Validation.*;
import static utilities.sqlAppointments.*;

/**
 * Class for adding appointments to the database.
 */
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


    /** Initialize AddAppointmentController.
     * A lambda expression is used to generate the times for the time selection combo boxes.
     * @param url
     * @param rb
     */
    public void initialize (URL url, ResourceBundle rb){

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


    /** This saves a new appointment.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
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

        boolean boolTest = validBusinessHours(startLDT, endLDT, date);
        System.out.println(boolTest);
        if (!(validBusinessHours(startLDT, endLDT, date))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Appointment times must be between 8:00 and 22:00 EST");
            alert.showAndWait();
        } else if (!(noAppointmentOverlapNew(startLDT, endLDT, custId))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Appointment time must not overlap with existing appointment for this customer.");
            alert.showAndWait();
        } else {
            saveAppointment(title, description, location, contactId, type, start, end, custId, userId);
            getAppointments();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /** Return to Main screen.
     * @param actionEvent
     * @throws IOException
     */
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

    /** Exits application. This button closes the server connection and exits the application.
     * @param actionEvent
     */
    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

}
