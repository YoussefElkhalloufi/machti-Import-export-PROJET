package com.example.machti_import_export.MachtiSte;

public class Client extends Fournisseur{
    private String  ville, pays ;


    public Client(int id, String nom, String adresse,String telephone, String ville, String pays) {
        super(id, nom, adresse, telephone);
        this.ville = ville;
        this.pays = pays;
    }

    public Client(String nom, String adresse, String telephone, String ville, String pays) {
        super(nom, adresse, telephone);
        this.ville = ville;
        this.pays = pays;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setPays(String pays) {this.pays = pays;}



    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }
}
