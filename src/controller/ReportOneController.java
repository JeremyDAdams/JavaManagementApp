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

public class ReportOneController implements Initializable {

    Parent scene;
    int numberInJan;
    int numberInFeb;
    int numberInMar;
    int numberInApr;
    int numberInMay;
    int numberInJun;
    int numberInJul;
    int numberInAug;
    int numberInSep;
    int numberInOct;
    int numberInNov;
    int numberInDec;

    int numberPlanningSession;
    int numberDebriefing;
    int numberStrategy;
    int numberPlanning;
    int numberTest;
    int numberOther;

    @FXML
    public Label janLbl;

    @FXML
    public Label febLbl;

    @FXML
    public Label marLbl;

    @FXML
    public Label aprLbl;

    @FXML
    public Label mayLbl;

    @FXML
    public Label junLbl;

    @FXML
    public Label julLbl;

    @FXML
    public Label augLbl;

    @FXML
    public Label sepLbl;

    @FXML
    public Label octLbl;

    @FXML
    public Label novLbl;

    @FXML
    public Label decLbl;

    @FXML
    public Label planningSessionLbl;

    @FXML
    public Label debriefingLbl;

    @FXML
    public Label strategyLbl;

    @FXML
    public Label planningLbl;

    @FXML
    public Label testLbl;

    @FXML
    public Label otherLbl;


    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    /*https://stackoverflow.com/questions/508791/mysql-query-group-by-day-month-year
    Was a big help here.
     */
    static String appointmentQueryMonth = "SELECT COUNT(Appointment_ID), DATE_FORMAT(start, '%M') FROM appointments GROUP BY MONTH(start)";
    static String appointmentQueryType = "SELECT COUNT(Type), Type from appointments GROUP BY Type";

    public void initialize (URL url, ResourceBundle rb) {
        try {
            monthCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        janLbl.setText(janLbl.getText() + " " + numberInJan);
        febLbl.setText(febLbl.getText() + " " + numberInFeb);
        marLbl.setText(marLbl.getText() + " " + numberInMar);
        aprLbl.setText(aprLbl.getText() + " " + numberInApr);
        mayLbl.setText(mayLbl.getText() + " " + numberInMay);
        junLbl.setText(junLbl.getText() + " " + numberInJun);
        julLbl.setText(julLbl.getText() + " " + numberInJul);
        augLbl.setText(augLbl.getText() + " " + numberInAug);
        sepLbl.setText(sepLbl.getText() + " " + numberInSep);
        octLbl.setText(octLbl.getText() + " " + numberInOct);
        novLbl.setText(novLbl.getText() + " " + numberInNov);
        decLbl.setText(decLbl.getText() + " " + numberInDec);

        try {
            typeCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        planningSessionLbl.setText(planningSessionLbl.getText() + " " + numberPlanningSession);
        debriefingLbl.setText(debriefingLbl.getText() + " " + numberDebriefing);
        strategyLbl.setText(strategyLbl.getText() + " " + numberStrategy);
        planningLbl.setText(planningLbl.getText() + " " + numberPlanning);
        testLbl.setText(testLbl.getText() + " " + numberTest);
        otherLbl.setText(otherLbl.getText() + " " + numberOther);
    }

    public void monthCount() throws SQLException {
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(appointmentQueryMonth);
        while(resultSet.next()) {
            //System.out.println(resultSet.getString(1));
            //System.out.println(resultSet.getString(2));

            if(resultSet.getString(2).equals("January")) {
                numberInJan = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("February")) {
                numberInFeb = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("March")) {
                numberInMar = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("April")) {
                numberInApr = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("May")) {
                numberInMay = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("June")) {
                numberInJun = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("July")) {
                numberInJul = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("August")) {
                numberInAug = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("September")) {
                numberInSep = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("October")) {
                numberInOct = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("November")) {
                numberInNov = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("December")) {
                numberInDec = resultSet.getInt(1);
            }
        }
    }

    public void typeCount() throws SQLException {
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(appointmentQueryType);
        while(resultSet.next()) {
            //System.out.println(resultSet.getString(1));
            //System.out.println(resultSet.getString(2));
            if(resultSet.getString(2).equals("Planning Session")) {
                numberPlanningSession = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("De-Briefing")) {
                numberDebriefing = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("Strategy")) {
                numberStrategy = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("Planning")) {
                numberPlanning = resultSet.getInt(1);
            } else if(resultSet.getString(2).equals("test")) {
                numberTest = resultSet.getInt(1);
            } else {
                numberOther = resultSet.getInt(1);
            }
        }
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
