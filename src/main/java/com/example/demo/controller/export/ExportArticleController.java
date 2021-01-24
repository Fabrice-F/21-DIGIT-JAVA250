package com.example.demo.controller.export;

import com.example.demo.service.export.ArticleExportCVSService;
import com.example.demo.service.export.ArticleExportXLSXService;
import com.example.demo.service.export.ClientExportCVSService;
import com.example.demo.service.export.ExportPDFITextService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportArticleController {

    @Autowired
    private ArticleExportCVSService articleExportCVSService;

    @Autowired
    private ArticleExportXLSXService articleExportXLSXService;

    @Autowired
    private ExportPDFITextService exportPDFITextService;


    /**
     * Export des articles au format CSV.
     */
    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
        PrintWriter writer = response.getWriter();
        articleExportCVSService.export(writer);
    }

    @GetMapping("/articles/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        articleExportXLSXService.export(outputStream);
    }

    /** Méthode utilisée pour l'export facture (point n°5) */
    @GetMapping("/clients/{id}/factures/xlsx")
    public void clientGetFacturesXLSX(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"client-" + id + "-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        // TODO
        Workbook wb = new HSSFWorkbook();
        wb.write(outputStream);
    }

    /**
     * Export de la liste des articles au format PDF.
     */
    @GetMapping("/articles/pdf")
    public void facturesPDF(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"test-factures.pdf\"");
        OutputStream outputStream = response.getOutputStream();
        exportPDFITextService.export(outputStream);
    }

}
