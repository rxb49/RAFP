package fr.univangers.classes;

public class RafpAgent {

    private String annee;
    private String no_dossier_pers;
    private String no_insee;
    private int tbi;
    private int indemn;
    private int seuil;
    private int rafpp;
    private int base_restante;
    private int total_retour;
    private int base_retour_recalculee;


    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getNo_dossier_pers() {
        return no_dossier_pers;
    }

    public void setNo_dossier_pers(String no_dossier_pers) {
        this.no_dossier_pers = no_dossier_pers;
    }

    public String getNo_insee() {
        return no_insee;
    }

    public void setNo_insee(String no_insee) { this.no_insee = no_insee; }

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

    public int getBase_Restante() {
        return base_restante;
    }

    public void setBase_Restante(int base_restante) { this.base_restante = base_restante; }

    public int getTotal_Retour() {
        return total_retour;
    }

    public void setTotal_Retour(int total_retour) { this.total_retour = total_retour; }

    public int getBase_Retour_Recalculee() {
        return base_retour_recalculee;
    }

    public void setBase_retour_recalculee(int base_retour_recalculee) { this.base_retour_recalculee = base_retour_recalculee; }

}
