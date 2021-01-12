package com.example.demo.controller.export;

import com.example.demo.service.export.ArticleExportCVSService;
import com.example.demo.service.export.ArticleExportXLSXService;
import com.example.demo.service.export.ClientExportCVSService;
import com.example.demo.service.export.ClientExportXLSXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controller pour réaliser export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportClientController {

    @Autowired
    private ClientExportCVSService clientExportCVSService;
    @Autowired
    private ClientExportXLSXService clientExportXLSXService;

    /** Méthode utilisée pour l'export des clients en CSV */
    @GetMapping("/clients/csv")
    public void clientCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();
        clientExportCVSService.export(writer);
    }

    /** Méthode utilisée pour l'export des clients en XLSX */
    @GetMapping("/clients/xlsx")
    public void clientXLSX(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        clientExportXLSXService.export(outputStream);
    }



}
