package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

public class sqlUser {
    private static Connection connection = JDBC.getConnection();

    static Statement statement = null;
    static String userQuery = "SELECT * FROM user";
    public static ObservableList<User> users = FXCollections.observableArrayList();

    public static void getUsers() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(userQuery);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
