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

    public Shoes(String name, double sellingPrice, double purchasingPrice, int discount, int nbItems, int shoeSize){
        super(name, "Shoe" ,sellingPrice, purchasingPrice, discount, nbItems);
        try {
            if(shoeSize>54 || shoeSize<34){
                throw new Product.CustomizedException("Wrong size");
            }
            this.shoeSize=shoeSize;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Shoes(int number,String name, double sellingPrice, double purchasingPrice, int discount, int nbItems, int shoeSize){
        super(name, "Shoe" ,sellingPrice, purchasingPrice, discount, nbItems);
        try {
            if(shoeSize>54 || shoeSize<34){
                throw new Product.CustomizedException("Wrong size");
            }
            this.shoeSize=shoeSize;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.setNumber(number);
    }
    @Override
    public String toString() {
        return "ID : "+this.getNumber() + " | " +
                "Product : "+this.getName() + " | " +
                "Price : "+this.getSellingPrice() +" € | "+
                "Qty : "+this.getNbItems();}

    @Override
    public void applyDiscount() {
        super.setSellingPrice(Math.round(super.getSellingPrice()*0.7*100.0)/100.0);
    }
}
