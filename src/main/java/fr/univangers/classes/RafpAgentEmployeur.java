package fr.univangers.classes;

public class RafpAgentEmployeur {

    private String lib_emp;
    private String nom_usuel;
    private String prenom;
    private String no_insee; // Siham_adm.siham_individu_paye
    private int mnt_retour; // rafp 2023 RafpPrecedante
    private int base_retour_recalculee_emp;// rafp 2023 RafpPrecedante

    @Override
    public String toString() {
        return "RafpAgentEmployeur{" +
                ", lib_emp='" + lib_emp + '\'' +
                ", nom_usuel='" + nom_usuel + '\'' +
                ", prenom='" + prenom + '\'' +
                ", no_insee='" + no_insee + '\'' +
                ", mnt_retour=" + mnt_retour +
                ", base_retour_recalculee_emp=" + base_retour_recalculee_emp +
                '}';
    }


    public String getLib_emp() {
        return lib_emp;
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

    public void setLib_emp(String lib_emp) {
        this.lib_emp = lib_emp;
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
