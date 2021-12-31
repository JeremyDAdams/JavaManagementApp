package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.sqlCustomer.*;
import static utilities.sqlUser.users;

public class MainController implements Initializable {


    Stage stage;
    Parent scene;

    public static Customer customerSelected;
    public String country;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, Integer> customerDivCol;



    @Override
    public void initialize (URL url, ResourceBundle rb){



        customerTableView.setItems(getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        int divisionId;
        for(Customer customer : customers) {
            divisionId = customer.getDivisionId();
            if(divisionId < 55) {
                country = "U.S";
            } else if(divisionId > 59 && divisionId < 73) {
                country = "Canada";
            } else if (divisionId > 100) {
                country = "UK";
            }
            customer.setCountry(country);
        }
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        int userId = LogInController.userId;
        System.out.println(userId + "This is from MainController");
    }


    public void custAddBtnClick(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void custUpdateBtnClick(ActionEvent actionEvent) throws IOException {
        customerSelected = customerTableView.getSelectionModel().getSelectedItem();
        if(customerSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("A customer must be selected.");
            alert.showAndWait();
        } else {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void custDeleteBtnClick(ActionEvent actionEvent) {
    }

    public void apptAddBtnClick(ActionEvent actionEvent) {
    }

    public void apptUpdateBtnClick(ActionEvent actionEvent) {
    }

    public void apptDeleteBtnClick(ActionEvent actionEvent) {
    }

    public void exitBtnClick(ActionEvent actionEvent) {
        JDBC.closeConnection();
        System.exit(0);
    }
}
