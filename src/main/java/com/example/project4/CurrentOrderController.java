package com.example.project4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CurrentOrderController {

    @FXML
    private TextField OrderNumber,subtotal, salesTax, orderTotal;

    @FXML
    private ListView PizzaOrdersList;

    @FXML
    private Button RemovePizzaButton, PlaceOrderButton;



    public void initialize() {
        updatePizzaOrdersList();
    }


    private void updatePizzaOrdersList() {
        Order currentOrder = Order.getInstance();
        ArrayList<Pizza> pizzas = currentOrder.getPizzas();

        ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
        for (Pizza pizza : pizzas) {

            pizzaDescriptions.add(pizza.getDescription());
        }

        PizzaOrdersList.setItems(pizzaDescriptions);
        updateOrderSummary();
    }

    private void updateOrderSummary() {
        Order currentOrder = Order.getInstance();
        double subtotalValue = currentOrder.getTotalPrice();
        double tax = subtotalValue * 0.06625;
        double total = subtotalValue + tax;

        subtotal.setText(String.format("$%.2f", subtotalValue));
        salesTax.setText(String.format("$%.2f", tax));
        orderTotal.setText(String.format("$%.2f", total));
        OrderNumber.setText(String.valueOf(currentOrder.getOrderId()));
    }




    public void refresh() {
        updatePizzaOrdersList();
    }



    @FXML
    private void handleRemovePizza() {
        int selectedIndex = PizzaOrdersList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Order currentOrder = Order.getInstance();
            currentOrder.getPizzas().remove(selectedIndex);
            updatePizzaOrdersList();
        } else {

        }
    }

    @FXML
    private void handlePlaceOrder() {

        StoreOrders storeOrders = StoreOrders.getInstance();
        Order currentOrder = Order.getInstance();

        if (!currentOrder.getPizzas().isEmpty()) {
            storeOrders.addOrder(currentOrder);

            Order.resetInstance();
            updatePizzaOrdersList();

        } else {

        }
    }





}
