package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.AgentDAO;
import fr.univangers.dao.PersonnelDAO;
import fr.univangers.dao.RetourDAO;
import fr.univangers.exceptions.UAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class RetourService {
    final private RetourDAO dao;
    final private AgentDAO agentDao;


    @Autowired
    public RetourService(RetourDAO dao, AgentDAO agentDAO){
        super();
        this.dao = dao;
        this.agentDao = agentDAO;
    }

    public RafpRetour getRetourByInseeEmployeur(int id_emp, String no_insee) throws SQLException, UAException {
        return dao.getRetourByInseeEmployeur(id_emp, no_insee);
    }

    public boolean updateRetourByInseeEmployeur(int id_emp, String no_insee, int montant) throws SQLException, UAException {
        return dao.updateRetourByInseeEmployeur(id_emp, no_insee, montant);
    }

    public boolean validateImportTotalDataFinal() throws SQLException, UAException {
        return dao.validateImportTotalDataFinal();
    }

    public boolean clearTempData() throws SQLException, UAException {
        return dao.clearTempData();
    }


    public boolean insertImportTotalDataTemp(int idEmp, String no_insee, double montant) throws SQLException, UAException {
        return dao.insertImportTotalDataTemp(idEmp, no_insee, montant);
    }

    public List<RafpImport> getTempImportData() throws SQLException {
        return dao.getTempImportData();
    }



}
