package com.example.javafxstore;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
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
    private ComboBox<String> cmbCategoryPS;
    @FXML
    private ComboBox<String> cmbCategoryDiscount;
    @FXML
    private ListView lvProductInv;
    @FXML
    private ListView lvProductPS;
    @FXML
    private ListView lvProductDiscount;
    @FXML
    private TabPane tabMenu;
    @FXML
    private TextField txtQuantityToChange;
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
        cmbCategoryDiscount.setItems(FXCollections.observableArrayList(cValues));
        cmbCategoryPS.setItems(FXCollections.observableArrayList(cValues));
        cmbCategoryInv.setValue("Clothes");
        cmbCategoryDiscount.setValue("Clothes");
        cmbCategoryPS.setValue("Clothes");
        lvProductInv.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try{
                if (cmbCategoryInv.getValue() == "Clothes") {
                    Clothes clothe = (Clothes) newVal;
                    displayProductDetails(clothe, clothe.getSize());
                } else if (cmbCategoryInv.getValue() == "Accessories") {
                    Accessories accessory = (Accessories) newVal;
                    displayProductDetails(accessory, 0);
                } else if (cmbCategoryInv.getValue() == "Shoes") {
                    Shoes shoe = (Shoes) newVal;
                    displayProductDetails(shoe, shoe.getShoeSize());
                }
            }catch(Exception e){

            }
        });
        onCategoryActionInv();
        onCategoryActionDiscount();
        onCategoryActionPS();
    }

    public void onCategoryActionInv(){
        categoryChoice((String) cmbCategoryInv.getValue(), lvProductInv);
    }
    public void onCategoryActionPS(){
        categoryChoice((String) cmbCategoryPS.getValue(), lvProductPS);
    }
    public void onCategoryActionDiscount(){
        categoryChoice((String) cmbCategoryDiscount.getValue(), lvProductDiscount);
    }
    public void categoryChoice(String selectedCat, ListView listView){
        if(selectedCat=="Accessories"){
            cmbSize.setDisable(true);
            fetchProducts("Accessory", listView);
        } else if(selectedCat=="Shoes"){
            cmbSize.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =36; i<=50 ; i++){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(36);
            fetchProducts("Shoe", listView);
        } else{
            cmbSize.setDisable(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =34; i<=54 ; i+=2){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(34);
            fetchProducts("Clothe", listView);
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
            fetchProducts("Clothe", lvProductInv);
        }
        else if(cmbCategoryInv.getValue()=="Shoes"){
            Product s= new Shoes(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0,
                    (Integer) cmbSize.getValue());
            manager.addProduct(s,(Integer)cmbSize.getValue());
            fetchProducts("Shoe", lvProductInv);

        } else{
            Product a= new Accessories(txtName.getText(),
                    Double.parseDouble(txtSellingPrice.getText()),
                    Double.parseDouble(txtPurchasingPrice.getText()),
                    0);
            manager.addProduct(a,0);
            fetchProducts("Accessory", lvProductInv);
        }
    }

    public void onModifyClick(){
        try {
            if (cmbCategoryInv.getValue() == "Clothes") {
                Clothes clothe = (Clothes) lvProductInv.getSelectionModel().getSelectedItem();
                Clothes newClothe = new Clothes(clothe.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()), clothe.getNbItems(), (Integer) cmbSize.getValue());
                manager.modifyProduct(newClothe, newClothe.getSize());
                fetchProducts("Clothe", lvProductInv);

            } else if (cmbCategoryInv.getValue() == "Shoes") {
                Shoes shoe = (Shoes) lvProductInv.getSelectionModel().getSelectedItem();
                Shoes newShoe = new Shoes(shoe.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()), shoe.getNbItems(), (Integer) cmbSize.getValue());
                manager.modifyProduct(newShoe, newShoe.getShoeSize());
                fetchProducts("Shoe", lvProductInv);

            } else {
                Accessories accessory = (Accessories) lvProductInv.getSelectionModel().getSelectedItem();
                Accessories newAccessory = new Accessories(accessory.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()), accessory.getNbItems());
                manager.modifyProduct(newAccessory, 0);
                fetchProducts("Accessory", lvProductInv);
            }
        }catch(Exception e){

        }
    }

    public void onDeleteClick(){
        Product product = (Product) lvProductInv.getSelectionModel().getSelectedItem();
        manager.deleteProduct(product);
        fetchProducts(product.getCategory(), lvProductInv);
    }

    public void fetchProducts(String category, ListView lvProduct) {
        List<Product> listProducts = this.manager.loadProduct(category);
        if (listProducts != null) {
            ObservableList<Product> products;
            products= FXCollections.observableArrayList(listProducts);
            lvProduct.setItems(products);

        }
    }

    public void onPurchaseAction(){
        Product purchasedProduct = (Product) lvProductPS.getSelectionModel().getSelectedItem();
        manager.operationProduct(purchasedProduct, Double.parseDouble(txtQuantityToChange.getText()));
        fetchProducts(purchasedProduct.getCategory(), lvProductPS);
        System.out.println(purchasedProduct.getCategory());
    }

    public void onSellAction(){
        Product soldProduct = (Product) lvProductPS.getSelectionModel().getSelectedItem();
        manager.operationProduct(soldProduct,-Double.parseDouble(txtQuantityToChange.getText()));
        fetchProducts(soldProduct.getCategory(), lvProductPS);
        System.out.println(soldProduct.getCategory());
    }



    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onOpenInventory(){
        cmbCategoryInv.setValue("Clothes");
        fetchProducts("Clothe", lvProductInv);
    }
    @FXML
    private void onOpenPS(){
        cmbCategoryPS.setValue("Clothes");
        fetchProducts("Clothe",lvProductPS);
    }
    @FXML
    private void onOpenDiscount(){
        cmbCategoryDiscount.setValue("Clothes");
        fetchProducts("Clothe",lvProductDiscount);

    }
    @FXML
    private void onOpenEconomy(){
        Double income = manager.loadIncome();
        Double cost = manager.loadCost();
        txtIncome.setText(income.toString()+" euros");
        txtCost.setText(cost.toString()+" euros");
    }



}