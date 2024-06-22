package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class ControllerCommande {

    @FXML
    private TableView<Object[]> commandeTableview;

    @FXML
    private Button precedentButton;
    @FXML
    private Button afficherButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button actualiserButton;
    @FXML
    private Button afficherCommandeButton;
    @FXML
    private Button modifierButton;
    @FXML
    private TextField idCommandeRecherche;
    @FXML
    private Text erreurMsgN_cmd;
    @FXML
    private ComboBox<String> cmbEtatCmd;


    SwitchWindows sw = new SwitchWindows();
    Machti m = ControllerIndex.m ;

    public static int idCommande = 0;
    public static Object[] commande = null ; //La commande selecionné, 'STATIC' pour l'acceder depuis d'autre classe
    Alerts alert = new Alerts();


    public void initialize(){
        m.remplirTableview(m.getCommande(), commandeTableview);
        cmbEtatCmd.getSelectionModel().select(0);
    }

    @FXML
    void afficherProduitParFacture(ActionEvent event) throws IOException {
        commande = commandeTableview.getSelectionModel().getSelectedItem();

        if(commande != null){
            idCommande = Integer.parseInt(commande[0].toString());
            sw.switchWindowWithoutClosingTheMainOne(event, "produitParCommande.fxml");
        }else{
            alert.showWarning("Attention","Veuillez selectionner une commande avant de proceder.");
        }
    }

    @FXML
    void afficherCommande(ActionEvent event) {
        if(idCommandeRecherche.getText().isEmpty()){
            alert.showWarning("Attention","Veuillez spécifier le numero de la commande");
            erreurMsgN_cmd.setVisible(true);
        }else{
            if(idCommandeRecherche.getText().matches("[0-9]+")){
                int idCmd = Integer.parseInt(idCommandeRecherche.getText());
                m.remplirTableview(m.getCommande(idCmd), commandeTableview);
                erreurMsgN_cmd.setVisible(false);
            }else{
                alert.showWarning("Attention","Veuillez saisir un numero de commande valide");
            }

        }
    }
    @FXML
    void modifierCommande(ActionEvent event) {
        commande = commandeTableview.getSelectionModel().getSelectedItem();

        if(commande != null){
            idCommande = Integer.parseInt(commande[0].toString());
            if(m.modifierCommande(idCommande, cmbEtatCmd.getValue())){
                alert.showAlert("Modification","Modification effectue avec succès","/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Erreur","une erreur s'est produite lors de la modification de la commande","/images/annuler.png");
            }
        }else{
            alert.showWarning("Attention","Veuillez selectionner une commande avant de proceder.");
        }
    }


    @FXML
    void ajouterCommande(ActionEvent event) throws IOException {
        sw.switchWindowWithoutClosingTheMainOne(event, "ajoutCommande.fxml");
    }

    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
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
    void onMouseEnteredActualiser(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(actualiserButton);
    }
    @FXML
    void onMouseEnteredAfficherCmd(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(afficherCommandeButton);
    }
    @FXML
    void onMouseEnteredModifier(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(modifierButton);
    }



    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
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
    void onMouseExitedActualiser(MouseEvent event) {
        ControllerFournisseur.onMouseExited(actualiserButton);
    }
    @FXML
    void onMouseExitedAfficherCmd(MouseEvent event) {
        ControllerFournisseur.onMouseExited(afficherCommandeButton);
    }

    @FXML
    void onMouseExitedModifier(MouseEvent event) {
        ControllerFournisseur.onMouseExited(modifierButton);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event, "Index.fxml");
    }


}
