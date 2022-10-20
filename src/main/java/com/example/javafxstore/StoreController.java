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
    private ComboBox cmbSizeModif;

    @FXML
    private ComboBox<String> cmbCategory;
    @FXML
    private ListView lvProduct;

    @FXML
    private TextField txtNbItems;

    DBManager manager;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = new DBManager();
        List<String> cValues = new ArrayList<String>();
        cValues.add("Clothes");
        cValues.add("Shoes");
        cValues.add("Accessories");
        cmbCategory.setItems(FXCollections.observableArrayList(cValues));
        cmbCategory.setValue("Clothes");
        List<Integer> sValues = new ArrayList<>();
        for(int i =34; i<=54 ; i= i+2){sValues.add(i);}
        cmbSizeAdd.setItems(FXCollections.observableArrayList(sValues));
        cmbSizeAdd.setValue(34);
        fetchProducts();

        lvProduct.getSelectionModel().selectedItemProperty().addListener(e->
                displayProductDetails((Product) lvProduct.getSelectionModel().getSelectedItem()));
    }

    private void onSelectCatInv(){

        //String selectedCat = cmbCategory.getSelectionModel().getSelectedItem();
        String selectedCat = (String) cmbCategory.getValue();
        //Récupère la catégorie sélectionnée
        if(selectedCat=="Accessories"){
            cmbSizeModif.setDisable(true);
        }
        else {
            cmbSizeModif.setDisable(false);
        }

        if(selectedCat=="Accessories"){
            cmbSizeAdd.setDisable(true);
        }
        else {
            cmbSizeAdd.setDisable(false);
        }

        //Reste à faire une requete SQL pour récupérer tous les éléments de la catégorie et les ajouter
    }

    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct!=null){
            txtName.setText(selectedProduct.getName());
            txtPurchasingPrice.setText(Double.toString(selectedProduct.getPurchasingPrice()));
            txtSellingPrice.setText(Double.toString(selectedProduct.getSellingPrice()));
            txtNbItems.setText(Integer.toString(selectedProduct.getNbItems()));
            cmbCategory.setValue(selectedProduct.getCategory());
        }
    }

    public void onAddClick() {
        if(((String)cmbCategory.getValue())=="Clothes"){
            Product p= new Clothes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    Integer.parseInt(txtNbItems.getText()),
                    (Integer) cmbSizeAdd.getValue());
            manager.addProduct(p,(Integer)cmbSizeAdd.getValue());
        }
        else if(cmbCategory.getValue()=="Shoes"){
            Product s= new Shoes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    Integer.parseInt(txtNbItems.getText()),
                    (Integer) cmbSizeAdd.getValue());
            manager.addProduct(s,(Integer)cmbSizeAdd.getValue());
        } else{
            Product a= new Accessories(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    Integer.parseInt(txtNbItems.getText()));
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

    public void onCategoryAction(){

    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) btnClose.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}