package com.example.project4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class BuildYourOwnController {



    @FXML
    private RadioButton TomatoSauceButton, AlfredoSauceButton;

    @FXML
    private CheckBox extraSauceBox, extraCheeseBox;

    @FXML
    private ComboBox<String> PizzaSize;

    @FXML
    private ListView AdditionalToppingsList, SelectedToppingsList;

    @FXML
    private Button AddToppingButton, RemoveToppingButton, AddToOrderButton;

    @FXML
    private TextField PriceOutput;



    //initialize the pizza size combo box
    @FXML
    public void initialize() {



        AdditionalToppingsList.setItems(FXCollections.observableArrayList(
                "Sausage",
                "Pepperoni",
                "Green Pepper",
                "Onion",
                "Mushroom",
                "Shrimp",
                "Squid",
                "Crab Meats",
                "Beef",
                "Ham",
                "Black Olive",
                "Chicken",
                "Bacon"
        ));


        PizzaSize.setItems(FXCollections.observableArrayList("Small", "Medium", "Large"));
        PizzaSize.setValue("Small");


        PizzaSize.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updatePriceDisplay());
        extraSauceBox.setOnAction(event -> updatePriceDisplay());
        extraCheeseBox.setOnAction(event -> updatePriceDisplay());


        updatePriceDisplay();
    }


    private double calculatePrice() {
        double basePrice = 8.99;
        double toppingPrice = 1.49;


        String size = PizzaSize.getValue().toString();
        if ("Medium".equals(size)) {
            basePrice += 2;
        } else if ("Large".equals(size)) {
            basePrice += 4;
        }


        int additionalToppingsCount = SelectedToppingsList.getItems().size() - 3;
        if (additionalToppingsCount > 0) {
            basePrice += additionalToppingsCount * toppingPrice;
        }


        if (extraSauceBox.isSelected()) {
            basePrice += 1;
        }
        if (extraCheeseBox.isSelected()) {
            basePrice += 1;
        }

        return basePrice;
    }

    private void updatePriceDisplay() {
        double price = calculatePrice();
        PriceOutput.setText(String.format("$%.2f", price));
    }

    @FXML
    private void handleAddButtonAction() {


        if (SelectedToppingsList.getItems().size() >= 7) {

            showAlertToppingsLimit();
            return;
        }


        String selectedTopping = (String) AdditionalToppingsList.getSelectionModel().getSelectedItem();

        if (selectedTopping != null) {

            SelectedToppingsList.getItems().add(selectedTopping);

            AdditionalToppingsList.getItems().remove(selectedTopping);

            AdditionalToppingsList.getSelectionModel().clearSelection();
        }


        updatePriceDisplay();
    }


    private void showAlertToppingsLimit() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Maximum number of toppings");
        alert.setHeaderText(null);
        alert.setContentText("At most 7 toppings!");
        alert.showAndWait();
    }


    @FXML
    private void handleRemoveButtonAction() {

        String selectedTopping = (String) SelectedToppingsList.getSelectionModel().getSelectedItem();

        if (selectedTopping != null) {

            AdditionalToppingsList.getItems().add(selectedTopping);

            SelectedToppingsList.getItems().remove(selectedTopping);

            SelectedToppingsList.getSelectionModel().clearSelection();
        }


        updatePriceDisplay();
    }






    @FXML
    private void AddToOrder() {


        String selectedPizzaType = "BuildYourOwn";

        Size selectedSize = Size.SMALL;
        if (PizzaSize.getValue().equals("Medium")) {
            selectedSize = Size.MEDIUM;
        } else if (PizzaSize.getValue().equals("Large")) {
            selectedSize = Size.LARGE;
        }

        Topping[] toppings = new Topping[SelectedToppingsList.getItems().size()];
        for (int i = 0; i < SelectedToppingsList.getItems().size(); i++) {
            String topping = (String) SelectedToppingsList.getItems().get(i);
            toppings[i] = Topping.valueOf(topping.toUpperCase().replace(" ", "_"));
        }


        Sauce sauce = Sauce.TOMATO;
        if (AlfredoSauceButton.isSelected()) {
            sauce = Sauce.ALFREDO;
        }


        boolean extraSauce = extraSauceBox.isSelected();
        boolean extraCheese = extraCheeseBox.isSelected();


        Pizza pizza = PizzaMaker.createPizza(selectedPizzaType,selectedSize,toppings,sauce,extraSauce,extraCheese);
        Order order = Order.getInstance();
        order.addPizza(pizza);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pizza Order");
        alert.setHeaderText(null);
        alert.setContentText("Pizza has been added to the order!");
        alert.showAndWait();


    }




}
