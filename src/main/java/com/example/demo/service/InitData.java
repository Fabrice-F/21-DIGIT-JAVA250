package com.example.demo.service;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Classe permettant d'insérer des données dans l'application.
 */
@Service
@Transactional
public class InitData implements ApplicationListener<ApplicationReadyEvent> {

    private EntityManager entityManager;

    @Autowired
    ClientRepository clientRepository;

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


        Facture fac1 = createFacture(cl1);
        LigneFacture lf1 = createLigneFacture(a1,2,fac1);
        LigneFacture lf2 = createLigneFacture(a2,4,fac1);



        Facture fac2 = createFacture(cl2);
        LigneFacture lf3 = createLigneFacture(a3,1,fac2);

        Facture fac3 = createFacture(cl3);
        LigneFacture lf4 = createLigneFacture(a2,1,fac3);
        LigneFacture lf5 = createLigneFacture(a1,2,fac3);
        LigneFacture lf6 = createLigneFacture(a3,3,fac3);

        Facture fac4 = createFacture(cl4);
        LigneFacture lf7 = createLigneFacture(a1,8,fac4);
        LigneFacture lf8 = createLigneFacture(a2,3,fac4);

        Facture fac5 = createFacture(cl1);
        LigneFacture lf9 = createLigneFacture(a3,1,fac5);
        LigneFacture lf10 = createLigneFacture(a1,14,fac5);

        Facture fac6 = createFacture(cl2);
        LigneFacture lf12 = createLigneFacture(a1,4,fac6);

        Facture fac7 = createFacture(cl4);
        LigneFacture lf13 = createLigneFacture(a1,1,fac7);
        LigneFacture lf14 = createLigneFacture(a2,1,fac7);
        LigneFacture lf15 = createLigneFacture(a3,1,fac7);

        Facture fac8 = createFacture(cl4);
        LigneFacture lf17 = createLigneFacture(a2,8,fac8);
    }

    private Client createClient(String prenom, String nom, LocalDate date) {
        Client client = new Client();
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setDateNaisance(date);
        entityManager.persist(client);
        return client;
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

    private Facture createFacture(Client client){
        Facture facture = new Facture();
        facture.setClient(client);
        entityManager.persist(facture);
        return facture;
    }

    private LigneFacture createLigneFacture(Article article,int quantite,Facture facture){
        LigneFacture lf = new LigneFacture();
        lf.setArticle(article);
        lf.setQuantite(quantite);
        lf.setFacture(facture);
        entityManager.persist(lf);
        return lf;
    }
}
