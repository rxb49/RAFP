package fr.univangers.service;

import fr.univangers.classes.DonneesCSV;
import fr.univangers.dao.CalculDAO;
import fr.univangers.dao.HistoriqueExportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
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

    public boolean checkEtatT() throws SQLException {
        return dao.checkEtatT();
    }



}
