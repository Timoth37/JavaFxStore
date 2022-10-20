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

    public Clothes(String name, double sellingPrice, double purchasingPrice, int nbItems, int size){
        super(name, "Clothe", sellingPrice, purchasingPrice, nbItems);
        try {
            if(size>54 || size<34 || size%2!=0){
                throw new CustomizedException("Wrong size");
            }
            this.size=size;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Clothes(int number, String name, double sellingPrice, double purchasingPrice, int nbItems, int size){
        super(name, "Clothe", sellingPrice, purchasingPrice, nbItems);
        try {
            if(size>54 || size<34 || size%2!=0){
                throw new CustomizedException("Wrong size");
            }
            this.size=size;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.setNumber(number);
    }

    @Override
    public String toString() {
        return this.getNumber() + " '" +
                this.getName() + "' " +
                this.getNbItems();
    }

    @Override
    public void applyDiscount() {
        super.setSellingPrice(Math.round(super.getSellingPrice()*0.7*100.0)/100.0);
    }
}
