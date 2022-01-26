package controller;

import javafx.fxml.Initializable;
import utilities.JDBC;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportOneController implements Initializable {

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

    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String appointmentQueryMonth = "SELECT COUNT(Appointment_ID), DATE_FORMAT(start, '%M') FROM appointments GROUP BY MONTH(start)";
    static String appointmentQueryType = "SELECT COUNT(Type), Type from appointments GROUP BY Type";

    public void initialize (URL url, ResourceBundle rb) {
        try {
            monthCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            typeCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
}
