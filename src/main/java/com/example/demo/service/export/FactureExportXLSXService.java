package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FactureExportXLSXService {

    @Autowired
    private FactureService factureService;

    @Autowired
    private ClientRepository clientRepository;

    Workbook wb;
    public void export(OutputStream outputSteam) {
        try {
            // Apache POI
            wb = new XSSFWorkbook();
            // TODO
            List<Client> clientList =  clientRepository.findAll();
            for( Client client : clientList) {
                Sheet sheet = wb.createSheet(client.getNom() + " " + client.getPrenom());
                createCellClient(sheet.createRow(0),"Nom : ",client.getNom());
                createCellClient(sheet.createRow(1),"Prénom : ",client.getPrenom());
                createCellClient(sheet.createRow(2),"Année de naissance : ",client.getDateNaisance().getYear());
                createCellClientTotalFacture(sheet.createRow(3),client);

                sheet.autoSizeColumn(0,true);

                // creation feuille(s) facture:
                Set<Facture> factureSet = client.getFactures();
                for (Facture facture : factureSet) {
                    Sheet sheetFacture = wb.createSheet("Facture N° "+facture.getId().toString());
                    createHeaderCellLigneFacture(sheetFacture.createRow(0));


                    // create des ligne factures:
                    Set<LigneFacture> ligneFactureSet  = facture.getLigneFactures();
                    List<LigneFacture> ligneFactureList = new ArrayList<LigneFacture>(ligneFactureSet);
                    int RowTotalPrice = ligneFactureList.size()+1;
                    double priceTotal=0;
                    for (int i = 0; i < ligneFactureList.size(); i++) {
                        priceTotal += createCellLigneFacture(sheetFacture.createRow(i+1),ligneFactureList.get(i));
                    }
                    createRowTotalLigneFacture(sheetFacture.createRow(RowTotalPrice),priceTotal);

                    sheetFacture.autoSizeColumn(0,true);
                    sheetFacture.autoSizeColumn(1,true);
                    sheetFacture.autoSizeColumn(2,true);
                }
            }
            wb.write(outputSteam);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCellClient(Row row,String Name,Object value){
        Cell cellOne =  row.createCell(0);
        cellOne.setCellValue(Name);
        Cell cellTwo =  row.createCell(1);
        cellTwo.setCellValue(value.toString());
    }

    private void createCellClientTotalFacture(Row row,Client client){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        Cell cellOne =  row.createCell(0);
        cellOne.setCellValue(client.getFactures().size() +" facture(s)");
        cellOne.setCellStyle(style);
        for (int i = 0; i < client.getFactures().size(); i++) {
            Cell cell=  row.createCell(i+1);
            Set<Facture> factureSet = client.getFactures();
            List<Facture> factures = new ArrayList<Facture>(factureSet);
            cell.setCellValue(factures.get(i).getId());
            cell.setCellStyle(style);
        }
    }

    private void createHeaderCellLigneFacture(Row row){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        Cell cell1 = row.createCell(0);
        cell1 .setCellValue("Désignation");
        cell1 .setCellStyle(style);

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Quantité");
        cell2 .setCellStyle(style);

        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Prix unitaire");
        cell3 .setCellStyle(style);
    }

    private double createCellLigneFacture(Row row , LigneFacture ligneFacture){
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(ligneFacture.getArticle().getLibelle());


        Cell cell2 = row.createCell(1);
        cell2.setCellValue(ligneFacture.getQuantite());


        Cell cell3 = row.createCell(2);
        cell3.setCellValue(ligneFacture.getArticle().getPrix());

        return ligneFacture.getQuantite() * ligneFacture.getArticle().getPrix();
    }

    private void createRowTotalLigneFacture(Row row,double price){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        style.setFont(font);

        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Total : ");
        cell2.setCellStyle(style);


        Cell cell3 = row.createCell(2);
        cell3.setCellValue(price);
        cell3.setCellStyle(style);

    }
}
