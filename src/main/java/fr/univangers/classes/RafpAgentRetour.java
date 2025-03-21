package fr.univangers.classes;

public class RafpAgentRetour {

    private int id_emp;
    private String nom_usuel;
    private String prenom;
    private String insee;
    private int mnt_retour;


    @Override
    public String toString() {
        return "RafpAgentRetour{" +
                "id_emp=" + id_emp +
                ", nom_usuel=" + nom_usuel +
                ", prenom=" + prenom +
                ", insee='" + insee + '\'' +
                ", mnt_retour=" + mnt_retour +
                '}';
    }

    public String getNom_usuel() {
        return nom_usuel;
    }

    public void setNom_usuel(String nom) {
        this.nom_usuel = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getInsee() {
        return insee;
    }

    public void setInsee(String insee) {
        this.insee = insee;
    }

    public int getMnt_retour() {
        return mnt_retour;
    }

    public void setMnt_retour(int mnt_retour) {
        this.mnt_retour = mnt_retour;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }
}
