package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    private Label passwordLabel;

    @FXML
    private Button submitButton;

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

        passwordLabel.setText(rb.getString("password"));
        submitButton.setText(rb.getString("submit"));
        userLabel.setText(rb.getString("user"));
        headingLabel.setText(rb.getString("heading"));
    }

}
