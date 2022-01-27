package utilities;

import controller.LogInController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.*;
import java.time.*;

import static java.time.LocalDateTime.now;

/**
 * Class to do most SQL related to appointments.
 */
public class sqlAppointments {
    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String appointmentsQuery = "SELECT * FROM appointments";
    static String contactsQuery = "SELECT * FROM contacts";
    public static ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    public static ObservableList<Appointments> anikaAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointments> danielAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointments> liAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointments> appointmentsByMonth = FXCollections.observableArrayList();
    public static ObservableList<Appointments> appointmentsByWeek = FXCollections.observableArrayList();
    public static ObservableList<Contacts> contacts = FXCollections.observableArrayList();


    /**
     * Method to get appointments.
     */
    public static void getAppointments() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(appointmentsQuery);

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact = null;
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                System.out.println(resultSet.getTimestamp("Start"));
                Appointments appointment = new Appointments();
                appointment.setAppointmentId(appointmentId);
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                appointment.setStart(startDate);
                appointment.setEnd(endDate);
                appointment.setCustomerId(customerId);
                appointment.setUserId(userId);
                appointment.setContactId(contactId);

                appointments.add(appointment);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to get appointments for the second report.
     */
    public static void getAppointmentsForReport() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(appointmentsQuery);

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact = null;
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                System.out.println(resultSet.getTimestamp("Start"));
                Appointments appointment = new Appointments();
                appointment.setAppointmentId(appointmentId);
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                appointment.setStart(startDate);
                appointment.setEnd(endDate);
                appointment.setCustomerId(customerId);
                appointment.setUserId(userId);
                appointment.setContactId(contactId);

                appointments.add(appointment);
                if(appointment.getContactId() == 1) {
                    anikaAppointments.add(appointment);
                } else if(appointment.getContactId() == 2) {
                    danielAppointments.add(appointment);
                } else if(appointment.getContactId() == 3) {
                    liAppointments.add(appointment);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to get appointments for the current month.
     */
    public static void getAppointmentsByMonth() {
        try {
            Month currentMonth = now().getMonth();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(appointmentsQuery);

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact = null;
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                //System.out.println(startDate.getMonth());
                //System.out.println(currentMonth);

                if(startDate.getMonth().equals(currentMonth)) {
                    Appointments appointment = new Appointments();
                    appointment.setAppointmentId(appointmentId);
                    appointment.setTitle(title);
                    appointment.setDescription(description);
                    appointment.setLocation(location);
                    appointment.setType(type);
                    appointment.setStart(startDate);
                    appointment.setEnd(endDate);
                    appointment.setCustomerId(customerId);
                    appointment.setUserId(userId);
                    appointment.setContactId(contactId);
                    appointmentsByMonth.add(appointment);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to get appointments for the current week.
     */
    public static void getAppointmentsByWeek() {
        try {

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(appointmentsQuery);

            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String contact = null;
                String type = resultSet.getString("Type");
                LocalDateTime startDate = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDate = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                LocalDateTime currentWeek = now().plusDays(6);

                if(startDate.isAfter(now()) && startDate.isBefore(currentWeek)) {
                    Appointments appointment = new Appointments();
                    appointment.setAppointmentId(appointmentId);
                    appointment.setTitle(title);
                    appointment.setDescription(description);
                    appointment.setLocation(location);
                    appointment.setType(type);
                    appointment.setStart(startDate);
                    appointment.setEnd(endDate);
                    appointment.setCustomerId(customerId);
                    appointment.setUserId(userId);
                    appointment.setContactId(contactId);
                    appointmentsByWeek.add(appointment);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Method to get contacts.
     * @throws SQLException
     */
    public static void getContacts() throws SQLException {

        statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery(contactsQuery);

        while (resultSet1.next()) {
            int contactId = resultSet1.getInt("Contact_ID");
            String contactName = resultSet1.getString("Contact_Name");

            Contacts contact = new Contacts();

            contact.setContactId(contactId);
            contact.setContactName(contactName);
            contacts.add(contact);
        }
    }

    /** Method to save a new appointment to the database.
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param custId
     * @param userId
     * @throws SQLException
     */
    public static void saveAppointment(String title, String description, String location, int contactId, String type, Timestamp start, Timestamp end, int custId, int userId) throws SQLException {
        try {
            String userName = LogInController.userName;
            String saveString = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " + "VALUES (?, ?, ? ,?, ?, ? ,NOW(),? ,NOW(), ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(saveString);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, location);
            statement.setString(4, type);
            statement.setTimestamp(5, start);
            statement.setTimestamp(6, end);
            statement.setString(7, userName);
            statement.setString(8, userName);
            statement.setInt(9, custId);
            statement.setInt(10, userId);
            statement.setInt(11, contactId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method to save modifications to the selected appointment.
     * @param title
     * @param description
     * @param location
     * @param contactId
     * @param type
     * @param start
     * @param end
     * @param custId
     * @param userId
     * @param apptId
     * @throws SQLException
     */
    public static void modifyAppointment(String title, String description, String location, int contactId, String type, Timestamp start, Timestamp end, int custId, int userId, int apptId) throws SQLException {
        String userName = LogInController.userName;
        String saveString = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Last_Update = NOW(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_Id = ? " +
                "WHERE Appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(saveString);
        statement.setString(1, title);
        statement.setString(2, description);
        statement.setString(3, location);
        statement.setString(4, type);
        statement.setTimestamp(5, start);
        statement.setTimestamp(6, end);
        statement.setString(7, userName);
        statement.setInt(8, custId);
        statement.setInt(9, userId);
        statement.setInt(10, contactId);
        statement.setInt(11, apptId);
        statement.executeUpdate();
    }

    /** Method to delete the selected appointment.
     * @param apptId
     * @throws SQLException
     */
    public static void deleteAppointment(int apptId) throws SQLException {
        String deleteString = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement statement = connection.prepareStatement(deleteString);
        statement.setInt(1, apptId);
        statement.executeUpdate();
    }

    /** Method to get all contacts.
     * @return
     */
    public static ObservableList<Contacts> getAllContacts() {
        return contacts;
    }

    /** Method to get all appointments.
     * @return
     */
    public static ObservableList<Appointments> getAllAppointments() {
        return appointments;
    }

    /** Method to return Anika's appointments.
     * @return
     */
    public static ObservableList<Appointments> getAnikaAppointments() {
        return anikaAppointments;
    }

    /** Method to return Daniel's appointments.
     * @return
     */
    public static ObservableList<Appointments> getDanielAppointments() {
        return danielAppointments;
    }

    /** Method to return Li's appointments.
     * @return
     */
    public static ObservableList<Appointments> getLiAppointments() {
        return liAppointments;
    }

    /** Method to return the current month's appointments.
     * @return
     */
    public static ObservableList<Appointments> getMonthAppointments() {
        return appointmentsByMonth;
    }

    /** Method to return the current week's appointments.
     * @return
     */
    public static ObservableList<Appointments> getWeekAppointments() {
        return appointmentsByWeek;
    }
}
