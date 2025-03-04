package fr.univangers.classes;

import java.util.List;

public class User {
    private String firstname;
    private String lastname;
    private String picture;
    private String program;
    private String promotion;
    private List<String> groups;

    public User(){
        super();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
