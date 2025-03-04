package fr.univangers.classes;

public class Person {
    private int numero;
    private String nom;
    private String prenom;

    public Person(int numero, String nom, String prenom) {
        this.numero = numero;
        this.nom = nom;
        this.prenom = prenom;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
