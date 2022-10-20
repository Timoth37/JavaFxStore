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
                Product s= new Product(myRs.getInt("id"),myRs.getString("name"), myRs.getString("price"));
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
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/store?serverTimezone=Europe%2FParis", "root","root");
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

    public void addProduct(Product product){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            String sql = "INSERT INTO producttable (name,price) VALUES (?, ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, product.getName());
            myStmt.setString(2, Double.toString(product.getPrice()));
            myStmt.execute();
            System.out.println("test1");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
}
