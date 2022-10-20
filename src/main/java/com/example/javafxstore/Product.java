package com.example.javafxstore;

public abstract class Product implements Discount{
    static int count =0;
    private int number;
    private String name;
    private double sellingPrice;
    private double purchasingPrice;
    private int nbItems;

    static double income =0;
    static double outcome =0;

    private String category = "undefined";

    public static double getIncome() {
        return Math.round(income*100.0)/100.0;
    }

    public class CustomizedException extends Exception {
        //we can have attributes
        String message ;
        public CustomizedException(String message) {
            this.message=message;
        }
        @Override
        public String getMessage(){
            return "Error:"+this.message;
        }

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double price) {
        try {
            if(price<0){
                throw new CustomizedException("Negative price");
            }
            this.sellingPrice=price;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public double getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(double purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        try {
            if(nbItems<0){
                throw new CustomizedException("Negative number of items");
            }
            this.nbItems=nbItems;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Product(String name, String category,double sellingPrice, double purchasingPrice, int nbItems){
        this.name=name;
        this.category= category;
        try {
            if(purchasingPrice<0){
                throw new CustomizedException("Negative price");
            }
            this.purchasingPrice=purchasingPrice;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try {
            if(sellingPrice<0){
                throw new CustomizedException("Negative price");
            }
            this.sellingPrice=sellingPrice;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        try {
            if(nbItems<0){
                throw new CustomizedException("Negative number of items");
            }
            this.nbItems=nbItems;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.number = ++count;
    }


    @Override
    public String toString() {
        return number + " '" +
                name + "' " +
                sellingPrice + "€ "+
                purchasingPrice+"€ "+
                nbItems;
    }

    public void sell(int nbItems){
        try {
            if((this.nbItems-nbItems)<0){
                throw new CustomizedException("Product unavailable");
            }
            this.nbItems -= nbItems;
            income += sellingPrice*nbItems;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void purchase(int nbItems){
        this.nbItems += nbItems;
        outcome += purchasingPrice*nbItems;
    }

}
