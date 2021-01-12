package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;

@Service
public class ClientExportCVSService {

    @Autowired
    private ClientRepository clientRepository;

    public void export(PrintWriter writer) {

        writer.println("NOM;PRENOM;AGE");
        for (Client client :clientRepository.findAll() ) {
            int age = Period.between(client.getDateNaisance(), LocalDate.now()).getYears();
            writer.println( String.format("\"%s\";\"%s\";\"%s\"",client.getNom(),client.getPrenom(),age));
        }

    }


}
