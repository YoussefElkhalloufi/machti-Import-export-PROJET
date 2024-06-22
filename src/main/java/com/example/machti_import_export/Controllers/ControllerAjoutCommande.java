package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Client;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.MachtiSte.Produit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerAjoutCommande {

    @FXML
    private Button ajouterButton;
    @FXML
    private Button actualiserButton;

    @FXML
    private TableView<Client> clientTableview;
    @FXML
    private TableColumn<Client, Integer> idClient;
    @FXML
    private TableColumn<Client, String> nomClient;


    @FXML
    private TextField idCommande;

    @FXML
    private Text libelleProduit;

    @FXML
    private Text puProduit;

    @FXML
    private TextField qteProduit;

    @FXML
    private ComboBox<Integer> refProduitCMB;
    @FXML
    private Text erreurMsgN_cmd;

    Alerts alert = new Alerts();
    Machti m = ControllerIndex.m;
    private Produit produitSelectionne = null ;;

    ArrayList<Produit> produits ;

    public ControllerAjoutCommande() throws SQLException {
    }

    public void initialize() throws SQLException {
        produits = m.getProduits();

        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomClient.setCellValueFactory(new PropertyValueFactory<>("nom"));

        ObservableList<Client> data = FXCollections.observableArrayList(m.getClients());
        clientTableview.setItems(data);

        if(refProduitCMB.getItems() != null){
            refProduitCMB.getItems().clear();
        }


        for(Produit p : produits){
            refProduitCMB.getItems().add(p.getRefProduit());
        }
        refProduitCMB.getSelectionModel().select(0);
        setProduitInfos();
    }
    @FXML
    void setProduitInfos() {
        int idProduit = refProduitCMB.getSelectionModel().getSelectedItem();


        for(Produit p1 : produits){
            if(p1.getRefProduit() == idProduit){
                produitSelectionne = p1;
            }
        }
        libelleProduit.setText(produitSelectionne.getLibelleProduit());
        puProduit.setText(String.valueOf(produitSelectionne.getPrixUnitaire()));
    }

    @FXML
    void checkQuantite(ActionEvent event) {
        int qteDemande = Integer.parseInt(qteProduit.getText());

        if(qteDemande > produitSelectionne.getStock()){
            alert.showAlert("Quantité non disponible","La quantité choisi est non disponible au stock .","/images/annuler.png");
            qteProduit.setText("");
        }
    }

    @FXML
    void ajouterCommande(ActionEvent event) throws SQLException {


        Client clientSelectionne = clientTableview.getSelectionModel().getSelectedItem();

        int refProduit = refProduitCMB.getSelectionModel().getSelectedItem();

        if(idCommande.getText().isEmpty() || clientSelectionne == null || qteProduit.getText().isEmpty()){
            alert.showWarning("Attention","Veuillez selectionner un client, specifier le numero de la commande et les informations du produit avant de proceder .");
        }else{
            if(!idCommande.getText().matches("[1-9][0-9]*")){ // int sauf 00, 000 ...
                erreurMsgN_cmd.setVisible(true);
                idCommande.setText("");
                return ;
            }
            if(!qteProduit.getText().matches("[1-9][0-9]*")){
                alert.showWarning("Attention","La quantité doit être un nombre.");
                return ;
            }
            int idCmd = Integer.parseInt(idCommande.getText());
            int idClt = clientSelectionne.getId();
            int qte = Integer.parseInt(qteProduit.getText());

            //si le produit existe deja dans la commande
            if(m.getProduitParCommande(idCmd, refProduit).next()){
                alert.showWarning("Attention","Produit " + libelleProduit.getText() +" déjà existant dans la Commande N°: " +idCmd);
                return ;
            }

            //Verification de l'existante de la commande au client
            ResultSet rs = m.commandeExists(idCmd);
            if(rs.next()){
              int idClientCommande = rs.getInt(2) ;
              ResultSet rsCmd = m.clientCommandeExists(idCmd, idClt); //si la commande est associé a un client different a celui selecionné
              if(!rsCmd.next()){
                  alert.showWarning("Attention","Cette commande est associé a un autre Client, son ID est : " +idClientCommande);
                  return ;
              }
            }


            if(m.ajouterCommande(idCmd, idClt, refProduit, qte)){
                alert.showAlert("Ajout avec succès","Produit " + libelleProduit.getText() +" ajouté avec succeès a la commande N° : " +idCmd,"/images/checked.png");
            }else{
                alert.showAlert("Ajout errone","Une erreur s'est produite lors de l'ajout du produit a la commande","/images/annuler.png");
            }
        }
    }

    @FXML
    void onMouseEnteredAjouter(MouseEvent event) {
        ajouterButton.setStyle("-fx-background-color : white; -fx-background-radius: 12;");
        ControllerFournisseur.enlargeButton(ajouterButton);
    }

    @FXML
    void onMouseExitedAjouter(MouseEvent event) {
        ajouterButton.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 12;");
        ControllerFournisseur.restoreButton(ajouterButton);
    }
    @FXML
    void onMouseEnteredActualiser(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(actualiserButton);
    }

    @FXML
    void onMouseExitedActualiser(MouseEvent event) {
        ControllerFournisseur.onMouseExited(actualiserButton);
    }

}
