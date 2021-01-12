package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    public InitData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        insertTestData();
    }

    private void insertTestData() {
        Article a1 = createArticle("Chargeurs de téléphones Portables", 22.98, 9,"Samsung EP-P1100. Type de chargeur: Intérieur;\n" + "Type de source d'alimentation: Secteur;\n" + "Compatibilité de chargeur: Smartphone;\n" + "Charge rapide. Couleur du produit: Noir");
        Article a2 = createArticle("Playmobil Hydravion de Police", 14.39, 2,"L'intérieur de l'avion peut contenir deux personnages et une valise.");
        Article a3 = createArticle("Distributeur de croquettes pour chien", 12.99, 0,"Distributeur de nourriture croquettes, biscuits ou snacks pour chats et chiens Plastique robuste avec couvercle amovible");

        Client cl1 = createClient("John", "Doe",LocalDate.parse("1987-08-30"));
        Client cl2 = createClient("Jeff", "Bezos",LocalDate.parse("1964-01-12"));
        Client cl3 = createClient("James", "Gosling",LocalDate.parse("1955-05-19"));
        Client cl4 = createClient("Rosa", "Parks",LocalDate.parse("1913-01-04"));
    }

    private Client createClient(String prenom, String nom, LocalDate date) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setDateNaisance(date);
        entityManager.persist(client);
        return null;
    }

    private Article createArticle(String libelle, double prix, int stock,String description) {
        Article a1 = new Article();
        a1.setLibelle(libelle);
        a1.setPrix(prix);
        a1.setStock(stock);
        a1.setDescription(description);
        entityManager.persist(a1);
        return a1;
    }

}
