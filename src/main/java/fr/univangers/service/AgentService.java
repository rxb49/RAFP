package fr.univangers.service;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.SihamIndividuPaye;
import fr.univangers.dao.AgentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AgentService {
    final private AgentDAO dao;

    @Autowired
    public AgentService(AgentDAO dao){
        super();
        this.dao = dao;
    }

    //Lors d'un vrai projet : les méthodes delete(), insert() et update() il appelle les méthodes dans la DAO.


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
