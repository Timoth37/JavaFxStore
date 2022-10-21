package com.example.javafxstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable{
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPurchasingPrice;
    @FXML
    private TextField txtSellingPrice;
    @FXML
    private Button btnClose;
    @FXML
    private ComboBox cmbSizeAdd;

    @FXML
    private ComboBox cmbSizeModification;

    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ListView lvProduct;

    @FXML
    private TextField txtQuantityToChange;

    DBManager manager;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DBManager();
        List<String> cValues = new ArrayList<String>();
        cValues.add("Clothes");
        cValues.add("Shoes");
        cValues.add("Accessories");
        cmbCategory.setItems(FXCollections.observableArrayList(cValues));
        cmbCategory.setValue("Clothes");
        onCategoryAction();


        lvProduct.getSelectionModel().selectedItemProperty().addListener(e->
                displayProductDetails((Product) lvProduct.getSelectionModel().getSelectedItem()));
    }

    public void onCategoryAction(){
        String selectedCat = (String) cmbCategory.getValue();
        //Get the selected category
        if(selectedCat=="Accessories"){
            cmbSizeModification.setDisable(true);
            cmbSizeAdd.setDisable(true);
        } else if(selectedCat=="Shoes"){
            cmbSizeModification.setDisable(false);
            cmbSizeAdd.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =36; i<=50 ; i++){sValues.add(i);}
            cmbSizeAdd.setItems(FXCollections.observableArrayList(sValues));
            cmbSizeAdd.setValue(36);
            cmbSizeModification.setItems(FXCollections.observableArrayList(sValues));
            cmbSizeModification.setValue(36);
        } else{
            cmbSizeModification.setDisable(false);
            cmbSizeAdd.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =34; i<=54 ; i+=2){sValues.add(i);}
            cmbSizeAdd.setItems(FXCollections.observableArrayList(sValues));
            cmbSizeAdd.setValue(34);
            cmbSizeModification.setItems(FXCollections.observableArrayList(sValues));
            cmbSizeModification.setValue(34);
        }
    }

    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct!=null){
            txtName.setText(selectedProduct.getName());
            txtPurchasingPrice.setText(Double.toString(selectedProduct.getPurchasingPrice()));
            txtSellingPrice.setText(Double.toString(selectedProduct.getSellingPrice()));
            cmbCategory.setValue(selectedProduct.getCategory());
        }
    }

    public void onAddClick() {
        if(((String)cmbCategory.getValue())=="Clothes"){
            Product p= new Clothes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0,
                    (Integer) cmbSizeAdd.getValue());
            manager.addProduct(p,(Integer)cmbSizeAdd.getValue());
        }
        else if(cmbCategory.getValue()=="Shoes"){
            Product s= new Shoes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0,
                    (Integer) cmbSizeAdd.getValue());
            manager.addProduct(s,(Integer)cmbSizeAdd.getValue());
        } else{
            Product a= new Accessories(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0);
            manager.addProduct(a,0);
        }
        fetchProducts();
    }

    public void fetchProducts() {
        List<Product> listProducts = manager.loadProduct();
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

}