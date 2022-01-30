package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utilities.AlertInterface;
import utilities.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utilities.Validation.logInAppointmentAlert;
import static utilities.sqlAppointments.getAppointments;
import static utilities.sqlCustomer.getCustomers;
import static utilities.sqlUser.getUsers;
import static utilities.sqlUser.users;

/**
 * Controller for the log-in screen.
 */
public class LogInController implements Initializable {

    public static String userName;
    public static int userId;

    public String logInError = "";
    Stage stage;
    Parent scene;

    @FXML
    public TextField passField;

    @FXML
    public TextField userField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label userLabel;

    @FXML
    private Label headingLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label timeZoneLabel;

    ResourceBundle rb;

    /** Method to initialize the log-in controller.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        this.rb = rb;
        System.out.println(Locale.getDefault());
        System.out.println(ZoneId.systemDefault());

        getCustomers();
        getAppointments();

        Locale currentLocale = Locale.getDefault();
        passwordLabel.setText(rb.getString("password"));
        submitButton.setText(rb.getString("submit"));
        exitButton.setText(rb.getString("exit"));
        userLabel.setText(rb.getString("user"));
        headingLabel.setText(rb.getString("heading"));
        locationLabel.setText(currentLocale.getDisplayCountry());
        timeZoneLabel.setText("Time zone: " + String.valueOf(ZoneId.systemDefault()));
        logInError = rb.getString("loginerror");

        submitButton.setDefaultButton(true);
    }

    /** Method to submit username and password.
     * Provided credentials are then checked against those in the database.
     * A lambda was added to display an alert if the credentials didn't match.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void submitBtnClick(ActionEvent actionEvent) throws IOException, SQLException {

        Logger log = Logger.getLogger("login_activity.txt");

        try {
            FileHandler fh = new FileHandler ("login_activity.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getUsers();
        //getCustomers();
        //System.out.println(users.getPassword());
        userName = userField.getText();
        String password = passField.getText();
        Boolean verify = false;
        String uN = "";
        String pass = "";

        for(User user : users) {
            uN = user.getUserName();
            pass = user.getPassword();
            if(uN.equals(userName)) {
                if (pass.equals(password)) {
                    verify = true;
                    userId = user.getUserId();
                }
            }
        }

        if(verify) {
            log.info("Login successful for user: " + userName);
            System.out.println(" Hello there " + userName);
            System.out.println(" Hey there " + userId);
            logInAppointmentAlert();
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            log.warning("Login failed for user: " + userName);
            System.out.println("Not working.");
            alert.displayAlert();
        }
    }

    AlertInterface alert = () -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        logInError = rb.getString("loginerror");
        alert.setContentText(logInError);
        alert.showAndWait();
    };

    /** Exits application. This button closes the server connection and exits the application.
     * @param actionEvent
     */
    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
