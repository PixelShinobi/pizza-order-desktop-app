package com.example.project4;

import java.util.ArrayList;

public abstract class Pizza {
    protected ArrayList<Topping> toppings;
    protected Size size;
    protected Sauce sauce;
    protected boolean extraSauce;
    protected boolean extraCheese;

    public abstract double price();



    public Pizza() {
        this.toppings = new ArrayList<>();
    }

    public String getDescription() {

        String description = "";
        description += "["+this.getClass().getSimpleName() +"]"+ " ";

        description += this.toppings + " ";
        description += this.sauce + " ";
        if (this.extraSauce) {
            description += "extra sauce ";
        }
        if (this.extraCheese) {
            description += "extra cheese ";
        }
        //price
        description += String.format("$%.2f", this.price());
        return description;
    }
}


enum Topping {
    SAUSAGE,
    PEPPERONI,
    GREEN_PEPPER,
    ONION,
    MUSHROOM,
    SHRIMP,
    SQUID,
    CRAB_MEATS,
    BEEF,
    HAM,
    BLACK_OLIVE,
    CHICKEN,
    BACON,


}


enum Size {
    SMALL,
    MEDIUM,
    LARGE,

}


enum Sauce {
    TOMATO,
    ALFREDO,

}
