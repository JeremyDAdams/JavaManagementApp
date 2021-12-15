package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import utilities.JDBC;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @Override
    public void initialize (URL url, ResourceBundle rb){
        int userId = LogInController.USEID;
        System.out.println(userId + "This is from MainController");

    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
