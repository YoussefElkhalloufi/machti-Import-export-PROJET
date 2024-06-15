package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Fournisseur;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.MachtiSte.Produit;
import com.example.machti_import_export.SwitchWindows;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerFournisseur {

    @FXML
    private TableView<Fournisseur> fournisseurTableView;
    @FXML
    private TableColumn<Fournisseur, Integer> idFournisseur;
    @FXML
    private TableColumn<Fournisseur, String> nom;
    @FXML
    private TableColumn<Fournisseur, String> adresse;
    @FXML
    private TableColumn<Fournisseur, String> telephone;

    @FXML
    private TextField nomFournisseur;
    @FXML
    private TextField nomFournisseurAfficher;
    @FXML
    private TextField telFournisseur;
    @FXML
    private TextField adresseFournisseur;



    @FXML
    private Button supprimerButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button afficherButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button precedentButton;


    SwitchWindows sw = new SwitchWindows();
    public Machti m = ControllerIndex.m;
    Alerts alert = new Alerts();

    @FXML
    public void initialize() throws SQLException {
        // Set up the columns with PropertyValueFactory
        idFournisseur.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Load data and set to TableView
        ObservableList<Fournisseur> data = FXCollections.observableArrayList(m.getFournisseurs());
        fournisseurTableView.setItems(data);

        fournisseurTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                afficherFournisseurSelectionner();
            }
        });
    }

    private void afficherFournisseurSelectionner() {
        Fournisseur selectedFournisseur = fournisseurTableView.getSelectionModel().getSelectedItem();
        if (selectedFournisseur != null) {
            nomFournisseur.setText(selectedFournisseur.getNom());
            adresseFournisseur.setText(selectedFournisseur.getAdresse());
            telFournisseur.setText(selectedFournisseur.getTelephone());
        }
    }

    @FXML
    void afficherFournisseur(ActionEvent event) {
        String nomF = nomFournisseurAfficher.getText().trim();

        Fournisseur f = m.afficherFournisseur(nomF);

        if (f != null && fournisseurTableView.getItems().contains(f)) {
            fournisseurTableView.getSelectionModel().select(f);
        }else{
            alert.showWarning("Attention","Aucun responsable avec ce nom");
        }
    }


    @FXML
    void ajouterFournisseur(ActionEvent event) throws SQLException {
        String nomF = nomFournisseur.getText().trim();
        String adresseF = adresseFournisseur.getText().trim();
        String telF = telFournisseur.getText().trim();

        if(nomF.isEmpty()) {
            alert.showWarning("Attention","Assurez-vous de remplir le nom du fournisseur .");
        }else{
            Fournisseur f = new Fournisseur(nomF, adresseF, telF);
            if(m.ajouterFournisseur(f)){
                alert.showAlert("Ajout avec succes","Le fournisseur a été ajouté avec succès","/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Ajout erroné","Une erreur s'est produite lors de l'ajout du fournisseur, veuillez réessayer plus tard.","/images/annuler.png");
            }
        }

    }

    @FXML
    void supprimerFournisseur(ActionEvent event) throws SQLException {
        Fournisseur selectedFournisseur = fournisseurTableView.getSelectionModel().getSelectedItem();

        if(selectedFournisseur != null) {
            if(alert.showConfirmationAlert("Confirmation","Voullez vous vraiment supprimer le fournisseur " + selectedFournisseur.getNom() +" ?")){
                if(m.supprimerFournisseur(selectedFournisseur)){
                    alert.showAlert("Suppression réussie","Le responsable a été supprimé avec succès","/images/checked.png");
                    initialize();
                }else{
                    alert.showAlert("Suppression échoué","Une erreur s'est produite lors de la suppression, veuillez réessayer plus tard .","/images/annuler.png");
                }
            }
        }else{
            alert.showWarning("Attention","Veuillez selectionner un fournisseur avant de proceder a la suppression.");
        }
    }


    @FXML
    void modifierFournisseur(ActionEvent event) throws SQLException {
        String nomF = nomFournisseur.getText().trim();
        String adresseF = adresseFournisseur.getText().trim();
        String telF = telFournisseur.getText().trim();


        Fournisseur selectedFournisseur = fournisseurTableView.getSelectionModel().getSelectedItem() ;
        if(selectedFournisseur == null){
            alert.showWarning("Attention","Veuillez sélectionner un fournisseur d'après le tableau et modifier ses informations avant de procéder à la modification.");
        }else{
            if(m.mofifierFournisseur(selectedFournisseur.getId(), nomF, adresseF, telF)){
                alert.showAlert("Modification avec succès","Modification des informations du fournisseur avec succès .","/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Erreur","Modification erroné, veuillez réessayer plus tard","/images/annuler.png");
            }
        }
    }


    public static void enlargeButton(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.play();
    }

    public static void restoreButton(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }
    public static void onMouseEntered(Button button) {
        button.setStyle("-fx-background-color : #D4D4D4; -fx-background-radius: 12;");
        enlargeButton(button);
    }

    public static void onMouseExited(Button button) {
        button.setStyle("-fx-background-color : white; -fx-background-radius: 12;");
        restoreButton(button);
    }

    @FXML
    void onMouseEnteredAjouter(MouseEvent event) {
        onMouseEntered(ajouterButton);
    }
    @FXML
    void onMouseEnteredSupprimer(MouseEvent event) {
        onMouseEntered(supprimerButton);
    }
    @FXML
    void onMouseEnteredAfficher(MouseEvent event) {
        onMouseEntered(afficherButton);
    }
    @FXML
    void onMouseEnteredModifier(MouseEvent event) {
        onMouseEntered(modifierButton);
    }
    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        onMouseEntered(precedentButton);
    }

    @FXML
    void onMouseExitedAjouter(MouseEvent event) {
        onMouseExited(ajouterButton);
    }
    @FXML
    void onMouseExitedSupprimer(MouseEvent event) {
        onMouseExited(supprimerButton);
    }
    @FXML
    void onMouseExitedAfficher(MouseEvent event) {
       onMouseExited(afficherButton);
    }
    @FXML
    void onMouseExitedModifier(MouseEvent event) {
        onMouseExited(modifierButton);
    }
    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        onMouseExited(precedentButton);
    }

    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event, "Index.fxml");
    }
}
