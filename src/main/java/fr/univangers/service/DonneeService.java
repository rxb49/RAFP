package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.controller.DonneeController;
import fr.univangers.dao.AgentDAO;
import fr.univangers.dao.DonneeDAO;
import fr.univangers.dao.EmployeurDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DonneeService {
    final private DonneeDAO dao;

    @Autowired
    public DonneeService(DonneeDAO dao){
        super();
        this.dao = dao;
    }

    public List<RafpEmployeur> getEmployeur() throws SQLException {
        return dao.getEmployeur();
    }

    public List<RafpAgentRetour> getAgentByEmployeurId(int idEmployeur) throws SQLException {
        return dao.getAgentByEmployeurId(idEmployeur);
    }

    public List<RafpLibAgent> getAgent() throws SQLException {
        return dao.getAgent();
    }

    public RafpAgent getInfoAgentById(String no_insee) throws SQLException {
        return dao.getInfoAgentById(no_insee);
    }

    public List<RafpRetour> getEmployeurByAgent(String no_insee) throws SQLException {
        return dao.getEmployeurByAgent(no_insee);
    }

}
