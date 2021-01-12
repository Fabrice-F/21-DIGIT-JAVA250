package com.example.demo.service.export;

import com.example.demo.entity.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@Service
public class ArticleExportCVSService {

    @Autowired
    private ArticleRepository articleRepository;

    public void export(PrintWriter writer) {
        writer.println("Libellé;Prix;Description produit");


        for (Article article :articleRepository.findAll() ) {
            writer.println(String.format("\"%s\";\"%s\";\"%s\"",article.getLibelle(),article.getPrix(),article.getDescription()));

        }

    }


}
