package com.example.javafxstore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StoreController {

    @FXML
    private Button closeButton;

    @FXML
    private ListView listInv;

    @FXML
    private ComboBox<String> categorieInv;

    @FXML
    private ChoiceBox<String> categorieAchatVente;

    @FXML
    private ChoiceBox<String> categorieDiscount;

    @FXML
    private ComboBox<String> categorieAdd;

    @FXML
    private ComboBox<String> tailleModif;

    @FXML
    private ComboBox<String> tailleListe;

    //Menu Inventaire
    @FXML
    private void onOpenInv(){
        categorieInv.setItems(FXCollections.observableArrayList("Vetements","Chaussures","Accesoires"));
        categorieAdd.setItems(FXCollections.observableArrayList("Vetements","Chaussures","Accesoires"));
        tailleListe.setItems(FXCollections.observableArrayList("34","36","38","40","42","44","46","48","50","52","54"));
    }


    @FXML
    private void onSelectCatInv(){

        //Clear ListView

        String selectedCat = categorieInv.getSelectionModel().getSelectedItem();
        //Récupère la catégorie sélectionnée
        if(selectedCat=="Accesoires"){
            tailleModif.setDisable(true);
        }
        else {
            tailleModif.setDisable(false);
        }

        //Reste à faire une requete SQL pour récupérer tous les éléments de la catégorie et les ajouter
    }

    @FXML
    private void onSelectCatAdd(){

        //Clear ListView

        String selectedCat = categorieAdd.getSelectionModel().getSelectedItem();
        //Récupère la catégorie sélectionnée

        //Retire l'option taille pour les accessoires
        if(selectedCat=="Accesoires"){
            tailleListe.setDisable(true);
        }
        else {
            tailleListe.setDisable(false);
        }
    }


    //Menu Achat Vente
    @FXML
    private void onOpenAchatVente(){
        categorieAchatVente.setItems(FXCollections.observableArrayList("Vetements","Chaussures","Accesoires"));
    }

    //Menu Discount
    @FXML
    private void onOpenDiscount(){
        categorieDiscount.setItems(FXCollections.observableArrayList("Vetements","Chaussures","Accesoires"));
    }

    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}