package com.example.project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main class that runs the program
 * It is responsible for loading the main menu and starting the program
 * To navigate back to the main menu from any other page, simply close the current window
 *
 * @author Jizhou Yang
 */


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));


            primaryStage.setTitle("RU Pizza Ordering System");


            primaryStage.setScene(new Scene(root, 978, 607));


            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
