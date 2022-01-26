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

    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String appointmentQuery1 = "SELECT COUNT(Appointment_ID), DATE_FORMAT(start, '%M') FROM appointments GROUP BY MONTH(start)";


    public void initialize (URL url, ResourceBundle rb) {
        try {
            monthCount();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void monthCount() throws SQLException {
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(appointmentQuery1);
        while(resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));

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
}
