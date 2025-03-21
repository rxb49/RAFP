package fr.univangers.classes;

public class RafpLibAgent {

    private String nom_usuel;
    private String prenom;
    private String no_insee;

    @Override
    public String toString() {
        return "RafpLibAgent{" +
                ", nom_usuel='" + nom_usuel + '\'' +
                ", prenom='" + prenom + '\'' +
                ", no_insee='" + no_insee + '\'' +
                '}';
    }

    public String getNom_usuel() {
        return nom_usuel;
    }

    public void setNom_usuel(String nom_usuel) {
        this.nom_usuel = nom_usuel;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNo_insee() {
        return no_insee;
    }

    public void setNo_insee(String no_insee) { this.no_insee = no_insee; }



}
