package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.Alerts;
import com.example.machti_import_export.MachtiSte.Client;
import com.example.machti_import_export.MachtiSte.Machti;
import com.example.machti_import_export.SwitchWindows;
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

import java.io.IOException;
import java.sql.SQLException;

public class ControllerClient {



    @FXML
    private TableView<Client> clientTableView;
    @FXML
    private TableColumn<Client, Integer> idClient;
    @FXML
    private TableColumn<Client, String> nom;
    @FXML
    private TableColumn<Client, String> adresse;
    @FXML
    private TableColumn<Client, String> telephone;
    @FXML
    private TableColumn<Client, String> ville;
    @FXML
    private TableColumn<Client, String> pays;


    @FXML
    private TextField nomClient;
    @FXML
    private TextField nomClientAfficher;
    @FXML
    private TextField adresseClient;
    @FXML
    private TextField paysClient;
    @FXML
    private TextField telClient;
    @FXML
    private TextField villeClient;



    @FXML
    private Button afficherButton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button modifierButton;
    @FXML
    private Button precedentButton;
    @FXML
    private Button supprimerButton;



    public Machti m = ControllerIndex.m;
    SwitchWindows sw = new SwitchWindows();
    Alerts alert = new Alerts();

    @FXML
    public void initialize() throws SQLException {
        // Set up the columns with PropertyValueFactory
        idClient.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
        pays.setCellValueFactory(new PropertyValueFactory<>("pays"));

        // Load data and set to TableView
        ObservableList<Client> data = FXCollections.observableArrayList(m.getClients());
        clientTableView.setItems(data);

        clientTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                afficherFournisseurSelectionner();
            }
        });
    }

    private void afficherFournisseurSelectionner() {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            nomClient.setText(selectedClient.getNom());
            adresseClient.setText(selectedClient.getAdresse());
            telClient.setText(selectedClient.getTelephone());
            villeClient.setText(selectedClient.getVille());
            paysClient.setText(selectedClient.getPays());
        }
    }


    @FXML
    void afficherClient(ActionEvent event) {
        String nomC = nomClientAfficher.getText().trim();

        Client c = m.afficherClient(nomC);

        if(c != null){
            clientTableView.getSelectionModel().select(c);
        }else{
            alert.showWarning("Attention","Aucun Client avec ce nom");
        }
    }

    @FXML
    void ajouterClient(ActionEvent event) throws SQLException {
        String nom = nomClient.getText().trim();
        String adresse = adresseClient.getText().trim();
        String tel = telClient.getText().trim();
        String ville = villeClient.getText().trim();
        String pays = paysClient.getText().trim();

        if(nom.isEmpty()){
            alert.showWarning("Attention","Assurez-vous de remplir le nom du Client .");
        }else{
            if(!tel.matches("\\d{10}")){
                alert.showWarning("Attention","La forme du numero de telephone n'est pas valide.\nEX : 0612129090.");
                return ;
            }
            Client c = new Client(nom, adresse, tel, ville, pays);
            if(m.ajouterClient(c)){
                alert.showAlert("Ajout avec succes","Le client a été ajouté avec succès","/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Ajout erroné","Une erreur s'est produite lors de l'ajout du client, veuillez réessayer plus tard.","/images/annuler.png");
            }
        }
    }

    @FXML
    void modifierClient(ActionEvent event) throws SQLException {
        String nom = nomClient.getText().trim();
        String adresse = adresseClient.getText().trim();
        String tel = telClient.getText().trim();
        String ville = villeClient.getText().trim();
        String pays = paysClient.getText().trim();

        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();

        if(selectedClient == null){
            alert.showWarning("Attention","Veuillez sélectionner un client d'après le tableau et modifier ses informations avant de procéder à la modification.");
        }else{
            if(m.modifierClient(selectedClient.getId(), nom, adresse, tel, ville, pays)) {
                alert.showAlert("Modification avec succès", "Modification des informations du client avec succès .", "/images/checked.png");
                initialize();
            }else{
                alert.showAlert("Erreur","Modification erroné, veuillez réessayer plus tard","/images/annuler.png");
            }
        }
    }

    @FXML
    void supprimerClient(ActionEvent event) throws SQLException {
        Client selectedClient = clientTableView.getSelectionModel().getSelectedItem();

        if(selectedClient != null){
            if(alert.showConfirmationAlert("Confirmation","Voullez vous vraiment supprimer le client " + selectedClient.getNom() +" ?")){
                if(m.supprimerClient(selectedClient)){
                    alert.showAlert("Suppression réussie","Le client a été supprimé avec succès","/images/checked.png");
                    initialize();
                }else{
                    alert.showAlert("Suppression échoué","Une erreur s'est produite lors de la suppression, veuillez réessayer plus tard .","/images/annuler.png");
                }
            }
        }else{
            alert.showWarning("Attention","Veuillez selectionner un client avant de proceder a la suppression.");
        }
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
    void onMouseEnteredModifier(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(modifierButton);
    }
    @FXML
    void onMouseEnteredPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(precedentButton);
    }
    @FXML
    void onMouseEnteredSupprimer(MouseEvent event) {
        ControllerFournisseur.onMouseEntered(supprimerButton);
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
    void onMouseExitedModifier(MouseEvent event) {
        ControllerFournisseur.onMouseExited(modifierButton);
    }
    @FXML
    void onMouseExitedPrecedent(MouseEvent event) {
        ControllerFournisseur.onMouseExited(precedentButton);
    }
    @FXML
    void onMouseExitedSupprimer(MouseEvent event) {
        ControllerFournisseur.onMouseExited(supprimerButton);
    }


    @FXML
    void switchToDashboard(ActionEvent event) throws IOException {
        sw.switchWindow(event, "Index.fxml");
    }

}
