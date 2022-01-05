package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class sqlAppointments {
    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String appointmentsQuery = "SELECT * FROM appointments";
    static String contactsQuery = "SELECT * FROM contacts";
    public static ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    public static ObservableList<Contacts> contacts = FXCollections.observableArrayList();



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
                LocalDateTime startDate = resultSet.getObject("Start", LocalDateTime.class);
                LocalDateTime endDate = resultSet.getObject("End", LocalDateTime.class);
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");

                Appointments appointment = new Appointments();
                appointment.setAppointmentId(appointmentId);
                appointment.setTitle(title);
                appointment.setDescription(description);
                appointment.setLocation(location);
                appointment.setType(type);
                ZonedDateTime startConverted = startDate.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
                appointment.setStart(startConverted);
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
    public static void getContacts() throws SQLException {

        statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery(contactsQuery);

        getAllAppointments();

        while (resultSet1.next()) {
            int contactId = resultSet1.getInt("Contact_ID");
            String contactName = resultSet1.getString("Contact_Name");

            Contacts contact = new Contacts();

            contact.setContactId(contactId);
            contact.setContactName(contactName);
            contacts.add(contact);
        }

    }

    public static ObservableList<Contacts> getAllContacts() {
        return contacts;
    }

    public static ObservableList<Appointments> getAllAppointments() {
        return appointments;
    }
}
