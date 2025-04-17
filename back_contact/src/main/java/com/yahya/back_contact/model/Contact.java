package com.yahya.back_contact.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author yahya
 **/

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Integer id;

    private String nom;

    private String numero;

    public Contact(int id, String nom, String num) {
        this.id=id;
        this.nom = nom;
        this.numero = num;
    }
    public Contact() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
