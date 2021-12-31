package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import utilities.JDBC;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.sqlCustomer.getAllCustomers;
import static utilities.sqlCustomer.getCustomers;

public class MainController implements Initializable {

    Stage stage;
    Parent scene;

    public static Customer customerSelected;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;



    @Override
    public void initialize (URL url, ResourceBundle rb){

        customerTableView.setItems(getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
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
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
