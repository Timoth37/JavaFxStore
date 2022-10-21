package com.example.javafxstore;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class DBManager {

    public List<Product> loadProduct(String category){
        List<Product> productAll= new ArrayList<Product>();
        try {
            Connection myConn= this.Connector();
            Statement myStmt= myConn.createStatement();
            String sql = "select * from product NATURAL JOIN "+category+";";
            ResultSet myRs= myStmt.executeQuery(sql);
            while (myRs.next()) {
                Product s;
                if(category=="Clothe"){
                    s= new Clothes(myRs.getInt("id"),myRs.getString("name"), myRs.getDouble("sellingPrice"), myRs.getDouble("purchasingPrice"),myRs.getInt("nbItems"),myRs.getInt("csize"));
                }else if(category=="Shoe"){
                    s= new Shoes(myRs.getInt("id"),myRs.getString("name"), myRs.getDouble("sellingPrice"), myRs.getDouble("purchasingPrice"),myRs.getInt("nbItems"),myRs.getInt("ssize"));
                }else{
                    System.out.println("Oui je rentre");
                    s= new Accessories(myRs.getInt("id"),myRs.getString("name"), myRs.getDouble("sellingPrice"), myRs.getDouble("purchasingPrice"),myRs.getInt("nbItems"));
                }
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
    public void addProduct(Product product, int size){
        Connection myConn=null;
        PreparedStatement myStmt = null;
        ResultSet myRs= null;
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
            System.out.println(insertProduct);
            myStmt.execute();
            myStmt.close();
            if(product.getCategory()=="Clothe") {
                String insertClothe = "INSERT INTO clothe (id, Csize) VALUES (?,?)";
                myStmt = myConn.prepareStatement(insertClothe);
                myStmt.setInt(1, product.getNumber());
                myStmt.setInt(2, size);
                myStmt.execute();
                myStmt.close();
            }else if(product.getCategory()=="Shoe"){
                String insertShoe = "INSERT INTO shoe (id, Ssize) VALUES (?,?)";
                myStmt = myConn.prepareStatement(insertShoe);
                myStmt.setInt(1, product.getNumber());
                myStmt.setInt(2, size);
                myStmt.execute();
                myStmt.close();
            }else if(product.getCategory()=="Accessory"){
                System.out.println("la");
                String insertAccessory = "INSERT INTO accessory (id) VALUES (?)";
                myStmt = myConn.prepareStatement(insertAccessory);
                myStmt.setInt(1, product.getNumber());
                myStmt.execute();
                myStmt.close();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
    public void modifyProduct(Product product, int size){
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            myStmt = myConn.createStatement();
            String modifyProduct = "UPDATE product SET name='"+product.getName() +
                    "', purchasingPrice=" + product.getPurchasingPrice()+
                    ", sellingPrice= " + product.getSellingPrice()+
                    " WHERE id="+product.getNumber()+";";
            System.out.println(modifyProduct);
            myStmt.execute(modifyProduct);
            myStmt.close();
            if(product.getCategory()=="Clothe") {
                myStmt = myConn.createStatement();
                String modifyClothe = "UPDATE clothe SET Csize= " + size+
                        " WHERE id ="+product.getNumber();
                myStmt.execute(modifyClothe);
                myStmt.close();
            }else if(product.getCategory()=="Shoe"){
                myStmt = myConn.createStatement();
                String modifyShoe = "UPDATE shoe SET Ssize= "+ size+
                        " WHERE id ="+product.getNumber();
                myStmt.execute(modifyShoe);
                myStmt.close();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally{
            close(myConn,myStmt,myRs);
        }
    }
    public void deleteProduct(Product product){
        Connection myConn=null;
        Statement myStmt = null;
        ResultSet myRs= null;
        try {
            myConn = this.Connector();
            myStmt = myConn.createStatement();
            String modifyProduct = "DELETE FROM product WHERE id="+product.getNumber()+";";
            myStmt.execute(modifyProduct);
            myStmt.close();
        }catch(Exception e){

        }
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
    public void operationProduct(Product product, int diffItem){
        Connection myConn=null;
        Statement myStmt = null;
        try{
            myConn = this.Connector();
            myStmt = myConn.createStatement();
            String opeOnProduct = "UPDATE product SET nbItems=" +(product.getNbItems()+diffItem)+
                    " WHERE id ="+product.getNumber()+";";
            myStmt.execute(opeOnProduct);
            myStmt.close();
            myStmt = myConn.createStatement();
            String updateIncomesOutcomes;
            if(diffItem<0){
                updateIncomesOutcomes = "INSERT INTO actions(id,gain) VALUES(" +product.getNumber()+","+(-product.getSellingPrice()*diffItem)+");";
            }else{
                updateIncomesOutcomes = "INSERT INTO actions(id,gain) VALUES(" +product.getNumber()+","+(-product.getPurchasingPrice()*diffItem)+");";
            }
            myStmt.execute(updateIncomesOutcomes);
            myStmt.close();
        }catch(Exception e){

        }
    }

}
