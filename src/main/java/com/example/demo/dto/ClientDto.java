package com.example.demo.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Classe permettant d'exposer des données au format JSON au client.
 */
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private Integer age;

    public ClientDto(Long id, String nom, String prenom, LocalDate date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance=date;
        this.age = Period.between(date, LocalDate.now()).getYears();
    }

    public String getDateNaissance() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateNaissance.format(dateFormat);
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
