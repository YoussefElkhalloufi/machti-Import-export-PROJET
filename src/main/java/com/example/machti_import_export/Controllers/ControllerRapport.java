package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.SwitchWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerRapport {

    @FXML
    private Button precedentButton;
    @FXML
    private AnchorPane rapportParClient;
    @FXML
    private AnchorPane rapportParProduit;
    SwitchWindows sw = new SwitchWindows();


    @FXML
    void onMouseEnteredClient(MouseEvent event) {
        ControllerIndex.onMouseEntered(rapportParClient);
    }
    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }
    @FXML
    void onMouseEnteredProduit(MouseEvent event) {
        ControllerIndex.onMouseEntered(rapportParProduit);
    }

    @FXML
    void onMouseExitedClient(MouseEvent event) {
        ControllerIndex.onMouseExited(rapportParClient);
    }
    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
    }
    @FXML
    void onMouseExitedProduit(MouseEvent event) {
        ControllerIndex.onMouseExited(rapportParProduit);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event, "Index.fxml");
    }

    @FXML
    void switchToRapportParClient(MouseEvent event) {

    }

    @FXML
    void switchToRapportParProduit(MouseEvent event) throws IOException {
        sw.switchWindowPane(event, "rapportParProduit.fxml");
    }

}
