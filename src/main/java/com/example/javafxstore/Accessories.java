package com.example.javafxstore;

public class Accessories extends Product {


    public Accessories(String name, double sellingPrice,double purchasingPrice, int discount, int nbItems){
        super(name, "Accessory", sellingPrice,purchasingPrice, discount, nbItems);
    }
    public Accessories(int number,String name, double sellingPrice,double purchasingPrice, int discount, int nbItems){
        super(name, "Accessory", sellingPrice,purchasingPrice, discount, nbItems);
        this.setNumber(number);
    }

    @Override
    public String toString() {
        return "ID : "+this.getNumber() + " | " +
                "Product : "+this.getName() + " | " +
                "Price : "+this.getSellingPrice() +" € | "+
                "Qty : "+this.getNbItems();
    }

    @Override
    public void applyDiscount() {
        super.setSellingPrice(Math.round(super.getSellingPrice()*0.7*100.0)/100.0);
    }
}
