package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.AgentDAO;
import fr.univangers.exceptions.UAException;
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

    public RafpAgent insertAgent(RafpAgent agent) throws SQLException {
        return dao.insertAgent(agent);
    }

    public List<RafpLibAgent> getAgent() throws SQLException {
        return dao.getAgent();
    }

    public List<RafpLibAgent> getAgentBySearch(String recherche) throws SQLException {
        return dao.getAgentBySearch(recherche);
    }

    public List<RafpRetour> getEmployeurByAgent(String no_insee) throws SQLException {
        return dao.getEmployeurByAgent(no_insee);
    }

    public RafpAgent getAgentByNoInsee(String no_insee) throws SQLException {
        return dao.getAgentByNoInsee(no_insee);
    }

    public boolean updateTotalRetourByAgent(String no_insee) throws SQLException, UAException {
        return dao.updateTotalRetourByAgent(no_insee);
    }





}
