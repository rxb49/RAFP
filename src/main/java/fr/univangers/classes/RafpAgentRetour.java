package fr.univangers.classes;

public class RafpAgentRetour {

    private int id_emp;
    private String insee;
    private int mnt_retour; // rafp 2023 RafpPrecedante


    @Override
    public String toString() {
        return "RafpAgentRetour{" +
                "id_emp=" + id_emp +
                ", insee='" + insee + '\'' +
                ", mnt_retour=" + mnt_retour +
                '}';
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
