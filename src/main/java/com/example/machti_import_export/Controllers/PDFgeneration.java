package com.example.machti_import_export.Controllers;

import com.example.machti_import_export.MachtiSte.Client;
import com.example.machti_import_export.MachtiSte.Machti;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javax.xml.transform.Result;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PDFgeneration {


    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String fichier = "Rapport Sans période ni type_" + LocalDate.now() +".pdf";
        Machti m = new Machti();
        Client c = new Client("Youssef El khalloufi","Hay mohammadi rue 72 N° 21","0627860225","EL AIOUN","MAROC");
        //genererCommande("Commande.pdf",202,c,"26-02-2024", m.getProduitParCommande(4) );
        rapportParClient("rapportParClient", c, m.getCommandeParClient(2));
    }
    public static void rapportParProduit(String nomFichier,String date1, String date2, ResultSet rs) throws FileNotFoundException, SQLException {


        File rapport = new File("Rapport");
        if (!rapport.exists()) {
            rapport.mkdir();
        }

        File rapportParProduit = new File(rapport, "Rapport Par Produit");
        if (!rapportParProduit.exists()) {
            rapportParProduit.mkdir();
        }


        String chemin = rapportParProduit.getPath() +"/"+nomFichier;

        PdfWriter ecrivainPdf = new PdfWriter(chemin);
        PdfDocument documentPdf = new PdfDocument(ecrivainPdf);
        documentPdf.setDefaultPageSize(new PageSize(600f, 700f));

        Document document = new Document(documentPdf);

        Paragraph spaces = new Paragraph();
        spaces.add("\n");


        Paragraph infosEntreprise = new Paragraph()
                .add(new Cell().add("Société MACHTI\n").setBold().setFontSize(18))
                .add("\nRapport de vente par produit\n")
                .add("Période : " + date1 +" / " +date2 +"\n")
                .add("Date de génération du rapport : " + LocalDate.now())
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);

        document.add(infosEntreprise);


        document.add(spaces);

        Border grayBorder = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(new float[]{190f * 3});
        divider.setBorder(grayBorder);
        document.add(divider);

        document.add(spaces);

        rapportParProduit(document, rs);


        document.add(spaces);



        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature de l'Agent Responsable"));


        document.close();

        openPDF(chemin);
    }

    public static void rapportParProduit(Document document, ResultSet rs) throws SQLException {

        Table table = new Table(new float[]{60f,250f,140f, 170f, 70f, 120f});
        table.addCell(new Cell().add("Réf").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Désignation").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Type produit").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Quantité vendue").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("PU").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Montant total").setTextAlignment(TextAlignment.CENTER).setBold());


        int nbTotalProduit = 0;
        float montantTotalFinal = 0;


        // Iterate through the result set and add cells to the table
        while (rs.next()) {
            int refProduit = rs.getInt(1);
            String designation = rs.getString(2);
            String typeProduit = rs.getString(3);
            int qteVendue = rs.getInt(4);
            float prixUnitaire = rs.getFloat(5);
            float montantTotal = rs.getFloat(6);

            // Add data to table cells
            table.addCell(new Cell().add(String.valueOf(refProduit)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(designation));
            table.addCell(new Cell().add(typeProduit));
            table.addCell(new Cell().add(String.valueOf(qteVendue)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(prixUnitaire)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(montantTotal)).setTextAlignment(TextAlignment.CENTER));

            nbTotalProduit += qteVendue;

            montantTotalFinal += montantTotal;


        }


        Table tableTotal = new Table(new float[]{190f, 90f});
        tableTotal.addCell(new Cell().add("Quantité totale vendue").setBold());
        tableTotal.addCell(new Cell().add(nbTotalProduit +"").setTextAlignment(TextAlignment.CENTER));
        tableTotal.addCell(new Cell().add("Montant total des ventes").setBold());
        tableTotal.addCell(new Cell().add(montantTotalFinal +" DH").setTextAlignment(TextAlignment.CENTER));



        System.out.println("Total number of products sold: " + nbTotalProduit);
        System.out.println("Total price: " + montantTotalFinal);

        document.add(table);

        document.add(new Paragraph("\n"));

        document.add(tableTotal);
    }

    public static void rapportParClient(String nomFichier, Client client, ResultSet rs) throws FileNotFoundException, SQLException {
        File rapport = new File("Rapport");
        if (!rapport.exists()) {
            rapport.mkdir();
        }

        File rapportParClient = new File(rapport, "Rapport Par Client");
        if (!rapportParClient.exists()) {
            rapportParClient.mkdir();
        }


        String chemin = rapportParClient.getPath() +"/"+nomFichier;

        PdfWriter ecrivainPdf = new PdfWriter(chemin);
        PdfDocument documentPdf = new PdfDocument(ecrivainPdf);
        documentPdf.setDefaultPageSize(new PageSize(600f, 700f));

        Document document = new Document(documentPdf);

        Paragraph spaces = new Paragraph();
        spaces.add("\n");

        Paragraph infosEntreprise = new Paragraph()
                .add(new Cell().add("Société MACHTI\n").setBold().setFontSize(18))
                .add("\nRapport de vente par Client\n")
                .add("Date de génération du rapport : " + LocalDate.now())
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);

        document.add(infosEntreprise);


        document.add(spaces);

        Border grayBorder = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(new float[]{190f * 3});
        divider.setBorder(grayBorder);
        document.add(divider);

        document.add(spaces);

        Table infosClient = new Table(new float[]{105f, 345f});
        infosClient.addCell(new Cell().add("Nom complet :").setBold().setBorder(Border.NO_BORDER));
        infosClient.addCell(new Cell().add(client.getNom()).setBorder(Border.NO_BORDER));
        infosClient.addCell(new Cell().add("Adresse :").setBold().setBorder(Border.NO_BORDER));
        infosClient.addCell(new Cell().add(client.getAdresse()+", " + client.getVille() +", " +client.getPays()).setBorder(Border.NO_BORDER));
        infosClient.addCell(new Cell().add("Telephone :").setBold().setBorder(Border.NO_BORDER));
        infosClient.addCell(new Cell().add(client.getTelephone()).setBorder(Border.NO_BORDER));


        document.add(infosClient);
        document.add(spaces);


        Table table = new Table(new float[]{110f,300f,300f, 100f});
        table.addCell(new Cell().add("N° Commande").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Etat Commande").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Date Commande").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Total HT").setTextAlignment(TextAlignment.CENTER).setBold());


        float total = 0 ;

        while(rs.next()){
            table.addCell(new Cell().add(String.valueOf(rs.getInt(1))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(rs.getString(2)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(rs.getDate(3).toString()).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(rs.getFloat(4))).setTextAlignment(TextAlignment.CENTER));

            total += rs.getFloat(4);
        }

        Table tableTotal = new Table(new float[]{190f, 90f});
        tableTotal.addCell(new Cell().add("Montant totale des ventes").setBold());
        tableTotal.addCell(new Cell().add(total +" DH").setTextAlignment(TextAlignment.CENTER));

        document.add(table);
        document.add(spaces);
        document.add(tableTotal);

        document.add(spaces);
        document.add(spaces);
        document.add(spaces);
        document.add(new Paragraph("Signature de l'Agent Responsable"));


        document.close();
        openPDF(chemin);

    }

    public static void genererCommande(String nomFichier, int idCmd, Client client, String dateCmd, ResultSet rs) throws SQLException, FileNotFoundException {
        File commande = new File("Commande");
        if (!commande.exists()) {
            commande.mkdir();
        }

        String chemin = commande.getPath() +"/"+nomFichier+"_"+dateCmd;

        PdfWriter ecrivainPdf = new PdfWriter(chemin);
        PdfDocument documentPdf = new PdfDocument(ecrivainPdf);
        documentPdf.setDefaultPageSize(PageSize.A4);

        Document document = new Document(documentPdf);

        Paragraph spaces = new Paragraph();
        spaces.add("\n");

        Paragraph infosEntreprise = new Paragraph()
                .add(new Cell().add("Société MACHTI\n").setBold().setFontSize(20).setFontColor(Color.RED))
                .add("\n81 BD LA RESISTANCE 4EME ETAGE, APRT N4\n")
                .add("CASABLANCA \n")
                .add("MAROC")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);

        document.add(infosEntreprise);

        document.add(spaces);

        Border grayBorder = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(new float[]{190f * 3});
        divider.setBorder(grayBorder);
        document.add(divider);

        document.add(spaces);



        Table infosCommande = new Table(new float[]{120f, 150f});
        infosCommande.addCell(new Cell().add("Commande N° :").setBold());
        infosCommande.addCell(new Cell().add(String.valueOf(idCmd)));
        infosCommande.addCell(new Cell().add("Date :").setBold());
        infosCommande.addCell(new Cell().add(dateCmd));

        Table infosAcheteur = new Table(new float[]{105f, 345f});
        infosAcheteur.addCell(new Cell().add("Nom complet :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getNom()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("Adresse :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getAdresse()+", " + client.getVille() +", " +client.getPays()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("Telephone :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getTelephone()).setBorder(Border.NO_BORDER));


        document.add(infosAcheteur);
        document.add(spaces);
        document.add(infosCommande);
        document.add(spaces);
        document.add(spaces);


        Table table = new Table(new float[]{60f,250f,140f, 70f, 170f, 120f});
        table.addCell(new Cell().add("Réf").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Désignation").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Type produit").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("QTE").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Prix Unitaire").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Montant total").setTextAlignment(TextAlignment.CENTER).setBold());


        float total = 0;

        while(rs.next()){
            table.addCell(new Cell().add(String.valueOf(rs.getInt(1))).setTextAlignment(TextAlignment.CENTER).setBold());
            table.addCell(new Cell().add(rs.getString(2)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(rs.getString(3)).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(rs.getInt(4))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(rs.getFloat(5))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(rs.getFloat(4)*rs.getFloat(5))).setTextAlignment(TextAlignment.CENTER));

            total += rs.getFloat(4)*rs.getFloat(5);
        }

        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Total").setBold().setFontSize(15));
        table.addCell(new Cell().add(total+" DH").setFontSize(12).setTextAlignment(TextAlignment.CENTER));


        document.add(table);

        document.add(spaces);
        document.add(spaces);
        Paragraph signature = new Paragraph("Signature de l'Agent responsable           ").setTextAlignment(TextAlignment.RIGHT).setFontSize(12);
        document.add(signature);

        document.add(spaces);
        document.add(spaces);
        document.add(spaces);
        document.add(spaces);
        document.add(spaces);
        document.add(spaces);



        document.add(divider);


        Paragraph footer = new Paragraph().setFontSize(12f).setTextAlignment(TextAlignment.CENTER)
                .add("SIEGE SOCIAL : 81 BD LA RESISTANCE 4EME ETAGE, APRT N4 CASABLANCA, MAROC - " +
                        "Fax : 0520186570 - TEL : 0773254249 - GMAIL : Machti.ste@gmail.com");

        document.add(footer);

        document.close();

        openPDF(chemin);
    }


    private static void openPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("Desktop is not supported. Cannot open the PDF file.");
                }
            } else {
                System.out.println("The file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
