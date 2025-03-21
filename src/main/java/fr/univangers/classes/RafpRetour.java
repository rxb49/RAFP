package fr.univangers.classes;

public class RafpRetour {

    private int annee;
    private String insee;
    private int id_emp;
    private String lib_emp;
    private int mnt_retour;
    private int base_retour_recalculee_emp;



    @Override
    public String toString() {
        return "RafpRetour{" +
                "annee=" + annee +
                ", insee='" + insee + '\'' +
                ", id_emp='" + id_emp + '\'' +
                ", lib_emp=" + lib_emp +
                ", mnt_retour='" + mnt_retour + '\'' +
                ", base_retour_recalculee_emp=" + base_retour_recalculee_emp +
                '}';
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getInsee() {
        return insee;
    }

    public void setInsee(String insee) {
        this.insee = insee;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public String getLib_emp() {
        return lib_emp;
    }

    public void setLib_emp(String id_emp) {
        this.lib_emp = id_emp;
    }

    public int getMnt_retour() {
        return mnt_retour;
    }

    public void setMnt_retour(int mnt_retour) {
        this.mnt_retour = mnt_retour;
    }

    public int getBase_retour_recalculee_emp() {
        return base_retour_recalculee_emp;
    }

    public void setBase_retour_recalculee_emp(int base_retour_recalculee_emp) {
        this.base_retour_recalculee_emp = base_retour_recalculee_emp;
    }
}
