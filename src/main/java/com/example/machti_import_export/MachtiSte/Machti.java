package com.example.machti_import_export.MachtiSte;

import com.example.machti_import_export.Connexion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;

public class Machti {

    private Connection conn ;
    private Statement stmt ;
    private ResultSet rs ;


    private ArrayList<Client> clients ;
    private ArrayList<Fournisseur> fournisseurs ;
    private ArrayList<Produit> produits ;


    public Machti() throws SQLException {
        conn = Connexion.connect();
        stmt = conn.createStatement();
        setProduits();
        setClients();
        setFournisseurs();
    }

    public void setFournisseurs() throws SQLException {
        fournisseurs = new ArrayList<>();
        rs = stmt.executeQuery("select * from fournisseur");

        while(rs.next()) {
            Fournisseur f = new Fournisseur(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            fournisseurs.add(f);
        }
        rs.close();
    }

    public void setClients() throws SQLException {
        clients = new ArrayList<>();

        rs = stmt.executeQuery("select * from client");

        while(rs.next()) {
            Client c = new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6));
            clients.add(c);
        }
        rs.close();
    }

    public void setProduits() throws SQLException {
        produits = new ArrayList<>();
        rs = stmt.executeQuery("select * from produit");
        while(rs.next()) {
            Produit p = new Produit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4),rs.getInt(5));
            produits.add(p);
        }
        rs.close();
    }

    public boolean ajouterFournisseur(Fournisseur f)  {
        try{
            String query = "INSERT INTO fournisseur VALUES ('"+ f.getNom()+"','"+f.getAdresse()+"','"+f.getTelephone()+"')";
            stmt.executeUpdate(query);
            //fournisseurs.add(f);
            setFournisseurs();
            return true;
        }catch(SQLException e) {
            e.printStackTrace();
            return false ;
        }
    }

    public boolean ajouterClient(Client c)  {
        try{
            String query = "INSERT INTO client values ('"+c.getNom()+"','"+c.getAdresse()+"','"+c.getTelephone()+"','" +c.getVille()+"','"+c.getPays()+"')";
            stmt.executeUpdate(query);
            //clients.add(c);
            setClients();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean ajouterProduit(Produit p, int idFournisseur){
        try{
            String query = "INSERT INTO produit VALUES ('" +p.getLibelleProduit()+"','"+p.getTypeProduit()+"'," +p.getPrixUnitaire()+","+p.getStock() +","+idFournisseur+")" ;
            stmt.executeUpdate(query);
            //produits.add(p);
            setProduits();
            return true ;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public boolean supprimerFournisseur(Fournisseur f){
       try{
           String query = "DELETE FROM fournisseur where idFournisseur = "+f.getId();
           stmt.executeUpdate(query);
           fournisseurs.remove(f);
           return true;
       } catch (SQLException e) {
           e.printStackTrace();
           return false ;
       }
    }

    public boolean supprimerClient(Client c){
        try{
            String query = "DELETE FROM client where idClient = "+c.getId();
            stmt.executeUpdate(query);
            clients.remove(c);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerProduit(Produit p){
        try{
            String query = "delete from produit where refProduit = " +p.getRefProduit() ;
            stmt.executeUpdate(query);
            produits.remove(p);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public Fournisseur afficherFournisseur (String nomF){
        for(Fournisseur f : fournisseurs){
            if(f.getNom().equalsIgnoreCase(nomF)){
                return f ;
            }
        }
        return null ;
    }

    public Client afficherClient(String nomC){
        for(Client c : clients){
            if(c.getNom().equalsIgnoreCase(nomC)){
                return c;
            }
        }
        return null;
    }

    public Produit afficherProduit(String libelleP){
        for(Produit p : produits){
            if(p.getLibelleProduit().equalsIgnoreCase(libelleP)){
                return p;
            }
        }
        return null ;
    }

    public int getIndexProduit(String libelleP){
        for(Produit p : produits){
            if(p.getLibelleProduit().equalsIgnoreCase(libelleP)){
                return produits.indexOf(p);
            }
        }
        return -1 ;
    }
    public boolean mofifierFournisseur(int idF, String nomF, String adresseF, String telF){
        try{
            String query = "update fournisseur set nomFournisseur = '"+nomF+"', adresse = '"+adresseF+"', telephone = '"+telF+"' where idFournisseur = " +idF;
            int i = stmt.executeUpdate(query);
            setFournisseurs();
            return i > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false ;
        }
    }

    public boolean modifierClient(int idC, String nomC, String adresseC, String telC, String villeC, String paysC){
        try{
            String query = "update client set nom = '"+nomC+"', adresse = '"+adresseC+"', telephone = '" +telC+"', ville = '"+villeC+"', pays =' "+paysC+"' where idClient = " +idC;
            int i = stmt.executeUpdate(query);
            setClients();
            return i > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false ;
        }
    }

    public boolean modifierProduit(int refP, String libelleP, String typeProduit, float prixUnitaire, int qteStock){
        try{
            String query = "update produit set libelleProduit = '" +libelleP+"', typeProduit ='" +typeProduit+"', prix_unitaire ="+prixUnitaire+", qte_stock = "+qteStock+" where refPRoduit = " +refP  ;
            int i = stmt.executeUpdate(query);
            setProduits();
            return i > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false ;
        }
    }


    public ResultSet getCommande(){
        try{
            String query = "select c.idCommande as [N° commande], clt.idClient as [ID client], clt.nom as client, \n" +
                    "count(libelleproduit) as [nombre de produit commandé] ,\n" +
                    "c.etat_Commande as [Etat commande], c.dateCommande as [Date commande], c.totalHT as [Total HT] from commande c ,\n" +
                    "lignecommande lc, produit p, Client clt\n" +
                    "where c.idCommande = lc.idCommande and lc.refProduit = p.refProduit \n" +
                    "and c.idClient = clt.idclient \n" +
                    "group by clt.idClient, clt.nom, c.idCommande, c.etat_commande, c.dateCommande, c.TotalHT order by c.idCommande" ;
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getCommande(int idCommande){
        try{
            String query = "select c.idCommande as [N° commande], clt.nom as client, \n" +
                    "count(libelleproduit) as [nombre de produit commandé] ,\n" +
                    "c.etat_Commande as [Etat commande], c.dateCommande as [Date commande], c.totalHT as [Total HT] from commande c ,\n" +
                    "lignecommande lc, produit p, Client clt\n" +
                    "where c.idCommande = lc.idCommande and lc.refProduit = p.refProduit \n" +
                    "and c.idClient = clt.idclient \n and c.idCommande = " +idCommande +
                    "group by clt.nom, c.idCommande, c.etat_commande, c.dateCommande, c.TotalHT order by c.idCommande" ;
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getProduitParCommande(int idCommande){
        try{
            String query = "select p.refProduit, p.libelleProduit as [Produit], p.typeProduit as [Type Produit],  lc.qte as [Quatité commandé], p.prix_Unitaire as [Prix unitaire] from produit p, ligneCommande lc \n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = " +idCommande ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getProduitParCommande(int idCommande, int refPRoduit){
        try{
            String query = "select p.libelleProduit as [Produit], p.typeProduit as [Type Produit], p.prix_Unitaire as [Prix unitaire],  lc.qte as [Quatité commandé] from produit p, ligneCommande lc \n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = " +idCommande +" and p.refProduit = " +refPRoduit ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getCommandeParClient(){
        try{
            String query = "select c.idClient as [Id client], c.nom as [Nom], c.ville as [Ville], c.pays as [Pays],\n" +
                    "count(cmd.idCommande) as [Nombre de commande], sum(cmd.TotalHT) as [Total HT]\n" +
                    "from client c, commande cmd \n" +
                    "where c.idClient = cmd.idClient group by c.idClient, c.nom, c.pays, c.ville\n" ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


    public ResultSet getCommandeParClient(int idClient){
        try{
            String query = "select cmd.idCommande as [N° commande], cmd.etat_Commande as [Etat Commande], cmd.dateCommande\n" +
                    "as [Date Commande], cmd.TotalHT as [Total HT]\n" +
                    "from commande cmd, client c\n" +
                    "where cmd.idClient = c.idClient and c.idClient = " +idClient ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet commandeExists(int idCmd){
        try{
            String query = "Select * from Commande where idCommande = " +idCmd ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null ;
        }
    }

    public ResultSet clientCommandeExists(int idCmd, int idClient){
        try{
            String query = "select * from Commande where idCommande = " +idCmd +" and idClient = " +idClient ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null ;
        }
    }

    public boolean ajouterCommande(int idCommande, int idClient, int refProduit, int qte){
        try{
            CallableStatement cs = conn.prepareCall("{call dbo.insererCommande(?,?,?,?)}");

            cs.setInt(1, idCommande);
            cs.setInt(2, idClient);
            cs.setInt(3, refProduit);
            cs.setInt(4, qte);

            cs.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifierCommande(int idCmd, String etat){
        try{
            String query = "update commande set etat_commande = '" +etat+"' where idCommande = " +idCmd;
            return stmt.executeUpdate(query) > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    //RAPPORT

    public ResultSet getRapport_ni_periode_ni_type(){
        try{
            String query = "select p.refProduit, p.LibelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c\n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande \n" +
                    "group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.LibelleProduit" ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getRapport_par1_PERIODE(String date1, String date2){
        try{
            String query = "select p.refProduit, p.libelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c\n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and c.dateCommande between '"+date1+"' and '"+date2+"'\n" +
                    "group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.libelleProduit" ;
            return stmt.executeQuery(query);
        }catch(SQLException e ){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getRapport_par1_TYPE(String type){
        try{
            String query = "select p.refProduit, p.libelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c\n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and p.typeProduit = '"+type+"'\n" +
                    "group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.libelleProduit" ;
            return stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getRapport_par2(String date1, String date2, String type){
        try{
            String query = "select p.refProduit, p.libelleProduit, p.typeProduit, sum(lc.qte) as qteVendue, p.prix_Unitaire, sum(lc.qte*p.prix_Unitaire) as [Montant total] from produit p, ligneCommande lc, Commande c\n" +
                    "where p.refProduit = lc.refProduit and lc.idCommande = c.idCommande and c.dateCommande \n" +
                    "between '"+date1+"' and '"+date2+"' and p.typeProduit ='"+type+"'\n" +
                    "group by p.refProduit, p.typeProduit, p.prix_Unitaire, p.libelleProduit";
            return stmt.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public int getnbFournisseur() {return fournisseurs.size();}
    public int getnbClient() {return clients.size();}
    public int getnbProduit() {return produits.size();}

    public ArrayList<Client> getClients() throws SQLException {
        setClients();
        return clients;
    }

    public ArrayList<Fournisseur> getFournisseurs() throws SQLException {
        setFournisseurs();
        return fournisseurs;
    }

    public ArrayList<Produit> getProduits() throws SQLException {
        setProduits();
        return produits;
    }




    public void remplirTableview(ResultSet rs, TableView<Object[]> tableView) {
        try {
            // Clear existing items in the TableView
            tableView.getItems().clear();
            tableView.setStyle("-fx-border-color: black");

            // Get metadata about the ResultSet
            ResultSetMetaData rsm = rs.getMetaData();
            int columnCount = rsm.getColumnCount();

            // Create TableColumn objects for each column in the ResultSet
            tableView.getColumns().clear(); // Clear existing columns
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i;
                TableColumn<Object[], Object> column = new TableColumn<>(rsm.getColumnName(i));
                column.setCellValueFactory(cellData -> {
                    Object[] row = cellData.getValue();
                    return new SimpleObjectProperty<>(row[columnIndex - 1]); // Note: columnIndex is 1-based
                });
                tableView.getColumns().add(column);
            }

            // Iterate through the ResultSet and add data to the TableView
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1); // Note: ResultSet index is 1-based
                }
                tableView.getItems().add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
