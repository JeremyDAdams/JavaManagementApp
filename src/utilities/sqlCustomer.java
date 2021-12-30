package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

public class sqlCustomer {
    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String customerQuery = "SELECT * FROM customers";
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    public static void getCustomers() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(customerQuery);

            while(resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                LocalDateTime createDate = resultSet.getObject("Create_Date", LocalDateTime.class);
                String createdBy = resultSet.getString("Created_By");
                LocalDateTime lastUpdate = resultSet.getObject("Last_Update", LocalDateTime.class);
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionId = resultSet.getInt("Division_ID");

                Customer customer = new Customer();
                customer.setCustomerId(customerId);
                customer.setCustomerName(customerName);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveCustomer() {
        String saveString = "INSERT INTO customer (customerName, address, postalCode, phone, createDate, CreatedBy, lastUpdate, lastUpdateBy, divisionId) " + "VALUES (?, ?, ? ,? ,NOW(),? ,NOW(), ?, ?)";
        //saveString.setString
    }
    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }
}
