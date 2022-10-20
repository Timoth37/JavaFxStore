package com.example.javafxstore;

public class Clothes extends Product {
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int nbItems) {
        try {
            if(size>54 || size<34 || size%2!=0){
                throw new CustomizedException("Wrong size");
            }
            this.size=size;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Clothes(String name, double price, int nbItems, int size){
        super(name, price, nbItems);
        try {
            if(size>54 || size<34 || size%2!=0){
                throw new CustomizedException("Wrong size");
            }
            this.size=size;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Clothes{" +
                "number= " + this.getNumber() +
                ", name= '" + this.getName() + '\'' +
                ", price= " + this.getPrice() +
                "€, nbItems= " + this.getNbItems() +
                ", size= " + size +
                '}';
    }

    @Override
    public void applyDiscount() {
        super.setPrice(Math.round(super.getPrice()*0.7*100.0)/100.0);
    }
}
