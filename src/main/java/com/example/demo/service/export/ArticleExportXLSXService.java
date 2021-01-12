package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Service
public class ArticleExportXLSXService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(OutputStream outputSteam) {
        try {
            // Apache POI
            Workbook wb = new XSSFWorkbook();
            // TODO
            // création de la feuille
            Sheet sheet = wb.createSheet("Feuille 1");

            // création de l'entête
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Libellé");
            row.createCell(1).setCellValue("Description"); // largeur taille 169.14
            row.createCell(2).setCellValue("Prix");
            row.createCell(3).setCellValue("Stock");


            List<Article> articleList = articleRepository.findAll();
            for (int i = 0; i < articleList.size(); i++) {
                row = sheet.createRow(i+1);
                row.createCell(0).setCellValue(articleList.get(i).getLibelle());
                row.createCell(1).setCellValue(articleList.get(i).getDescription());
                row.createCell(2).setCellValue(articleList.get(i).getPrix());
                row.createCell(3).setCellValue(articleList.get(i).getStock());
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
