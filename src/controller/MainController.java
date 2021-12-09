package controller;

import javafx.event.ActionEvent;
import utilities.JDBC;

public class MainController {
    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
