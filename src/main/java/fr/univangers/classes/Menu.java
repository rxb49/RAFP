package fr.univangers.classes;

public class Menu {
    private String nom;
    private String nomPage;
    private String lien;
    private String icone;
    private boolean actif;

    public Menu(String nom, String lien, String icone) {
        this.nom = nom;
        this.nomPage = nom;
        this.lien = lien;
        this.icone = icone;
        this.actif = false;
    }

    public Menu(String nom, String nomPage, String lien, String icone) {
        this.nom = nom;
        this.nomPage = nomPage;
        this.lien = lien;
        this.icone = icone;
        this.actif = false;
    }

    public String getNom() {
        return nom;
    }

    public String getNomPage() {
        return nomPage;
    }

    public String getLien() {
        return lien;
    }

    public String getIcone() {
        return icone;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
