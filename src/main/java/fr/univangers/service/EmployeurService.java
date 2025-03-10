package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.EmployeurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EmployeurService {
    final private EmployeurDAO dao;

    @Autowired
    public EmployeurService(EmployeurDAO dao){
        super();
        this.dao = dao;
    }

    public RafpEmployeur insertEmployeur(String nomEmployeur, String mailEmployeur) throws SQLException {
        return dao.insertEmployeur(nomEmployeur, mailEmployeur);
    }

    public List<RafpEmployeur> getEmployeur() throws SQLException {
        return dao.getEmployeur();
    }


}
