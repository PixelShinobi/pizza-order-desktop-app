package com.example.project4;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;


public class StoreOrdersController {

    @FXML
    private ComboBox<String> OrderNumber;

    @FXML
    private TextField OrderTotal;

    @FXML
    private ListView OrderList;

    @FXML
    private Button CancelOrderButton, ExportStoreOrdersButton;



    public void initialize() {
        refresh();
        OrderNumber.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateSelectedOrderDisplay());
    }

    public void refresh() {
        updateOrderList();
        updateOrderNumbers();

    }



    private void updateOrderList() {
        StoreOrders storeOrders = StoreOrders.getInstance();
        ObservableList<String> orderDescriptions = FXCollections.observableArrayList();
        for (Order order : storeOrders.getOrders()) {

            orderDescriptions.add("Order ID: " + order.getOrderId());
        }
        OrderList.setItems(orderDescriptions);
    }



    private void updateOrderNumbers() {
        StoreOrders storeOrders = StoreOrders.getInstance();
        ObservableList<String> orderNumbers = FXCollections.observableArrayList();
        for (Order order : storeOrders.getOrders()) {
            orderNumbers.add(String.valueOf(order.getOrderId()));
        }
        OrderNumber.setItems(orderNumbers);
    }


    private void updateSelectedOrderDisplay() {
        String selectedOrderId = OrderNumber.getValue();
        if (selectedOrderId != null) {
            int orderId = Integer.parseInt(selectedOrderId);
            StoreOrders storeOrders = StoreOrders.getInstance();
            Order selectedOrder = storeOrders.getOrderById(orderId);
            if (selectedOrder != null) {
                updateOrderDetails(selectedOrder);
            }
        }
    }



    private void updateOrderDetails(Order order) {
        ObservableList<String> pizzaDescriptions = FXCollections.observableArrayList();
        for (Pizza pizza : order.getPizzas()) {
            pizzaDescriptions.add(pizza.getDescription());
        }
        OrderList.setItems(pizzaDescriptions);
        OrderTotal.setText(String.format("$%.2f", order.getTotalPrice()));
    }



    @FXML
    private void handleCancelOrder() {
        String selectedOrderId = OrderNumber.getValue();
        if (selectedOrderId != null) {
            int orderId = Integer.parseInt(selectedOrderId);
            StoreOrders storeOrders = StoreOrders.getInstance();
            storeOrders.removeOrderById(orderId);
            updateOrderNumbers();
        } else {

        }
    }

    @FXML
    private void handleExportStoreOrders() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Store Orders");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) OrderList.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportStoreOrders(file);
    }


}
    private void exportStoreOrders(File file) {
        StoreOrders storeOrders = StoreOrders.getInstance();
        storeOrders.export(file);
    }





}



