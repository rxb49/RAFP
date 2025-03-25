package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.PersonnelDAO;
import fr.univangers.dao.RetourDAO;
import fr.univangers.exceptions.UAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RetourService {
    final private RetourDAO dao;

    @Autowired
    public RetourService(RetourDAO dao){
        super();
        this.dao = dao;
    }

    public RafpRetour getRetourByInseeEmployeur(int id_emp, String no_insee) throws SQLException, UAException {
        return dao.getRetourByInseeEmployeur(id_emp, no_insee);
    }

    public boolean updateRetourByInseeEmployeur(int id_emp, String no_insee, int montant) throws SQLException, UAException {
        return dao.updateRetourByInseeEmployeur(id_emp, no_insee, montant);
    }

    public boolean insertImportTotalData(String lbl_emp, String no_insee, double montant) throws SQLException, UAException {
        return dao.insertImportTotalData(lbl_emp, no_insee, montant);
    }



}
