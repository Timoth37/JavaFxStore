package com.example.javafxstore;

public class Accessories extends Product {


    public Accessories(String name, double price, int nbItems){
        super(name, price, nbItems);
    }

    @Override
    public String toString() {
        return "Accessories{" +
                "number= " + this.getNumber() +
                ", name= '" + this.getName() + '\'' +
                ", price= " + this.getPrice() +
                "€, nbItems= " + this.getNbItems() +
                '}';
    }

    @Override
    public void applyDiscount() {
        super.setPrice(Math.round(super.getPrice()*0.7*100.0)/100.0);
    }
}
