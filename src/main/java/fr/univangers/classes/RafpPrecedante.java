package fr.univangers.classes;

public class RafpPrecedante {

    private int no_individu;
    private String no_insee;
    private String nom_usuel;
    private String prenom;
    private int tbi;
    private int indemn;
    private int seuil;
    private int rafpp;
    private int retour;
    private int base_restante;
    private int base_retour_calculee;


    public int getNo_individu() {
        return no_individu;
    }

    public void setNo_individu(int no_individu) {
        this.no_individu = no_individu;
    }

    public String getNo_insee() {
        return no_insee;
    }

    public void setNo_insee(String no_insee) {
        this.no_insee = no_insee;
    }

    public String getNom_usuel() {
        return nom_usuel;
    }

    public void setNom_usuel(String nom_usuel) { this.nom_usuel = nom_usuel; }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public int getTbi() {
        return tbi;
    }

    public void setTbi(int tbi) {
        this.tbi = tbi;
    }

    public int getIndemn() {
        return indemn;
    }

    public void setIndemn(int indemn) { this.indemn = indemn; }

    public int getSeuil() {
        return seuil;
    }

    public void setSeuil(int seuil) { this.seuil = seuil; }

    public int getRafpp() {
        return rafpp;
    }

    public void setRafpp(int rafpp) { this.rafpp = rafpp; }

    public int getRetour() {
        return retour;
    }

    public void setRetour(int retour) { this.retour = retour; }

    public int getBase_Restante() {
        return base_restante;
    }

    public void setBase_Restante(int base_restante) { this.base_restante = base_restante; }

    public int getBase_Retour_Calculee() {
        return base_retour_calculee;
    }

    public void setBase_Retour_Calculee(int base_retour_calculee) { this.base_retour_calculee = base_retour_calculee; }

}
