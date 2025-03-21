package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.EmployeurDAO;
import fr.univangers.exceptions.UAException;
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

    public boolean insertEmployeur(RafpEmployeur employeur) throws SQLException, UAException {
        return dao.insertEmployeur(employeur);
    }

    public List<RafpEmployeur> getEmployeur() throws SQLException {
        return dao.getEmployeur();
    }


    public RafpEmployeur getEmployeurById(int id) throws SQLException {
        return dao.getEmployeurById(id);
    }

    public List<RafpAgentRetour> getAgentByEmployeurId(int idEmployeur) throws SQLException {
        return dao.getAgentByEmployeurId(idEmployeur);
    }

    public List<RafpEmployeur> getEmployeurBySearch(String recherche) throws SQLException {
        return dao.getEmployeurBySearch(recherche);
    }

    public boolean updateEmployeur(RafpEmployeur employeur) throws SQLException, UAException {
        return dao.updateEmployeur(employeur);
    }

    public boolean insertEmployeurAdd(String no_insee, int id_emp, int montant) throws SQLException, UAException {
        return dao.insertEmployeurAdd(no_insee, id_emp, montant);
    }


}
