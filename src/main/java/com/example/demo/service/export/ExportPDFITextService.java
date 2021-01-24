package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.io.PrintWriter;

@Service
public class ExportPDFITextService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(OutputStream outputSteam) throws DocumentException {

        Document document = new Document();
        PdfWriter.getInstance(document, outputSteam);
        document.open();

        PdfPTable table = new PdfPTable(3); // 3 columns.
        table.setWidthPercentage(100); //Width 100%
        table.setSpacingBefore(10f); //Space before table
        table.setSpacingAfter(10f); //Space after table

        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f};
        table.setWidths(columnWidths);

        PdfPCell cell1 = new PdfPCell(new Paragraph("LIBELLÉ"));
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setPaddingLeft(10);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell2 = new PdfPCell(new Paragraph("PRIX"));
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setPaddingLeft(10);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell3 = new PdfPCell(new Paragraph("DESCRIPTION"));
        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell3.setPaddingLeft(10);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);


        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        createCellArticle(table,document);

        // step 5
        document.close();

    }

    public void createCellArticle(PdfPTable table , Document document) throws DocumentException {

        for (Article article : articleRepository.findAll()) {
            PdfPCell cellLibellé = new PdfPCell(new Paragraph(article.getLibelle()));
            Double prix = article.getPrix();
            PdfPCell cellPrix= new PdfPCell(new Paragraph(prix.toString()));
            cellPrix.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell cellDescription = new PdfPCell(new Paragraph(article.getDescription()));
            table.addCell(cellLibellé);
            table.addCell(cellPrix);
            table.addCell(cellDescription);
        }
        document.add(table);
    }

}
