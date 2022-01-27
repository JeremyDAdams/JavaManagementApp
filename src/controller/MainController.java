package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customer;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static utilities.sqlAppointments.*;
import static utilities.sqlCustomer.*;

/** Controller for the main screen. Populates and displays tables.
 */
public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    public static Customer customerSelected;
    public static Appointments appointmentSelected;
    public String country;


    @FXML
    public TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivCol;

    @FXML
    public TableView<Appointments> apptTableView;

    @FXML
    public TableColumn<Appointments, Integer> apptIdCol;

    @FXML
    public TableColumn<Appointments, String> apptTitleCol;

    @FXML
    public TableColumn<Appointments, String> apptDescCol;

    @FXML
    public TableColumn<Appointments, String> apptLocationCol;

    @FXML
    public TableColumn<Appointments, String> apptContactCol;

    @FXML
    public TableColumn<Appointments, String> apptTypeCol;

    @FXML
    public TableColumn<Appointments, LocalDateTime> apptStartCol;

    @FXML
    public TableColumn<Appointments, LocalDateTime> apptEndCol;

    @FXML
    public TableColumn<Appointments, Integer> apptCustIdCol;

    @FXML
    public TableColumn<Appointments, Integer> apptUserIdCol;

    @FXML
    public RadioButton allApptRadio;



    /** Initialize MainController.java
     * @param url
     * @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){
        appointments.clear();
        customerTableView.setItems(getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        int divisionId;
        for(Customer customer : customers) {
            divisionId = customer.getDivisionId();
            if(divisionId < 55) {
                country = "U.S";
            } else if(divisionId > 59 && divisionId < 73) {
                country = "Canada";
            } else if (divisionId > 100) {
                country = "UK";
            }
            customer.setCountry(country);
        }
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        int userId = LogInController.userId;

        getAppointments();
        apptTableView.setItems(getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String contact1 = null;
        for (Appointments appointment : appointments) {
            for (Contacts contact : contacts) {
                if (appointment.getContactId() == contact.getContactId()) {
                    contact1 = contact.getContactName();
                }
                appointment.setContact(contact1);
            }
        }

        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        allApptRadio.isSelected();
    }


    /** Switch to add customer screen. This switches screens and clears the table view for customers.
     * @param actionEvent
     * @throws IOException
     */
    public void custAddBtnClick(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        customerTableView.getItems().clear();
    }

    /** Switch to update customer screen. This switches screens and clears the table view for customers.
     * @param actionEvent
     * @throws IOException
     */
    public void custUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        customerSelected = customerTableView.getSelectionModel().getSelectedItem();
        if(customerSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("A customer must be selected.");
            alert.showAndWait();
        } else {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            customerTableView.getItems().clear();
        }
    }

    /** Delete selected customer. This deletes the selected customer and any associated appointments.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void custDeleteBtnClick(ActionEvent actionEvent) throws SQLException, IOException {
        customerSelected = customerTableView.getSelectionModel().getSelectedItem();
        int customerId = customerSelected.getCustomerId();

        if(customerSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("A customer must be selected.");
            alert.showAndWait();
        } else {
            deleteCustomer(customerId);

            apptTableView.getItems().clear();
            getAppointments();

            customerTableView.getItems().clear();
            getCustomers();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("The selected customer and related appointments have been deleted.");
            alert.showAndWait();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Switch to add appointment screen. This switches screens and clears the table view for appointments.
     * @param actionEvent
     * @throws IOException
     */
    public void apptAddBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        apptTableView.getItems().clear();
    }

    /** Switch to update appointment screen. This switches screens and clears the table view for appointments.
     * @param actionEvent
     * @throws IOException
     */
    public void apptUpdateBtnClick(ActionEvent actionEvent) throws IOException {

        appointmentSelected = apptTableView.getSelectionModel().getSelectedItem();
        if(appointmentSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An appointment must be selected.");
            alert.showAndWait();
        } else {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            apptTableView.getItems().clear();
        }
    }

    /** Delete selected appointment. This deletes the selected appointment and repopulates the table.
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void apptDeleteBtnClick(ActionEvent actionEvent) throws SQLException, IOException {
        appointmentSelected = apptTableView.getSelectionModel().getSelectedItem();
        int apptId = appointmentSelected.getAppointmentId();

        if(appointmentSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An appointment must be selected.");
            alert.showAndWait();
        } else {
            deleteAppointment(apptId);

            apptTableView.getItems().clear();
            getAppointments();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText(
                    "Appointment with ID: " + appointmentSelected.getAppointmentId() +
                            "and type: " + appointmentSelected.getType() + " has been deleted.");
            alert.showAndWait();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Exits application. This button closes the server connection and exits the application.
     * @param actionEvent
     */
    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /** Shows all appointments. This radio button makes the appointment table show all appointments.
     * @param actionEvent
     * @throws IOException
     */
    public void apptAllRadioClick(ActionEvent actionEvent) throws IOException {
        getAppointments();
        apptTableView.getItems().clear();
        apptTableView.setItems(getAllAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String contact1 = null;
        for (Appointments appointment : appointments) {
            for (Contacts contact : contacts) {
                if (appointment.getContactId() == contact.getContactId()) {
                    contact1 = contact.getContactName();
                }
                appointment.setContact(contact1);
            }
        }

        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /** Show monthly appointments. This radio button makes the appointment table show only appointments for the current month.
     * @param actionEvent
     */
    public void apptMonthRadioClick(ActionEvent actionEvent) {
        apptTableView.getItems().clear();
        appointments.clear();
        getAppointmentsByMonth();
        apptTableView.setItems(getMonthAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String contact1 = null;
        for (Appointments appointment : appointmentsByMonth) {
            for (Contacts contact : contacts) {
                if (appointment.getContactId() == contact.getContactId()) {
                    contact1 = contact.getContactName();
                }
                appointment.setContact(contact1);
            }
        }

        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /** Show weekly appointments. This radio button makes the appointment table show only appointments for the next 7 days.
     * @param actionEvent
     */
    public void apptWeekRadioClick(ActionEvent actionEvent) {
        apptTableView.getItems().clear();
        appointments.clear();
        getAppointmentsByWeek();
        apptTableView.setItems(getWeekAppointments());
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        try {
            getContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String contact1 = null;
        for (Appointments appointment : appointmentsByWeek) {
            for (Contacts contact : contacts) {
                if (appointment.getContactId() == contact.getContactId()) {
                    contact1 = contact.getContactName();
                }
                appointment.setContact(contact1);
            }
        }

        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /** Switch to report one screen. This button switches screens to the first required report.
     * @param actionEvent
     * @throws IOException
     */
    public void report1BtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportOne.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        apptTableView.getItems().clear();
    }

    /** Switch to report two screen. This button switches screens to the second required report.
     * @param actionEvent
     * @throws IOException
     */
    public void report2BtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportTwo.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        apptTableView.getItems().clear();
    }

    /** Switch to report three screen. This button switches screens to the third required report.
     * @param actionEvent
     * @throws IOException
     */
    public void report3BtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportThree.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        apptTableView.getItems().clear();
    }
}

















