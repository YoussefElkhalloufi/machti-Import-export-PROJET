package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Fournisseur;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.MachtiSte.Produit;
import com.example.machti_import_export.SwitchWindows;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerProduit {

    @FXML
    private TableView<Produit> produitTableview;
    @FXML
    private TableColumn<Produit, Integer> ref;
    @FXML
    private TableColumn<Produit, Integer> stock;
    @FXML
    private TableColumn<Produit, String> libelle;
    @FXML
    private TableColumn<Produit, String> type;
    @FXML
    private TableColumn<Produit, Float> prixUnitaire;


    @FXML
    private TextField libelleProduit;
    @FXML
    private TextField libelleProduitAfficher;
    @FXML
    private TextField prixUnitaireProduit;
    @FXML
    private TextField stockProduit;
    @FXML
    private ComboBox<String> typeProduitCmb;
    @FXML
    private ComboBox<Integer> fournisseur;


    @FXML
    private Button afficherButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button precedentButton;
    @FXML
    private Button supprimerButton;


    SwitchWindows sw = new SwitchWindows();
    public Machti m = ControllerIndex.m;
    Alerts alert = new Alerts();

    @FXML
    public void initialize() throws SQLException {
        ArrayList<Produit> produits = m.getProduits();

        // Set up the columns with PropertyValueFactory
        ref.setCellValueFactory(new PropertyValueFactory<>("refProduit"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelleProduit"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeProduit"));
        prixUnitaire.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));


        // Load data and set to TableView
        ObservableList<Produit> dataTableview = FXCollections.observableArrayList(produits);
        produitTableview.setItems(dataTableview);

        fournisseur.getItems().addAll(getIdfournisseurs(m.getFournisseurs()));

        typeProduitCmb.getItems().add("Produit agricole");
        typeProduitCmb.getItems().add("Machine");

        produitTableview.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                afficherProduitSelectionner();
            }
        });

        for(Produit p : m.getProduits()) {
            if(p.getStock() <= 10){
                alert.showWarning("Attention","Le stock est bas, il ne reste que "+p.getStock()+" articles du produit " +p.getLibelleProduit());
            }
        }
    }

    private ArrayList<Integer> getIdfournisseurs(ArrayList<Fournisseur> fournisseurs) {
        ArrayList<Integer> idfournisseurs = new ArrayList<>();
        for(Fournisseur f : fournisseurs) {
            idfournisseurs.add(f.getId());
        }
        return idfournisseurs;
    }

    private void afficherProduitSelectionner() {
        Produit selectedProduit = produitTableview.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            libelleProduit.setText(selectedProduit.getLibelleProduit());
            prixUnitaireProduit.setText(String.valueOf(selectedProduit.getPrixUnitaire()));
            stockProduit.setText(String.valueOf(selectedProduit.getStock()));

            if(selectedProduit.getTypeProduit().equals("Produit agricole")) {
                typeProduitCmb.getSelectionModel().select("Produit agricole");
            }else{
                typeProduitCmb.getSelectionModel().select("Machine");
            }
        }
    }

    @FXML
    void afficherProduit(ActionEvent event) {
        String libellePr = libelleProduitAfficher.getText();

        //produit p = m.afficherProduit(libellePr);
        int indexPr = m.getIndexProduit(libellePr);
        if(indexPr == -1){
            alert.showWarning("Attention","Aucun produit avec ce libelle");
        }else{
            //System.out.println(produitTableview.getItems().contains(p));
            //int indexP = produitTableview.getItems().indexOf(p);
            produitTableview.getSelectionModel().select(indexPr);
            System.out.println("index of produit a afficher: "+indexPr);
        }
    }

    @FXML
    void ajouterProduit(ActionEvent event) throws SQLException {
        String libellePr = libelleProduit.getText().trim();
        String typePr = typeProduitCmb.getValue();

        int idFournisseur = 0 ;


        if(fournisseur.getValue() !=null){
            idFournisseur = fournisseur.getValue() ;
        }

        if(libellePr.isEmpty() || idFournisseur == 0){
            alert.showWarning("Attention","Assurez-vous de remplir le libelle, et le fournisseur du produit .");
        }else{

            if(!prixUnitaireProduit.getText().matches("\\d+(\\.\\d+)?")){
                alert.showWarning("Attention","Le prix unitaire est non valide.");
                return ;
            }

            if(!stockProduit.getText().matches("[1-9][0-9]*")){
                alert.showWarning("Attention","Le stock est non valide.");
                return ;
            }

            float prixUnitairePr = Float.parseFloat(prixUnitaireProduit.getText().trim());
            int stockPr = Integer.parseInt(stockProduit.getText().trim());

            Produit p = new Produit(libellePr,typePr,prixUnitairePr,stockPr) ;
            if(m.ajouterProduit(p, idFournisseur)){
                alert.showAlert("Ajout avec succes","Le produit a été ajouté avec succès","/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Ajout erroné","Une erreur s'est produite lors de l'ajout du produit, veuillez réessayer plus tard.","/images/annuler.png");
            }
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) throws SQLException {
        String libellePr = libelleProduit.getText().trim();
        String typePr = typeProduitCmb.getValue();



        Produit selectedProduit = produitTableview.getSelectionModel().getSelectedItem();

        if(selectedProduit == null){
            alert.showWarning("Attention","Veuillez sélectionner un produit d'après le tableau et modifier ses informations avant de procéder à la modification.");
        }else{

            if(!prixUnitaireProduit.getText().matches("\\d+(\\.\\d+)?")){
                alert.showWarning("Attention","Le prix unitaire est non valide.");
                return ;
            }

            if(!stockProduit.getText().matches("[1-9][0-9]*")){
                alert.showWarning("Attention","Le stock est non valide.");
                return ;
            }

            float prixUnitairePr = Float.parseFloat(prixUnitaireProduit.getText().trim());
            int stockPr = Integer.parseInt(stockProduit.getText().trim());

            if(m.modifierProduit(selectedProduit.getRefProduit(),libellePr,typePr,prixUnitairePr,stockPr)){
                alert.showAlert("Modification avec succès", "Modification des informations du prduit avec succès .", "/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Erreur","Modification erroné, veuillez réessayer plus tard","/images/annuler.png");
            }
        }
    }

    @FXML
    void supprimerProduit(ActionEvent event) throws SQLException {
        Produit selectedProduit = produitTableview.getSelectionModel().getSelectedItem();

        if(selectedProduit != null){
            if(alert.showConfirmationAlert("Confirmation","Voullez vous vraiment supprimer le produit " + selectedProduit.getLibelleProduit() +" ?")){
                if(m.supprimerProduit(selectedProduit)){
                    alert.showAlert("Suppression réussie","Le produit a été supprimé avec succès","/images/checked.png");
                    initialize();
                }else{
                    alert.showAlert("Suppression échoué","Une erreur s'est produite lors de la suppression, veuillez réessayer plus tard .","/images/annuler.png");
                }
            }
        }else{
            alert.showWarning("Attention","Veuillez selectionner un client avant de proceder a la suppression.");
        }
    }

    @FXML
    void onMouseEnteredAfficher(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(afficherButton);
    }
    @FXML
    void onMouseEnteredAjouter(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(ajouterButton);
    }
    @FXML
    void onMouseEnteredModifier(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(modifierButton);
    }
    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }
    @FXML
    void onMouseEnteredSupprimer(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(supprimerButton);
    }


    @FXML
    void onMouseExitedAfficher(MouseEvent event) {
        ControllerFournisseur.onMouseExited(afficherButton);
    }
    @FXML
    void onMouseExitedAjouter(MouseEvent event) {
        ControllerFournisseur.onMouseExited(ajouterButton);
    }
    @FXML
    void onMouseExitedModifier(MouseEvent event) {
        ControllerFournisseur.onMouseExited(modifierButton);
    }
    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
    }
    @FXML
    void onMouseExitedSupprimer(MouseEvent event) {
        ControllerFournisseur.onMouseExited(supprimerButton);
    }


    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event,"Index.fxml");
    }
}
