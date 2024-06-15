package com.example.machti_import_export.Controllers;

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
        rapportParProduit(fichier,null,null, null);
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

        Machti m = new Machti();

        rapportParProduit_Sans_Periode_Ni_Type(document, rs);


        document.add(spaces);



        document.add(new Paragraph(" "));
        document.add(new Paragraph("Signature de l'Agent Responsable"));


        document.close();

        openPDF(chemin);
    }

    public static void rapportParProduit_Sans_Periode_Ni_Type(Document document, ResultSet rs) throws SQLException {

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
