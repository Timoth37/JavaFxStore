package com.example.javafxstore;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DBManager {

    public List<Product> loadProduct(){
        List<Product> productAll= new ArrayList<Product>();
        Connection myConn= this.Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String sql = "select * from product";
            ResultSet myRs= myStmt.executeQuery(sql);
            while (myRs.next()) {
                Product s= new Clothes(myRs.getInt("id"),myRs.getString("name"), myRs.getDouble("sellingPrice"), myRs.getDouble("purchasingPrice"),myRs.getInt("nbItems"),38);
                productAll.add(s);
            }
            this.close(myConn, myStmt, myRs);
            return productAll;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
    public Connection Connector(){
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/store", "root","Samsam4321");
            return connection;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
        try{
            if(myStmt!=null)
                myStmt.close();
            if(myRs!=null)
                myRs.close();
            if(myConn!=null)
                myConn.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void addProduct(Product product, int size) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = this.Connector();
            String insertProduct = "INSERT INTO product (id,name,category,sellingPrice, purchasingPrice, nbItems) VALUES (?,?,?,?,?,?)";
            myStmt = myConn.prepareStatement(insertProduct);
            myStmt.setInt(1, product.getNumber());
            myStmt.setString(2, product.getName());
            myStmt.setString(3, product.getCategory());
            myStmt.setDouble(4, product.getSellingPrice());
            myStmt.setDouble(5, product.getPurchasingPrice());
            myStmt.setInt(6, product.getNbItems());
            myStmt.execute();
            myStmt.close();
            if (product.getCategory() == "Clothe") {
                String insertClothe = "INSERT INTO clothe (id, Csize) VALUES (?,?)";
                myStmt = myConn.prepareStatement(insertClothe);
                myStmt.setInt(1, product.getNumber());
                myStmt.setInt(2, size);
                myStmt.execute();
                myStmt.close();
            } else if (product.getCategory() == "Shoe") {
                String insertShoe = "INSERT INTO shoe (id, Ssize) VALUES (?,?)";
                myStmt = myConn.prepareStatement(insertShoe);
                myStmt.setInt(1, product.getNumber());
                myStmt.setInt(2, size);
                myStmt.execute();
                myStmt.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            close(myConn, myStmt, myRs);
        }
    }





    public void purchaseProduct(Product product){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;

        try{
            myConn = this.Connector();
            String insertProduct = "UPDATE product SET nbItems= ? WHERE id = ? ";
            myStmt = myConn.prepareStatement(insertProduct);
            myStmt.setInt(1, product.getNbItems());
            myStmt.setInt(2, product.getNumber());
            myStmt.execute();
            myStmt.close();
        }catch(Exception e){

        }
    }

    public void sellProduct(){

    }

    public Double loadIncome(){
        Double incomeValue= 0.0;
        Connection myConn= this.Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String income = "select sum(gain) from actions where gain>0;";
            ResultSet myRs= myStmt.executeQuery(income);
            while (myRs.next()) {
                incomeValue= myRs.getDouble("sum(gain)");
            }

            this.close(myConn, myStmt, myRs);
            return incomeValue;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double loadCost(){
        Double costValue= 0.0;
        Connection myConn= this.Connector();
        try {
            Statement myStmt= myConn.createStatement();
            String income = "select sum(gain) from actions where not gain>0;";
            ResultSet myRs= myStmt.executeQuery(income);
            while (myRs.next()) {
                costValue= myRs.getDouble("sum(gain)");
            }
            costValue *= -1;

            this.close(myConn, myStmt, myRs);
            return costValue;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
