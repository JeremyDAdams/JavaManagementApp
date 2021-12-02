package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class sqlUser {
    private static Connection connection = JDBC.getConnection();

    static Statement statement = null;
    static String userQuery = "SELECT * FROM users";
    public static ObservableList<User> users = FXCollections.observableArrayList();

    public static void getUsers() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(userQuery);

            while (resultSet.next()) {
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
