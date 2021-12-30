package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class sqlCombo {
    private static Connection connection = JDBC.getConnection();
    static Statement statement = null;
    static String countryQuery = "SELECT * FROM countries";
    public static ObservableList<Countries> countries = FXCollections.observableArrayList();

    public static void getCountries() {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(countryQuery);

            while(resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String country = resultSet.getString("Country");

                Countries country1 = new Countries();
                country1.setCountryId(countryId);
                country1.setCountry(country);
                countries.add(country1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static ObservableList<Countries> getAllCountries() {
        return countries;
    }
}
