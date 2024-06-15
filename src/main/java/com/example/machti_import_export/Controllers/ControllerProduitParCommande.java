package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.MachtiSte.Machti;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ControllerProduitParCommande {


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text commandeLabel;
    @FXML
    private Button imprimerButton;
    @FXML
    private TableView<Object[]> prooduitParCommandeTableview;

    private int idCommande = ControllerCommande.idCommande ;
    Machti m = ControllerIndex.m ;

    public void initialize(){
        commandeLabel.setText("Commande N°: " + idCommande);

        if(idCommande != 0){
            m.remplirTableview(m.getProduitParCommande(idCommande), prooduitParCommandeTableview);
        }

        Platform.runLater(() -> {
            double centerX = (anchorPane.getWidth() - commandeLabel.getLayoutBounds().getWidth()) / 2;
            AnchorPane.setLeftAnchor(commandeLabel, centerX);
        });
    }
    @FXML
    void imprimer(ActionEvent event) {
        if(idCommande != 0){
            System.out.println("id est : " + idCommande);
        }
    }

    @FXML
    void onMouseEnteredImprimer(MouseEvent event) {

    }

    @FXML
    void onMouseExitedImprimer(MouseEvent event) {

    }

}
