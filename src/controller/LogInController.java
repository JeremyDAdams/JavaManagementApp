package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import model.User;
import utilities.JDBC;

import static utilities.sqlUser.getUsers;
import static utilities.sqlUser.users;

public class LogInController implements Initializable {

    public TextField passField;
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

    public void submitBtnClick(ActionEvent actionEvent) {
        getUsers();

        for(User user : users) {
            //String userName = user.getUserName();
            //String password = user.getPassword();

            if(user.getUserName() == userField.getText() && user.getPassword() == passField.getText()){
                System.out.println("Login worked.");
            } else {
                System.out.println("Ruh roh");
            }
        }
    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
