package com.example.immobilier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bien implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String adresse;
    private String prix;
    private String surface;
    private String nbPiece;
    private String description;
    private String typeBien;
    private String etat;
    private String ville;
    private String cp;
    private String chambres;

    private List<String> tags;



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    private List<String> url;


    public String getAdresse() {
        return adresse;
    }

    public String getChambres() {
        return chambres;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNbPiece() {
        return nbPiece;
    }

    public void setNbPiece(String nbPiece) {
        this.nbPiece = nbPiece;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setTypeBien(String typeBien) {
        this.typeBien = typeBien;
    }
    public String getTypeBien() {
        return typeBien;
    }



    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }







    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public void setChambres(String chambres) {
        this.chambres = chambres;
    }



    public void setUrl(List<String> url) {
        this.url = url;
    }


    public Bien(String adresse, String prix, String surface, String nbPiece, String description, String typeBien, String etat, String chambres, List<String> url) {
        this.adresse = adresse;
        this.prix = prix;
        this.surface = surface;
        this.nbPiece = nbPiece;
        this.description = description;
        this.typeBien = typeBien;
        this.etat = etat;
        this.chambres = chambres;
        this.url = url;
        tags = new ArrayList<>();
    }


    public Bien() {}

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPrix() {
        return prix;
    }

    public String getSurface() {
        return surface;
    }


public String getTitle(){
        return this.ville+ " ("+this.cp+")";
}


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getUrl() {
        if (url!= null){
            return url;
        }
        return new ArrayList<>();

    }
}
