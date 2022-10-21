package com.example.javafxstore;

public class Accessories extends Product {


    public Accessories(String name, double sellingPrice,double purchasingPrice, int nbItems){
        super(name, "Accessory", sellingPrice,purchasingPrice, nbItems);
    }
    public Accessories(int number,String name, double sellingPrice,double purchasingPrice, int nbItems){
        super(name, "Accessory", sellingPrice,purchasingPrice, nbItems);
        this.setNumber(number);
    }

    @Override
    public String toString() {
        return this.getNumber() + " | " +
                this.getName() + " | " +
                this.getNbItems()+" | "+
                this.getCategory();
    }

    @Override
    public void applyDiscount() {
        super.setSellingPrice(Math.round(super.getSellingPrice()*0.7*100.0)/100.0);
    }
}
