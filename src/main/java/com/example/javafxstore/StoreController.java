package com.example.javafxstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable{
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPurchasingPrice;
    @FXML
    private TextField txtSellingPrice;
    @FXML
    private Button btnClose;
    @FXML
    private ComboBox cmbSize;
    @FXML
    private ComboBox<String> cmbCategoryInv;
    @FXML
    private ListView lvProduct;
    @FXML
    private ListView lvDiscount;

    @FXML
    private ComboBox cmbCategoryDiscount;

    @FXML
    private TextField txtQuantityToChange;
    @FXML
    private Text txtIncome;
    @FXML
    private Text txtCost;

    @FXML
    private Text txtIncome;

    @FXML
    private Text txtCost;

    DBManager manager;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DBManager();
        List<String> cValues = new ArrayList<String>();
        cValues.add("Clothes");
        cValues.add("Shoes");
        cValues.add("Accessories");
        cmbCategoryInv.setItems(FXCollections.observableArrayList(cValues));
        cmbCategoryInv.setValue("Clothes");
        onCategoryAction();
        lvProduct.getSelectionModel().selectedItemProperty().addListener(e->
                displayProductDetails((Product) lvProduct.getSelectionModel().getSelectedItem(),40));
        fetchProducts("clothe");
    }

    public void onCategoryAction(){
        String selectedCat = (String) cmbCategoryInv.getValue();
        //Get the selected category
        if(selectedCat=="Accessories"){
            cmbSize.setDisable(true);
            fetchProducts("accessory");
        } else if(selectedCat=="Shoes"){
            cmbSize.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =36; i<=50 ; i++){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(36);
            fetchProducts("shoe");
        } else{
            cmbSize.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =34; i<=54 ; i+=2){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(34);
            fetchProducts("clothe");
        }
    }

    private void displayProductDetails(Product selectedProduct, int size) {
        if(selectedProduct!=null){
            txtName.setText(selectedProduct.getName());
            txtPurchasingPrice.setText(Double.toString(selectedProduct.getPurchasingPrice()));
            txtSellingPrice.setText(Double.toString(selectedProduct.getSellingPrice()));
            if(selectedProduct.getCategory()!= "accessory"){
                cmbSize.setValue(size);
            }
        }
    }

    public void onAddClick() {
        if(((String)cmbCategoryInv.getValue())=="Clothes"){
            Product p= new Clothes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0,
                    (Integer) cmbSize.getValue());
            manager.addProduct(p,(Integer)cmbSize.getValue());
            fetchProducts("clothe");
        }
        else if(cmbCategoryInv.getValue()=="Shoes"){
            Product s= new Shoes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0,
                    (Integer) cmbSize.getValue());
            manager.addProduct(s,(Integer)cmbSize.getValue());
            fetchProducts("shoe");

        } else{
            Product a= new Accessories(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0);
            manager.addProduct(a,0);
            fetchProducts("accessory");
        }
    }

    public void onModifyClick(){
        if(cmbCategoryInv.getValue()=="Clothes"){
            Clothes clothe = (Clothes) lvProduct.getSelectionModel().getSelectedItem();
            displayProductDetails(clothe, clothe.getSize());
            Clothes newClothe = new Clothes(clothe.getNumber(),txtName.getText(),Double.parseDouble(txtPurchasingPrice.getText()),Double.parseDouble(txtSellingPrice.getText()),clothe.getNbItems(), (Integer) cmbSize.getValue());
            manager.modifyProduct(clothe, clothe.getSize());
            fetchProducts("clothe");

        }else if(cmbCategoryInv.getValue()=="Shoes"){
            Shoes shoe = (Shoes) lvProduct.getSelectionModel().getSelectedItem();
            displayProductDetails(shoe, shoe.getShoeSize());
            manager.modifyProduct(shoe, shoe.getShoeSize());
            fetchProducts("shoe");

        } else{
            Accessories accessory = (Accessories) lvProduct.getSelectionModel().getSelectedItem();
            displayProductDetails(accessory, 0);
            manager.modifyProduct(accessory, 0);
            fetchProducts("accessory");
        }
    }

    public void onDeleteClick(){

    }

    public void fetchProducts(String category) {
        List<Product> listProducts = manager.loadProduct(category);
        if (listProducts != null) {
            ObservableList<Product> products;
            products= FXCollections.observableArrayList(listProducts);
            lvProduct.setItems(products);

        }
    }

    public void onPurchaseAction(){
        Product purchasedProduct = (Product) lvProduct.getSelectionModel().getSelectedItem();
        purchasedProduct.setNbItems(purchasedProduct.getNbItems()+Integer.parseInt(txtQuantityToChange.getText()));
        manager.purchaseProduct(purchasedProduct);
    }

    public void onSellAction(){
        Product soldProduct = (Product) lvProduct.getSelectionModel().getSelectedItem();
    }



    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) btnClose.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void onOpenEconomy(){
        Double income = manager.loadIncome();
        Double cost = manager.loadCost();
        txtIncome.setText(income.toString()+" euros");
        txtCost.setText(cost.toString()+" euros");
    }

}