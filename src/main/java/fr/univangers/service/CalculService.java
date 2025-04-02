package fr.univangers.service;

import fr.univangers.classes.*;
import fr.univangers.dao.CalculDAO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

        if (zipFile == null || !zipFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Aucun fichier ZIP trouvé.");
            return;
        }

        // Configurer la réponse HTTP pour le téléchargement
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipFile.getName());
        response.setContentLength((int) zipFile.length());

        // Envoyer le fichier ZIP en réponse
        try (FileInputStream fis = new FileInputStream(zipFile);
             OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        dao.deleteZipFiles();


    }



}
