package fr.univangers.classes;

public class RafpEmployeur {

    private int id_emp;
    private String lib_emp;
    private String mail_emp;


    @Override
    public String toString() {
        return "RafpEmployeur{" +
                "id_emp=" + id_emp +
                ", lib_emp='" + lib_emp + '\'' +
                ", mail_emp='" + mail_emp + '\'' +
                '}';
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public String getMail_emp() {
        return mail_emp;
    }

    public void setMail_emp(String mail_emp) {
        this.mail_emp = mail_emp;
    }

    public String getLib_emp() {
        return lib_emp;
    }

    public void setLib_emp(String lib_emp) {
        this.lib_emp = lib_emp;
    }
}
