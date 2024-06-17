package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Client;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerRapportVenteClient {

    @FXML
    private TableView<Object[]> clientCommandeTableview;

    @FXML
    private Button genererButton;

    @FXML
    private Button precedentButton;

    Machti m = ControllerIndex.m;
    Alerts alert = new Alerts();

    public void initialize() {
        m.remplirTableview(m.getCommandeParClient(),clientCommandeTableview);
    }


    @FXML
    void genererRapport(ActionEvent event) throws SQLException, FileNotFoundException {

        Object[] client = clientCommandeTableview.getSelectionModel().getSelectedItem();

        if(client == null){
            alert.showWarning("Attention","Veuillez selectionner un client avant de proceder");
        }else{
            int idClient = (int) client[0];
            Client clt = null;
            for(Client c : m.getClients()){
                if(c.getId() == idClient){
                    clt = c ;
                }
            }
            if(clt == null){
                alert.showWarning("Erreur","Une erreur s'est produite lors de la generation du rapport, veuillez réessayer plus tard");
                return ;
            }

            String nomClient = clt.getNom().replace(" ", "_");

            String nomFichier = "Rapport_Client("+nomClient+").pdf" ;
            PDFgeneration.rapportParClient(nomFichier,clt,m.getCommandeParClient(idClient));
        }
    }

    @FXML
    void onMouseEnteredGenerer(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(genererButton);
    }

    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }

    @FXML
    void onMouseExitedGenerer(MouseEvent event) {
        ControllerFournisseur.onMouseExited(genererButton);
    }

    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
    }

    @FXML
    void switchToRapport(ActionEvent event) throws IOException {
        SwitchWindows sw = new SwitchWindows();
        sw.switchWindow(event, "Rapport.fxml");
    }

}
