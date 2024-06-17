package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.MachtiSte.Client;
import com.example.machti_import_export.MachtiSte.Machti;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

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
    void imprimer(ActionEvent event) throws SQLException, FileNotFoundException {
        if(idCommande != 0){
            System.out.println("id est : " + idCommande);
        }

        Object[] commande = ControllerCommande.commande ;
        int idClient = (int) commande[1];
        Date dateCmd = (Date) commande[5];


        String date = dateCmd.toString();
        Client clt = null;
        for(Client c : m.getClients()){
            if(c.getId() == idClient){
                clt = c ;
            }
        }

        String nomFichier = "Commande_N°" +idCommande +".pdf";
        PDFgeneration.genererCommande(nomFichier,idCommande,clt,date,m.getProduitParCommande(idCommande));
    }

    @FXML
    void onMouseEnteredImprimer(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(imprimerButton);
    }

    @FXML
    void onMouseExitedImprimer(MouseEvent event) {
        ControllerFournisseur.onMouseExited(imprimerButton);
    }

}
