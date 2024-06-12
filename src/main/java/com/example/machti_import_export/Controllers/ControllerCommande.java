package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ControllerCommande {

    @FXML
    private TableView<Object[]> commandeTableview;

    @FXML
    private Button precedentButton;
    SwitchWindows sw = new SwitchWindows();
    Machti m = ControllerIndex.m ;

    public void initialize(){
        m.remplirTableview(m.getCommande(), commandeTableview);
    }


    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }

    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event, "Index.fxml");
    }

}
