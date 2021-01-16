package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class FactureExportXLSXService {

    @Autowired
    private FactureService factureService;

    Workbook wb;
    public void export(OutputStream outputSteam) {
        try {
            // Apache POI
            wb = new XSSFWorkbook();
            // TODO
            // création de la feuille
            Sheet sheet = wb.createSheet("Feuille 1");

            // création de l'entête
            Row row = sheet.createRow(0);
            CellStyle style = wb.createCellStyle();

            // application du style et de la valeurs
            CellStyle(row.createCell(0),"NOM",true);
            CellStyle(row.createCell(1),"PRENOM",true);
            CellStyle(row.createCell(2),"AGE",true);

            List<FactureDto> factureDtos = factureService.findAllFactures();
            for (int i = 0; i < factureDtos.size(); i++) {
                row = sheet.createRow(i+1);
//                CellStyle(row.createCell(0),clientDtoList.get(i).getNom(),false);
//                CellStyle(row.createCell(1),clientDtoList.get(i).getPrenom(),false);
//                CellStyle(row.createCell(2),clientDtoList.get(i).getAge().toString() + " ans",false);
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CellStyle (Cell cell,String value,Boolean Header){
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(BorderStyle.THICK);
        style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderLeft(BorderStyle.THICK);
        style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderRight(BorderStyle.THICK);
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
        style.setBorderTop(BorderStyle.THICK);
        style.setTopBorderColor(IndexedColors.BLUE.getIndex());
        cell.setCellStyle(style);
        cell.setCellValue(value);
        if (Header)
        {
            Font font = wb.createFont();
            font.setBold(true);
            font.setColor(IndexedColors.PINK.getIndex());
            style.setFont(font);
        }
    }

}
