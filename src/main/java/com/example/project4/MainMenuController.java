package com.example.project4;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class MainMenuController {



    @FXML
    private Button BuildYourOwnButton, SpecialtyPizzasButton, StoreOrdersButton, CurrentOrdersButton;

    @FXML
    private TextArea Title;



    @FXML
    private void OpenBuildYourOwn() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource( "BuildYourOwn.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Build Your Own");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    @FXML
    private void OpenSpecialtyPizzas() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpecialtyPizzas.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Specialty Pizzas");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }



    @FXML
    private void OpenStoreOrders() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreOrders.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Store Orders");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();

        }


    }

    @FXML
    private void OpenCurrentOrder() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CurrentOrder.fxml"));
            Parent root = loader.load();


            CurrentOrderController controller = loader.getController();

            controller.refresh();

            Stage stage = new Stage();
            stage.setTitle("Current Order");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
















}
