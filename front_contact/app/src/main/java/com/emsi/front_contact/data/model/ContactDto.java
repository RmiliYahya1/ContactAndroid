package com.emsi.front_contact.data.model;

import com.google.gson.annotations.SerializedName;

public class ContactDto {
    @SerializedName("nom")
    private String nom;
    @SerializedName("numero")
    private String numero;

    public ContactDto(){}
    public ContactDto(String nom, String numero){
        this.nom=nom;
        this.numero=numero;
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
