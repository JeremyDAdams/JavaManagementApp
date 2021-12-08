package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utilities.JDBC;

import static utilities.sqlUser.getUsers;
import static utilities.sqlUser.users;

public class LogInController implements Initializable {

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


    ResourceBundle rb;

    //@Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        System.out.println(Locale.getDefault());

        Locale currentLocale = Locale.getDefault();
        passwordLabel.setText(rb.getString("password"));
        submitButton.setText(rb.getString("submit"));
        exitButton.setText(rb.getString("exit"));
        userLabel.setText(rb.getString("user"));
        headingLabel.setText(rb.getString("heading"));
        locationLabel.setText(currentLocale.getDisplayCountry());
    }

    public void submitBtnClick(ActionEvent actionEvent) throws IOException {

        Logger log = Logger.getLogger("log.txt");

        try {
            FileHandler fh = new FileHandler ("log.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.addHandler(fh);
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        getUsers();

        //System.out.println(users.getPassword());
        String userName = userField.getText();
        String password = passField.getText();
        Boolean verify = false;
        for(User user : users) {
            String uN = user.getUserName();
            String pass = user.getPassword();
            if(uN.equals(userName)) {
                if (pass.equals(password)) {
                    verify = true;
                }
            }
        }

        if(verify) {
            log.info("Login successful.");

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            log.warning("Login failed.");
        }
    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
