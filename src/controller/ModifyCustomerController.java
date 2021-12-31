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

public class ModifyCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    public int divId;
    public Connection conn;

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
        Customer customerSelected = MainController.customerSelected;
        System.out.println(customerSelected.getCustomerName());
        ResultSet rs = accessDB("SELECT * FROM countries");
        try {

            while (rs.next()) {
                addCountryCombo.getItems().add(rs.getString(2));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ComboBoxExampleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        addNameTxt.setText(String.valueOf(customerSelected.getCustomerName()));
        addAddressTxt.setText(String.valueOf(customerSelected.getAddress()));
        addPostalTxt.setText(String.valueOf(customerSelected.getPostalCode()));
        addPhoneTxt.setText(String.valueOf(customerSelected.getPhone()));
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

    public void countryComboSelect(ActionEvent actionEvent) {
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

    public void divisionComboSelect(ActionEvent actionEvent) {
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
    }

    public void saveBtnClick(ActionEvent actionEvent) {
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
