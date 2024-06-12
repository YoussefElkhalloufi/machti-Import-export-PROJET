package com.example.machti_import_export.MachtiSte;

public class Produit {
    private int refProduit;
    private String libelleProduit, typeProduit;
    private float prixUnitaire ;
    private int stock ;


    public Produit(int refProduit, String libelleProduit, String typeProduit, float prixUnitaire, int stock) {
        this.refProduit = refProduit;
        this.libelleProduit = libelleProduit;
        this.typeProduit = typeProduit;
        this.prixUnitaire = prixUnitaire;
        this.stock = stock;
    }

    public Produit(String libelleProduit, String typeProduit, float prixUnitaire, int stock) {
        this.libelleProduit = libelleProduit;
        this.typeProduit = typeProduit;
        this.prixUnitaire = prixUnitaire;
        this.stock = stock;
    }

    public int getRefProduit() {
        return refProduit;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public int getStock() {
        return stock;
    }
}
