package fr.univangers.service;

import fr.univangers.utils.ZipFileUtils;
import fr.univangers.classes.*;
import fr.univangers.dao.CalculDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class CalculService {
    final private CalculDAO dao;

    @Autowired
    public CalculService(CalculDAO dao){
        super();
        this.dao = dao;
    }

    public boolean setTBI() throws SQLException {
        return dao.setTBI();
    }

    public boolean setIndemn() throws SQLException {
        return dao.setIndemn();
    }

    public boolean setRafpp() throws SQLException {
        return dao.setRafpp();
    }

    public boolean setSeuil() throws SQLException {
        return dao.setSeuil();
    }

    public boolean calculBaseRetourRecalculeeEmp() throws SQLException {
        return dao.calculBaseRetourRecalculeeEmp();
    }

    public List<DonneesCSV> getDataEmployeurCSV() throws SQLException {
        return dao.getDataEmployeurCSV();
    }

    public boolean generateCSVEmployeur(List<DonneesCSV> donnees) throws SQLException, IOException {
        return dao.generateCSVEmployeur(donnees);
    }

    public List<DonneesCSV> getDataAgentCSV() throws SQLException {
        return dao.getDataAgentCSV();
    }

    public boolean generateCSVagent(List<DonneesCSV> donnees) throws SQLException, IOException {
        return dao.generateCSVagent(donnees);
    }

    public void downloadAllFiles(HttpServletResponse response) throws IOException {
        File zipFile = dao.createZipFile();
        ZipFileUtils.sendZipFile(zipFile, response);
        dao.deleteZipFiles();

    }



}
