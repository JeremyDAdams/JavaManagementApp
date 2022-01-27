package utilities;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import controller.LogInController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.User;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Class to do most of the SQL related to customers.
 */
public class sqlCustomer {
    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String customerQuery = "SELECT * FROM customers";
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();


    /**
     * Method to get customers from the database.
     */
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
                customer.setAddress(address);
                customer.setPostalCode(postalCode);
                customer.setPhone(phone);
                customer.setDivisionId(divisionId);
                customers.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Method to save a new customer.
     * @param name
     * @param address
     * @param postCode
     * @param phoneNumber
     * @param divId
     * @throws SQLException
     */
    public static void saveCustomer(String name, String address, String postCode, String phoneNumber, int divId) throws SQLException {
        try {
            String userName = LogInController.userName;
            String saveString = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " + "VALUES (?, ?, ? ,? ,NOW(),? ,NOW(), ?, ?)";
            PreparedStatement statement = connection.prepareStatement(saveString);
            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postCode);
            statement.setString(4, phoneNumber);
            statement.setString(5, userName);
            statement.setString(6, userName);
            statement.setInt(7, divId);
            statement.executeUpdate();
            //customers.add(customer);
            //saveString.setString
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method to update an existing customer.
     * @param name
     * @param address
     * @param postCode
     * @param phoneNumber
     * @param divId
     * @param customerId
     * @throws SQLException
     */
    public static void updateCustomer(String name, String address, String postCode, String phoneNumber, int divId, int customerId) throws SQLException {
        String userName = LogInController.userName;
        String saveString = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement statement = connection.prepareStatement(saveString);
        statement.setString(1, name);
        statement.setString(2, address);
        statement.setString(3, postCode);
        statement.setString(4, phoneNumber);
        statement.setString(5, userName);
        statement.setInt(6, divId);
        statement.setInt(7, customerId);
        statement.executeUpdate();

    }

    /** Method to delete a selected customer and any linked appointments.
     * @param customerId
     * @throws SQLException
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String deleteRelatedAppt = "DELETE FROM appointments WHERE Customer_ID = ?";
        String deleteCustomerString = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement statement0 = connection.prepareStatement(deleteRelatedAppt);
        statement0.setInt(1, customerId);
        statement0.executeUpdate();
        PreparedStatement statement = connection.prepareStatement(deleteCustomerString);
        statement.setInt(1, customerId);
        statement.executeUpdate();
    }

    public static ObservableList<Customer> getAllCustomers() {
        return customers;
    }
}
