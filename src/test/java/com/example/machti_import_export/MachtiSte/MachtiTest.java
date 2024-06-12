package com.example.machti_import_export.MachtiSte;

import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MachtiTest {

    @Test
    void testAjouterFournisseur() throws SQLException {
        Machti m = new Machti();
        Fournisseur f = new Fournisseur("Abde9a lkenas","Quartier Andalouss","0729056721");
        assertTrue(m.ajouterFournisseur(f));
    }

    @Test
    void testAjouterClient() throws SQLException {
        Machti m = new Machti();
        Client c = new Client("Hiba Salmi","Hay Riad","09999999","Oujda","Maroc");
        assertTrue(m.ajouterClient(c));
    }

    @Test
    void testAjouterProduit() throws SQLException {
        Machti m = new Machti();
        Produit p = new Produit("Machine de test2","Machine",1800,150);
        assertTrue(m.ajouterProduit(p,4));
    }




    @Test
    void testSupprimerProduit() throws SQLException {
        Machti m = new Machti();
        Produit p = new Produit(9,"","",500,500);
        System.out.println("Nombre de produit avant la suppression ( dans la liste ) : " +m.getnbProduit());
        assertTrue(m.supprimerProduit(p));
        System.out.println("Apres la suppression : " +m.getnbProduit());
    }

    @Test
    void testSupprimerFournisseur() throws SQLException {
        Machti m = new Machti();
        Fournisseur f = new Fournisseur(7,"","","");
        assertTrue(m.supprimerFournisseur(f));
    }

    @Test
    void testSupprimerClient() throws SQLException {
        Machti m = new Machti();
        Client c = new Client(4,"","","","","");
        assertTrue(m.supprimerClient(c));
    }

}