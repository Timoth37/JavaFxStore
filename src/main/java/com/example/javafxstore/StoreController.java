package com.example.javafxstore;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Alert.AlertType;

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
    @FXML
    private TextField txtDiscount;
    @FXML
    private CheckBox checkDiscountPS;
    @FXML
    private Text txtNewPrice;
    @FXML
    private Text txtOldPrice;
    @FXML
    DBManager manager;

    @FXML
    private Text txtProfit;
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
        lvProductDiscount.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            try{
                if (cmbCategoryDiscount.getValue() == "Clothes") {
                    Clothes clothe = (Clothes) newVal;
                    displayProductDetailsDiscount(clothe);
                } else if (cmbCategoryDiscount.getValue() == "Accessories") {
                    Accessories accessory = (Accessories) newVal;
                    displayProductDetailsDiscount(accessory);
                } else if (cmbCategoryDiscount.getValue() == "Shoes") {
                    Shoes shoe = (Shoes) newVal;
                    displayProductDetailsDiscount(shoe);
                }
            }catch(Exception e){

            }
        });
        cmbSize.setDisable(false);
        List<Integer> sValues = new ArrayList<>();
        for(int i =34; i<=54 ; i+=2){sValues.add(i);}
        cmbSize.setItems(FXCollections.observableArrayList(sValues));
        cmbSize.setValue(34);
        fetchProducts("Clothe", lvProductInv);
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
            checkDiscountPS.setSelected(false);
            txtNewPrice.setText("");
            txtOldPrice.setText("");
            fetchProducts("Accessory", listView);
        } else if(selectedCat=="Shoes"){
            cmbSize.setDisable(false);
            checkDiscountPS.setSelected(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =36; i<=50 ; i++){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(36);
            txtNewPrice.setText("");
            txtOldPrice.setText("");
            fetchProducts("Shoe", listView);
        } else{
            cmbSize.setDisable(false);
            checkDiscountPS.setSelected(false);
            List<Integer> sValues = new ArrayList<>();
            for(int i =34; i<=54 ; i+=2){sValues.add(i);}
            cmbSize.setItems(FXCollections.observableArrayList(sValues));
            cmbSize.setValue(34);
            txtNewPrice.setText("");
            txtOldPrice.setText("");
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
        try{
            if(Double.parseDouble(txtSellingPrice.getText())<0 || Double.parseDouble(txtPurchasingPrice.getText())<0)
                throw new Exception();
            if(((String)cmbCategoryInv.getValue())=="Clothes"){
                Product p= new Clothes(txtName.getText(),
                        Double.parseDouble(txtSellingPrice.getText()),
                        Double.parseDouble(txtPurchasingPrice.getText()),
                        0,
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
                        0,
                        (Integer) cmbSize.getValue());
                manager.addProduct(s,(Integer)cmbSize.getValue());
                fetchProducts("Shoe", lvProductInv);

            } else{
                Product a= new Accessories(txtName.getText(),
                        Double.parseDouble(txtSellingPrice.getText()),
                        Double.parseDouble(txtPurchasingPrice.getText()),
                        0,
                        0);
                manager.addProduct(a,0);
                fetchProducts("Accessory", lvProductInv);
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, verify that all your text fields are correctly completed.\n" +
                    "Name should be classic text\n"+
                    "Selling and purchasing price should be positive numbers.");
            alert.showAndWait();
        }

    }
    public void onModifyClick(){
        try {
            if(Double.parseDouble(txtSellingPrice.getText())<0 || Double.parseDouble(txtPurchasingPrice.getText())<0)
                throw new Exception();
            if (cmbCategoryInv.getValue() == "Clothes") {
                Clothes clothe = (Clothes) lvProductInv.getSelectionModel().getSelectedItem();
                Clothes newClothe = new Clothes(clothe.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()), clothe.getDiscount(), clothe.getNbItems(), (Integer) cmbSize.getValue());
                manager.modifyProduct(newClothe, newClothe.getSize());
                fetchProducts("Clothe", lvProductInv);

            } else if (cmbCategoryInv.getValue() == "Shoes") {
                Shoes shoe = (Shoes) lvProductInv.getSelectionModel().getSelectedItem();
                Shoes newShoe = new Shoes(shoe.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()), shoe.getDiscount(), shoe.getNbItems(), (Integer) cmbSize.getValue());
                manager.modifyProduct(newShoe, newShoe.getShoeSize());
                fetchProducts("Shoe", lvProductInv);

            } else {
                Accessories accessory = (Accessories) lvProductInv.getSelectionModel().getSelectedItem();
                Accessories newAccessory = new Accessories(accessory.getNumber(), txtName.getText(), Double.parseDouble(txtSellingPrice.getText()), Double.parseDouble(txtPurchasingPrice.getText()),accessory.getDiscount(), accessory.getNbItems());
                manager.modifyProduct(newAccessory, 0);
                fetchProducts("Accessory", lvProductInv);
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please, verify that you have selected an ite.\n"+
                    "If done, verify that all your text fields are correctly completed.\n" +
                    "Name should be classic text\n"+
                    "Selling and purchasing price should be positive numbers.");
            alert.showAndWait();
        }
    }
    public void onDeleteClick(){
        Product product = (Product) lvProductInv.getSelectionModel().getSelectedItem();
        try{
            if(product==null){ throw new Exception();}
            manager.deleteProduct(product);
            fetchProducts(product.getCategory(), lvProductInv);
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to delete");
            alert.showAndWait();
        }

    }
    public void fetchProducts(String category, ListView lvProduct) {
        manager = new DBManager();
        List<Product> listProducts = manager.loadProduct(category);
        if (listProducts != null) {
            ObservableList<Product> products;
            products= FXCollections.observableArrayList(listProducts);
            lvProduct.setItems(products);
        }
    }
    public void onPurchaseAction(){
        try{
            if(Double.parseDouble(txtQuantityToChange.getText())<0){
                throw new Exception();
            }
            Product purchasedProduct = (Product) lvProductPS.getSelectionModel().getSelectedItem();
            manager.operationProduct(purchasedProduct, Double.parseDouble(txtQuantityToChange.getText()), false);
            fetchProducts(purchasedProduct.getCategory(), lvProductPS);
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to purchase.\n" +
                    "If an item is selected, please enter a positive quantity to purchase");
            alert.showAndWait();
        }

    }
    public void onSellAction(){
        try {
            boolean discountOn = false;
            if(checkDiscountPS.isSelected()==true)
                discountOn = true;
            if (Double.parseDouble(txtQuantityToChange.getText()) < 0)
                throw new Exception();
            Product soldProduct = (Product) lvProductPS.getSelectionModel().getSelectedItem();
            if(Double.parseDouble(txtQuantityToChange.getText())> soldProduct.getNbItems())
                throw new Exception();
            manager.operationProduct(soldProduct,-Double.parseDouble(txtQuantityToChange.getText()), discountOn);
            fetchProducts(soldProduct.getCategory(), lvProductPS);
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to purchase.\n" +
                    "If an item is selected, please enter a positive quantity to sell.\n" +
                    "You cannot sell more items than you own ");
            alert.showAndWait();
        }
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
    private void onOpenDiscount() {
        cmbCategoryDiscount.setValue("Clothes");
        fetchProducts("Clothe", lvProductDiscount);

    }
    private void displayProductDetailsDiscount(Product selectedProduct) {
        if(selectedProduct!=null){
            int amount = manager.loadDiscount(selectedProduct.getNumber());
            txtDiscount.setText(Integer.toString(amount));
        }
    }
    @FXML
    public void onDiscountClick(){
        try {
            if (cmbCategoryDiscount.getValue() == "Clothes") {
                Clothes clothe = (Clothes) lvProductDiscount.getSelectionModel().getSelectedItem();
                manager.modifyDiscount(clothe, Integer.parseInt(txtDiscount.getText()));
                fetchProducts("Clothe", lvProductDiscount);
            } else if (cmbCategoryDiscount.getValue() == "Shoes") {
                Shoes shoe = (Shoes) lvProductDiscount.getSelectionModel().getSelectedItem();
                manager.modifyDiscount(shoe, Integer.parseInt(txtDiscount.getText()));
                fetchProducts("Shoe", lvProductDiscount);
            } else {
                Accessories accessory = (Accessories) lvProductDiscount.getSelectionModel().getSelectedItem();
                manager.modifyDiscount(accessory, Integer.parseInt(txtDiscount.getText()));
                fetchProducts("Accessory", lvProductDiscount);
            }
        }catch(Exception e){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to appluy a discount");
            alert.showAndWait();
        }
    }
    @FXML
    private void onOpenEconomy(){
        Double income = manager.loadIncome();
        Double cost = manager.loadCost();
        txtIncome.setText(income.toString()+" €");
        txtCost.setText(cost.toString()+" €");
        Double profit = income-cost;
        txtProfit.setText(profit.toString()+ " €");
        Color color;
        if(profit<0)
            color = Color.RED;
        else
            color = Color.GREEN;
        txtProfit.setFill(color);


    }
    @FXML
    private void onCheckDiscountPS(){
        if(checkDiscountPS.isSelected())
        {
            Product product = (Product) lvProductPS.getSelectionModel().getSelectedItem();
            if(product!=null && product.getDiscount()!=0){
                txtOldPrice.setText(Double.toString(product.getSellingPrice()));
                txtNewPrice.setText("--> "+Double.toString(product.getSellingPrice()*(1-product.getDiscount()/100.0)));
            }else if(product!=null){
                txtNewPrice.setText("No Discount");
            }
        }else{
            txtOldPrice.setText("");
            txtNewPrice.setText("");
        }

    }


}