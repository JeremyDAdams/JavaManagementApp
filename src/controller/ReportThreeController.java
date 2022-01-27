package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Class to generate the third required report.
 * It shows the number of customers created by each user.
 */
public class ReportThreeController implements Initializable {

    Parent scene;

    int numberByAdmin;
    int numberByTest;
    int numberByScript;

    @FXML
    public Label createdByAdmin;

    @FXML
    public Label createdByTest;

    @FXML
    public Label createdByScript;

    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String customerQuery = "SELECT COUNT(Customer_ID), Created_By FROM customers GROUP BY Created_By";

    /** Method to initialize ReportThreeController.
     * @param url
     * @param rb
     */
    @Override
    public void initialize (URL url, ResourceBundle rb) {
        try {
            customerCreationCounts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void customerCreationCounts() throws SQLException {

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(customerQuery);
        while(resultSet.next()) {
            System.out.println(resultSet.getString(2));

            if(resultSet.getString(2).equals("test")) {
                numberByTest = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("admin")) {
                numberByAdmin = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("script")) {
                numberByScript = resultSet.getInt(1);
            }

        }

        createdByAdmin.setText(Integer.toString(numberByAdmin));
        createdByTest.setText(Integer.toString(numberByTest));
        createdByScript.setText(Integer.toString(numberByScript));

    }

    /** Return to Main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
