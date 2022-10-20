package com.example.javafxstore;

public abstract class Product implements Discount{
    static int count =0;
    private int number;
    private String name;
    private double price;
    private int nbItems;
    static double income =0;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        try {
            if(price<0){
                throw new CustomizedException("Negative price");
            }
            this.price=price;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
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
    public Product(String name, double price, int nbItems){
        this.name=name;
        try {
            if(price<0){
                throw new CustomizedException("Negative price");
            }
            this.price=price;
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
        return "Product{" +
                "number= " + number +
                ", name= '" + name + '\'' +
                ", price= " + price +
                "€, nbItems= " + nbItems +
                '}';
    }

    public void sell(int nbItems){
        try {
            if((this.nbItems-nbItems)<0){
                throw new CustomizedException("Product unavailable");
            }
            this.nbItems -= nbItems;
            income += price*nbItems;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void purchase(int nbItems){
        this.nbItems += nbItems;
    }

}
