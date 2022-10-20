package com.example.javafxstore;

public class Shoes extends Product{
    private int shoeSize;

    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        try {
            if(shoeSize>50 || shoeSize<36){
                throw new Product.CustomizedException("Wrong Shoe size");
            }
            this.shoeSize=shoeSize;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Shoes(String name, double price, int nbItems, int shoeSize){
        super(name, price, nbItems);
        try {
            if(shoeSize>54 || shoeSize<34){
                throw new Product.CustomizedException("Wrong size");
            }
            this.shoeSize=shoeSize;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "number= " + this.getNumber() +
                ", name= '" + this.getName() + '\'' +
                ", price= " + this.getPrice() +
                "€, nbItems= " + this.getNbItems() +
                ", Shoe size= " + shoeSize +
                '}';
    }

    @Override
    public void applyDiscount() {
        super.setPrice(Math.round(super.getPrice()*0.7*100.0)/100.0);
    }
}
