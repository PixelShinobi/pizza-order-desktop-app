package com.example.project4;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class StoreOrders {

    private static StoreOrders instance = new StoreOrders();
    private ArrayList<Order> orders;


    public StoreOrders() {

        orders = new ArrayList<>();
    }


    public void addOrder(Order order) {
        orders.add(order);
    }



    public static StoreOrders getInstance() {
        return instance;
    }


    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void removeOrderById(int orderId) {
        orders.removeIf(order -> order.getOrderId() == orderId);
    }

    public Order getOrderById(int orderId) {
        for (Order order : this.orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }



    public void export(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Order order : this.orders) {
                writer.println("Order ID: " + order.getOrderId());
                for (Pizza pizza : order.getPizzas()) {
                    writer.println("  - " + pizza.getDescription());
                }
                writer.println("Total Price: $" + order.getTotalPrice());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());

        }
    }




}
