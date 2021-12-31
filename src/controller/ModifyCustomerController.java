package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static utilities.sqlCustomer.saveCustomer;
import static utilities.sqlCustomer.updateCustomer;

public class ModifyCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    public int divId;
    public Connection conn;

    public String countryName;

    Customer customerSelected = MainController.customerSelected;

    @FXML
    private TextField addIdTxt;

    @FXML
    private TextField addNameTxt;

    @FXML
    private TextField addAddressTxt;

    @FXML
    private TextField addPostalTxt;

    @FXML
    private TextField addPhoneTxt;

    @FXML
    private ComboBox addCountryCombo;

    @FXML
    private ComboBox addFirstLevelCombo;

    //@Override
    public void initialize (URL url, ResourceBundle rb){

        System.out.println(customerSelected.getCustomerName());
        ResultSet rs = null;

            rs = accessDB("SELECT * FROM countries");

        try {

            while (rs.next()) {
                addCountryCombo.getItems().add(rs.getString(2));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ComboBoxExampleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addIdTxt.setText(String.valueOf(customerSelected.getCustomerId()));
        addNameTxt.setText(String.valueOf(customerSelected.getCustomerName()));
        addAddressTxt.setText(String.valueOf(customerSelected.getAddress()));
        addPostalTxt.setText(String.valueOf(customerSelected.getPostalCode()));
        addPhoneTxt.setText(String.valueOf(customerSelected.getPhone()));
        if(customerSelected.getCountry().equals("U.S")) {
            addCountryCombo.getSelectionModel().select(0);
        } else if(customerSelected.getCountry().equals("UK")) {
            addCountryCombo.getSelectionModel().select(1);
        } else if(customerSelected.getCountry().equals("Canada")) {
            addCountryCombo.getSelectionModel().select(2);
        }

        try {
            populateCountriesCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(customerSelected.getDivisionId() + "hola");

        if(customerSelected.getCountry().equals("U.S")) {
            addFirstLevelCombo.getSelectionModel().select(customerSelected.getDivisionId() - 1);
        } else if(customerSelected.getCountry().equals("UK")) {
            addFirstLevelCombo.getSelectionModel().select(customerSelected.getDivisionId() - 101);
        } else if(customerSelected.getCountry().equals("Canada")) {
            addFirstLevelCombo.getSelectionModel().select(customerSelected.getDivisionId() - 60);
        }

    }

    public ResultSet accessDB(String sql) {

        ResultSet rs = null;

        try {

            Statement stmt;

            conn = JDBC.makeConnection();

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
            rs.beforeFirst(); //prepare for rs to be used by the caller
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public void countryComboSelect(ActionEvent actionEvent) throws SQLException {
        populateCountriesCombo();
    }

    public void divisionComboSelect(ActionEvent actionEvent) throws SQLException {
        divStringToInt();
    }

    public void populateCountriesCombo() throws SQLException {
        if(addCountryCombo.getValue().toString().equals("Canada")) {
            ResultSet rs = accessDB("SELECT * FROM first_level_divisions WHERE Country_ID = 3");

            addFirstLevelCombo.setItems(FXCollections.observableArrayList());
            try {
                while (rs.next()) {
                    addFirstLevelCombo.getItems().add(rs.getString(2));
                }
            } catch (SQLException ex) {

            }
        } else if(addCountryCombo.getValue().toString().equals("U.S")) {
            ResultSet rs = accessDB("SELECT * FROM first_level_divisions WHERE Country_ID = 1");
            addFirstLevelCombo.setItems(FXCollections.observableArrayList());
            try {
                while (rs.next()) {
                    addFirstLevelCombo.getItems().add(rs.getString(2));
                }
            } catch (SQLException ex) {

            }
        } else if(addCountryCombo.getValue().toString().equals("UK")) {
            ResultSet rs = accessDB("SELECT * FROM first_level_divisions WHERE Country_ID = 2");
            addFirstLevelCombo.setItems(FXCollections.observableArrayList());
            try {
                while (rs.next()) {
                    addFirstLevelCombo.getItems().add(rs.getString(2));
                }
            } catch (SQLException ex) {

            }
        }
    }

    public int getDivInt() throws SQLException {

        ResultSet rs = accessDB("SELECT * FROM first_level_divisions");
        try {
            while (rs.next()) {
                if(rs.getString(2).equals(customerSelected.getDivisionId())) {
                    divId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {

        }
        System.out.println(divId);

        return divId;
    }

    public int divStringToInt() throws SQLException {
        addFirstLevelCombo.getValue();
        ResultSet rs = accessDB("SELECT * FROM first_level_divisions");
        try {
            while (rs.next()) {
                if(rs.getString(2).equals(addFirstLevelCombo.getValue())) {
                    divId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {

        }
        System.out.println(divId);

        return divId;
    }

    public void saveBtnClick(ActionEvent actionEvent) throws SQLException {
        String name = addNameTxt.getText();
        String address = addAddressTxt.getText();
        String postalCode = addPostalTxt.getText();
        String phone = addPhoneTxt.getText();
        int divisionId = divStringToInt();
        int customerId = customerSelected.getCustomerId();

        updateCustomer(name, address, postalCode, phone, divisionId, customerId);
        System.out.println(divisionId);
    }

    public void backBtnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
