package com.example.project4;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SpecialtyPizzasController {


    @FXML
    private ComboBox<String> PizzaType;

    @FXML
    private ImageView PizzaPic;



    @FXML
    private RadioButton SmallButton, MediumButton, LargeButton;

    @FXML
    private TextField SauceOutput, PriceOutput;

    @FXML
    private CheckBox extraSauceBox, extraCheeseBox;

    @FXML
    private Button AddToOrderButton;

    @FXML
    private ListView ToppingsList;


    private ToggleGroup sizeToggleGroup = new ToggleGroup();


    Image Deluxe = new Image(getClass().getResourceAsStream("/com/example/project4/images/Deluxe.png"));
    Image Supreme = new Image(getClass().getResourceAsStream("/com/example/project4/images/Supreme.png"));
    Image Meatzza = new Image(getClass().getResourceAsStream("/com/example/project4/images/Meatzza.png"));
    Image Seafood = new Image(getClass().getResourceAsStream("/com/example/project4/images/Seafood.png"));
    Image Pepperoni = new Image(getClass().getResourceAsStream("/com/example/project4/images/Pepperoni.png"));



    public void initialize() {
        SmallButton.setToggleGroup(sizeToggleGroup);
        MediumButton.setToggleGroup(sizeToggleGroup);
        LargeButton.setToggleGroup(sizeToggleGroup);


        ObservableList<String> options = FXCollections.observableArrayList(
                "Deluxe", "Supreme", "Meatzza" , "Seafood" ,  "Pepperoni"
        );
        PizzaType.setItems(options);


        PizzaType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateImageView(newValue);
            updateToppingsList(newValue);
            updatePriceDisplay();
        });

        sizeToggleGroup.selectedToggleProperty().addListener((observableValue, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updatePriceDisplay();
            }
        });

        extraSauceBox.setOnAction(event -> updatePriceDisplay());
        extraCheeseBox.setOnAction(event -> updatePriceDisplay());
    }


    private void updateImageView(String selectedOption) {

        switch (selectedOption) {
            case "Deluxe":
                PizzaPic.setImage(Deluxe);
                break;
            case "Supreme":
                PizzaPic.setImage(Supreme);
                break;
            case "Meatzza":
                PizzaPic.setImage(Meatzza);
                break;
            case "Seafood":
                PizzaPic.setImage(Seafood);
                break;
            case "Pepperoni":
                PizzaPic.setImage(Pepperoni);
                break;
        }
    }

    private void updateToppingsList(String selectedPizzaType) {
        List<String> toppings = getToppingsForPizza(selectedPizzaType);
        ToppingsList.setItems(FXCollections.observableArrayList(toppings));
        updateSauceDisplay(selectedPizzaType);
    }


    private List<String> getToppingsForPizza(String pizzaType){

        List<String> toppings = new ArrayList<>();
        switch (pizzaType) {
            case "Deluxe":

                toppings = Arrays.asList( "Sausage", "Pepperoni", "Green Pepper", "Onion", "Mushroom");
                break;
            case "Supreme":

                toppings = Arrays.asList( "Sausage", "Pepperoni", "Ham", "Green Pepper", "Onion", "Black Olive", "Mushroom");
                break;
            case "Meatzza":

                toppings = Arrays.asList( "Sausage", "Pepperoni", "Beef", "Ham");
                break;
            case "Seafood":

                toppings = Arrays.asList( "Shrimp", "Squid", "Crab Meats");
                break;
            case "Pepperoni":

                toppings = Arrays.asList( "Pepperoni");
                break;
        }
        return toppings;
    }


    private void updateSauceDisplay(String selectedPizzaType) {
        String sauce = "";
        switch (selectedPizzaType) {
            case "Deluxe":
            case "Supreme":
            case "Meatzza":
            case "Pepperoni":
                sauce = "Tomato";
                break;
            case "Seafood":
                sauce = "Alfredo";
                break;
        }
        SauceOutput.setText(sauce);
    }



    private double calculatePizzaBasePrice(String selectedPizzaType, String selectedSize) {
        double basePrice;
        switch (selectedPizzaType) {
            case "Deluxe":
                basePrice = 14.99;
                break;
            case "Supreme":
                basePrice = 15.99;
                break;
            case "Meatzza":
                basePrice = 16.99;
                break;
            case "Seafood":
                basePrice = 17.99;
                break;
            case "Pepperoni":
                basePrice = 10.99;
                break;
            default:
                throw new IllegalArgumentException("Unknown pizza type: " + selectedPizzaType);
        }


        if ("Medium".equals(selectedSize)) {
            basePrice += 2;
        } else if ("Large".equals(selectedSize)) {
            basePrice += 4;
        }

        return basePrice;
    }

    private void updatePriceDisplay() {
        String selectedPizzaType = PizzaType.getValue();
        String selectedSize = SmallButton.isSelected() ? "Small" : MediumButton.isSelected() ? "Medium" : "Large";
        double price = calculatePizzaBasePrice(selectedPizzaType, selectedSize);


        if (extraSauceBox.isSelected()) {
            price += 1;
        }
        if (extraCheeseBox.isSelected()) {
            price += 1;
        }

        PriceOutput.setText(String.format("$%.2f", price));
    }


    @FXML
    private void AddToOrder() {
        String selectedPizzaType = PizzaType.getValue();

        Size selectedSize = Size.SMALL;
        if (MediumButton.isSelected()) {
            selectedSize = Size.MEDIUM;
        } else if (LargeButton.isSelected()) {
            selectedSize = Size.LARGE;
        }

        Topping[] toppings = new Topping[ToppingsList.getItems().size()];
        for (int i = 0; i < ToppingsList.getItems().size(); i++) {
            String topping = (String) ToppingsList.getItems().get(i);
            toppings[i] = Topping.valueOf(topping.toUpperCase().replace(" ", "_"));
        }


        Sauce sauce = Sauce.TOMATO;
        if ("Seafood".equals(selectedPizzaType)) {
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
        alert.setContentText("Pizza has been added to your order!");
        alert.showAndWait();

    }

}







