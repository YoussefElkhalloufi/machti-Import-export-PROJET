package com.example.machti_import_export.Controller;


import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerIndex {

    @FXML
    private AnchorPane client;
    @FXML
    private AnchorPane commande;
    @FXML
    private AnchorPane fournisseur;
    @FXML
    private AnchorPane produits;
    @FXML
    private AnchorPane rapport;


    @FXML
    private Text nbrClient;
    @FXML
    private Text nbrFournisseur;
    @FXML
    private Text nbrProduit;



    SwitchWindows sw = new SwitchWindows();

    private static final double ENLARGE_FACTOR = 1.05;

    public static Machti m ;
    public void initialize() throws SQLException {
        m = new Machti();
        nbrFournisseur.setText(String.valueOf(m.getnbFournisseur()));
        nbrClient.setText(String.valueOf(m.getnbClient()));
        nbrProduit.setText(String.valueOf(m.getnbProduit()));
    }

    public void enlargeButton(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(ENLARGE_FACTOR);
        scaleTransition.setToY(ENLARGE_FACTOR);
        scaleTransition.play();
    }

    public void restoreButtonSize(AnchorPane anchor) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), anchor);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }


    private void onMouseEntered(AnchorPane anchor){
        anchor.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 25;");
        enlargeButton(anchor);
    }

    private void onMouseExited(AnchorPane anchor){
        anchor.setStyle("-fx-background-color : #EDEDED; -fx-background-radius: 25;");
        restoreButtonSize(anchor);
    }

    @FXML
    void onMouseEnteredClient(MouseEvent event) {
        onMouseEntered(client);
    }

    @FXML
    void onMouseEnteredCommande(MouseEvent event) {
        onMouseEntered(commande);
    }

    @FXML
    void onMouseEnteredFournisseur(MouseEvent event) {
        onMouseEntered(fournisseur);
    }

    @FXML
    void onMouseEnteredProduit(MouseEvent event) {
        onMouseEntered(produits);
    }

    @FXML
    void onMouseEnteredRapport(MouseEvent event) {
        onMouseEntered(rapport);
    }

    @FXML
    void onMouseExitedClient(MouseEvent event) {
        onMouseExited(client);
    }

    @FXML
    void onMouseExitedCommande(MouseEvent event) {
        onMouseExited(commande);
    }

    @FXML
    void onMouseExitedFournisseur(MouseEvent event) {
        onMouseExited(fournisseur);
    }

    @FXML
    void onMouseExitedProduit(MouseEvent event) {
        onMouseExited(produits);
    }

    @FXML
    void onMouseExitedRapport(MouseEvent event) {
        onMouseExited(rapport);
    }

    @FXML
    void switchToClient(MouseEvent event) throws IOException {
        sw.switchWindowPane(event, "gestionClient.fxml");
    }

    @FXML
    void switchToCommande(MouseEvent event) throws IOException {
        sw.switchWindowPane(event, "gestionCommande.fxml");
    }

    @FXML
    void switchToFournisseur(MouseEvent event) throws IOException {
        sw.switchWindowPane(event, "gestionFournisseur.fxml");
    }

    @FXML
    void switchToProduit(MouseEvent event) throws IOException {
        sw.switchWindowPane(event, "gestionProduit.fxml");
    }

    @FXML
    void switchToRapport(MouseEvent event) {

    }
}
