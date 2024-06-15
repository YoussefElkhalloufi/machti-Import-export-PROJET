package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControllerRapportVenteProduit {

    @FXML
    private ComboBox<String> CmbType_par1;

    @FXML
    private ComboBox<String> CmbType_par2;

    @FXML
    private DatePicker dp_a_par1;

    @FXML
    private DatePicker dp_a_par2;

    @FXML
    private DatePicker dp_du_par1;

    @FXML
    private DatePicker dp_du_par2;

    @FXML
    private Button genererButton_par0;

    @FXML
    private Button genererButton_par1_PERIODE;

    @FXML
    private Button genererButton_par1_TYPE;

    @FXML
    private Button genererButton_par2;

    @FXML
    private Button precedentButton;

    Alerts alert = new Alerts();
    Machti m = ControllerIndex.m ;

    public void initialize(){
        CmbType_par1.getSelectionModel().select(0);
        CmbType_par2.getSelectionModel().select(0);
    }


    @FXML
    void genererRapport_par0(ActionEvent event) throws SQLException, FileNotFoundException {
        String fichier = "Rapport(Sans_période_ni_type)_" + LocalDate.now() +".pdf" ;
        PDFgeneration.rapportParProduit(fichier,null, null, m.getRapport_ni_periode_ni_type());
    }

    @FXML
    void genererRapport_par1_PERIODE(ActionEvent event) throws SQLException, FileNotFoundException {
        LocalDate du_date = dp_du_par1.getValue();
        LocalDate a_date = dp_a_par1.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        if(du_date == null || a_date == null){
            alert.showWarning("Attention","Veuillez specifier les dates");
            return ;
        }
        String fichier = "Rapport(par_période_sans_type)_" + LocalDate.now() +".pdf" ;
        String du_date_String = du_date.format(formatter);
        String a_date_String = a_date.format(formatter);

        if(!m.getRapport_par1_PERIODE(du_date_String, a_date_String).next()){
            alert.showWarning("Attention","Aucun produit vendu dans cette periode");
            return ;
        }
        PDFgeneration.rapportParProduit(fichier, du_date_String, a_date_String, m.getRapport_par1_PERIODE(du_date_String, a_date_String));
    }

    @FXML
    void genererRapport_par1_TYPE(ActionEvent event) {

    }

    @FXML
    void genererRapport_par2(ActionEvent event) {

    }






    @FXML
    void onMouseEnteredGenerer_par0(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(genererButton_par0);
    }
    @FXML
    void onMouseEnteredGenerer_par1_PERIODE(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(genererButton_par1_PERIODE);
    }
    @FXML
    void onMouseEnteredGenerer_par1_TYPE(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(genererButton_par1_TYPE);
    }
    @FXML
    void onMouseEnteredGenerer_par2(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(genererButton_par2);
    }
    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }




    @FXML
    void onMouseExitedGenerer_par0(MouseEvent event) {
        ControllerFournisseur.onMouseExited(genererButton_par0);
    }
    @FXML
    void onMouseExitedGenerer_par1_PERIODE(MouseEvent event) {
        ControllerFournisseur.onMouseExited(genererButton_par1_PERIODE);
    }
    @FXML
    void onMouseExitedGenerer_par1_TYPE(MouseEvent event) {
        ControllerFournisseur.onMouseExited(genererButton_par1_TYPE);
    }
    @FXML
    void onMouseExitedGenerer_par2(MouseEvent event) {
        ControllerFournisseur.onMouseExited(genererButton_par2);
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
