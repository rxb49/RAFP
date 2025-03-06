package fr.univangers.service;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.SihamIndividuPaye;
import fr.univangers.dao.PersonnelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class PersonnelService {
    final private PersonnelDAO dao;

    @Autowired
    public PersonnelService(PersonnelDAO dao){
        super();
        this.dao = dao;
    }

    //Lors d'un vrai projet : les méthodes delete(), insert() et update() il appelle les méthodes dans la DAO.

    public Personnel info(String login ) throws SQLException {
        return dao.info(login);
    }

    public RafpAgent initialisation() throws SQLException {
        return dao.initialisation();
    }

    public RafpPrecedante getRafpPrecedante() throws SQLException {
        return dao.getRafpPrecedante();
    }

    public SihamIndividuPaye getNoInsee() throws SQLException {
        return dao.getNoInsee();
    }

    public RafpAgent insertAgent(RafpAgent agent) throws SQLException {
        return dao.insertAgent(agent);
    }

    public boolean delete(String valeur) throws SQLException {
        return true;
    }

    public String insert(String valeur) throws SQLException {
        return "ok";
    }

    public String update(String valeur, String valeur2) throws SQLException {
        return "ok";
    }


}
