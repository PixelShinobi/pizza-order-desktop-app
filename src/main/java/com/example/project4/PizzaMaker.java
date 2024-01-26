package com.example.project4;


public class PizzaMaker {
    public static Pizza createPizza(String pizzaType, Size size, Topping[] toppings, Sauce sauce, boolean extraSauce, boolean extraCheese) {
        switch (pizzaType) {
            case "Deluxe":
                return new Deluxe(size, toppings, sauce, extraSauce, extraCheese);
            case "Supreme":
                return new Supreme(size, toppings, sauce, extraSauce, extraCheese);
            case "Meatzza":
                return new Meatzza(size, toppings, sauce, extraSauce, extraCheese);
            case "Pepperoni":
                return new Pepperoni(size, toppings, sauce, extraSauce, extraCheese);
            case "Seafood":
                return new Seafood(size, toppings, sauce, extraSauce, extraCheese);
            case "BuildYourOwn":
                return new BuildYourOwn(size, toppings, sauce, extraSauce, extraCheese);
            default:
                throw new IllegalArgumentException("Unknown pizza type: " + pizzaType);
        }
    }
}
