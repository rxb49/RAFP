package fr.univangers.service;

import fr.univangers.classes.DonneesCSV;
import fr.univangers.dao.CalculDAO;
import fr.univangers.dao.HistoriqueExportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class HistoriqueService {
    final private HistoriqueExportDAO dao;

    @Autowired
    public HistoriqueService(HistoriqueExportDAO dao){
        super();
        this.dao = dao;
    }


    public boolean insertHistoriqueExport() throws SQLException {
        return dao.insertHistoriqueExport();
    }

    public String getDernierEtat(String etat) throws SQLException {
        return dao.getDernierEtat(etat);
    }

    public boolean checkEtat(String etat) throws SQLException {
        return dao.checkEtat(etat);
    }

    public boolean insertHistoriqueCalcul() throws SQLException {
        return dao.insertHistoriqueCalcul();
    }


    public boolean insertHistoriqueImport() throws SQLException {
        return dao.insertHistoriqueImport();
    }



}
