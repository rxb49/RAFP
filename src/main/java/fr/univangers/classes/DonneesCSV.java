package fr.univangers.classes;

public class DonneesCSV {

    private int id_emp;
    private String nom_usuel;
    private String prenom;
    private String no_insee;
    private double mnt_retour;
    private double base_retour_recalculee_emp;
    private double SalaraialRafp;
    private double PatronalRafp;
    private double TotalRafp;

    @Override
    public String toString() {
        return "DonneesCSV{" +
                "nom_usuel='" + nom_usuel + '\'' +
                ", id_emp='" + id_emp + '\'' +
                ", prenom='" + prenom + '\'' +
                ", no_insee='" + no_insee + '\'' +
                ", mnt_retour=" + mnt_retour +
                ", base_retour_recalculee_emp=" + base_retour_recalculee_emp +
                ", SalaraialRafp=" + SalaraialRafp +
                ", PatronalRafp=" + PatronalRafp +
                ", TotalRafp=" + TotalRafp +
                '}';
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
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

    public void setNo_insee(String no_insee) {
        this.no_insee = no_insee;
    }

    public double getMnt_retour() {
        return mnt_retour;
    }

    public void setMnt_retour(double mnt_retour) {
        this.mnt_retour = mnt_retour;
    }

    public double getBase_retour_recalculee_emp() {
        return base_retour_recalculee_emp;
    }

    public void setBase_retour_recalculee_emp(double base_retour_recalculee_emp) {
        this.base_retour_recalculee_emp = base_retour_recalculee_emp;
    }

    public double getSalaraialRafp() {
        return SalaraialRafp;
    }

    public void setSalaraialRafp(double salaraialRafp) {
        SalaraialRafp = salaraialRafp;
    }

    public double getPatronalRafp() {
        return PatronalRafp;
    }

    public void setPatronalRafp(double patronalRafp) {
        PatronalRafp = patronalRafp;
    }

    public double getTotalRafp() {
        return TotalRafp;
    }

    public void setTotalRafp(double totalRafp) {
        TotalRafp = totalRafp;
    }
}
