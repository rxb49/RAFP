package fr.univangers.classes;

import java.util.HashMap;
import java.util.Map;

public class RafpImport {

    private int id_emp;
    private String lib_emp;
    private String insee;
    private String nom_usuel;
    private String prenom;
    private double retour;

    public String getLib_emp() {
        return lib_emp;
    }

    public void setLib_emp(String lib_emp) {
        this.lib_emp = lib_emp;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public String getInsee() {
        return insee;
    }

    public void setInsee(String insee) {
        this.insee = insee;
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

    public double getRetour() {
        return retour;
    }

    public void setRetour(double retour) {
        this.retour = retour;
    }
}
