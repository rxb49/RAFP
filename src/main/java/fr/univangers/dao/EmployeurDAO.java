package fr.univangers.dao;

import fr.univangers.classes.*;
import fr.univangers.exceptions.UAException;
import fr.univangers.sql.OracleConfiguration;
import fr.univangers.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeurDAO {
    private final Logger logger = LoggerFactory.getLogger(EmployeurDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public EmployeurDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }

    public boolean insertEmployeur(RafpEmployeur employeur) throws SQLException, UAException {
        logger.info("Début de la requête d'insertion de l'employeur");
        if (employeur.getLib_emp() == null || employeur.getMail_emp() == null || employeur.getLib_emp().isEmpty() || employeur.getMail_emp().isEmpty()) {
            throw new UAException("Le nom et l'email de l'employeur ne peuvent pas être null");
        }
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Vérifier si l'employeur existe déjà
            String verificationQuery = "SELECT COUNT(lib_emp) nb FROM harp_adm.rafp_employer WHERE lib_emp = ? AND mail_emp = ?";
            PreparedStatement verificationStmt = maConnexion.prepareStatement(verificationQuery);
            verificationStmt.setString(1, employeur.getLib_emp());
            verificationStmt.setString(2, employeur.getMail_emp());
            ResultSet resultSet = verificationStmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt("nb") > 0) {
                    logger.info("L'employeur existe déjà");
                    throw new UAException("L'employeur existe déjà.");
                }
            }
            Sql.close(resultSet);

            // Obtenir l'id maximum existant
            String maxIdQuery = "SELECT MAX(id_emp) FROM harp_adm.rafp_employer";
            PreparedStatement maxIdStmt = maConnexion.prepareStatement(maxIdQuery);
            ResultSet maxIdResultSet = maxIdStmt.executeQuery();
            maxIdResultSet.next();
            int maxId = maxIdResultSet.getInt(1);
            int newId = maxId + 1;

            // Insertion de l'agent
            String requete = "INSERT INTO harp_adm.rafp_employer (id_emp, lib_emp, mail_emp) VALUES (?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            // Définir les valeurs des paramètres avant l'exécution
            cstmt.setInt(1, newId);
            cstmt.setString(2, employeur.getLib_emp());
            cstmt.setString(3, employeur.getMail_emp());
            // Exécuter la requête d'insertion

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            Sql.close(maxIdResultSet);
        }
        finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requête d'insertion de l'employeur");
        return result;
    }

    public List<RafpEmployeur> getEmployeur() throws SQLException {
        logger.info("Début de la requête de récuperation des employeurs");

        List<RafpEmployeur> employeurs = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select id_emp, lib_emp, mail_emp from harp_adm.rafp_employer order by lib_emp ASC";
            // Exécuter la requête de récuperation
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpEmployeur employeur = new RafpEmployeur();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setMail_emp(rs.getString("mail_emp"));
                employeurs.add(employeur);
            }
            rs.close();
            cstmt.close();
        }finally {
        Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation des employeurs");
        return employeurs;
    }

    public List<RafpEmployeur> getEmployeurBySearch(String recherche) throws SQLException {
        logger.info("Début de la requête de recherche des employeurs");
        List<RafpEmployeur> employeurs = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "SELECT distinct id_emp, lib_emp, mail_emp FROM harp_adm.rafp_employer " +
                    "WHERE lib_emp LIKE ? " +
                    "   OR mail_emp LIKE ? ";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, "%" + recherche + "%");
            cstmt.setString(2, "%" + recherche + "%");
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpEmployeur employeur = new RafpEmployeur();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setMail_emp(rs.getString("mail_emp"));
                employeurs.add(employeur);
            }
            logger.info(employeurs.toString());
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de recherche des employeurs");
        return employeurs;
    }

    public boolean updateEmployeur(RafpEmployeur rafpEmployeur) throws SQLException, UAException {
        logger.info("Début de la requête de modification d'un employeur");
        logger.info(rafpEmployeur.getLib_emp());
        if (rafpEmployeur.getLib_emp() == null || rafpEmployeur.getMail_emp() == null || rafpEmployeur.getLib_emp().isEmpty() || rafpEmployeur.getMail_emp().isEmpty()) {
            logger.info("Le nom et l'email de l'employeur ne peuvent pas être null");
            throw new UAException("Le nom et l'email de l'employeur ne peuvent pas être null");
        }
        boolean result = false;
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            //Vérifier si l'employeur existe déjà
            String verificationQuery = "SELECT COUNT(lib_emp) nb FROM harp_adm.rafp_employer WHERE lib_emp = ? AND mail_emp = ?";
            PreparedStatement verificationStmt = maConnexion.prepareStatement(verificationQuery);
            verificationStmt.setString(1, rafpEmployeur.getMail_emp());
            verificationStmt.setString(2, rafpEmployeur.getLib_emp());
            ResultSet resultSet = verificationStmt.executeQuery();
            resultSet.next();
            if (resultSet.getInt("nb") > 0) {
                logger.info("L'employeur existe déjà.");
                throw new UAException("L'employeur existe déjà.");
            }
            Sql.close(resultSet);

            String requete = "update harp_adm.rafp_employer set lib_emp = ?, mail_emp = ? where id_emp = ?";
            cstmt = maConnexion.prepareStatement(requete);
            // Exécuter la requête d'insertion

            cstmt.setString(1, rafpEmployeur.getMail_emp());
            cstmt.setString(2, rafpEmployeur.getLib_emp());
            cstmt.setInt(3, rafpEmployeur.getId_emp());
            int rowsAffected = cstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }else{
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
        } finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de modification d'un employeur");
        return result;
    }

    public RafpEmployeur getEmployeurById(int idEmployeur) throws SQLException {
        logger.info("Début de la requête de récuperation de l'employeurs");
        RafpEmployeur employeur = new RafpEmployeur();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select E.id_emp, E.lib_emp, E.mail_emp from harp_adm.rafp_employer E where E.id_emp = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setInt(1, idEmployeur);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setMail_emp(rs.getString("mail_emp"));
            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation de l'employeurs");
        return employeur;
    }


    public List<RafpAgentRetour> getAgentByEmployeurId(int idEmployeur) throws SQLException {
        logger.info("Début de la requête de récuperation des agents pour un employeur");
        logger.info("Id de l'employeur " + idEmployeur);
        List<RafpAgentRetour> agents = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select distinct R.id_emp, a.nom_usuel, A.prenom ,R.insee, R.mnt_retour from harp_adm.rafp_retour R " +
                    "               inner join harp_adm.rafp_agent A on R.insee = A.no_insee where id_emp = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setInt(1, idEmployeur);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpAgentRetour employeur = new RafpAgentRetour();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setNom_usuel(rs.getString("nom_usuel"));
                employeur.setPrenom(rs.getString("prenom"));
                employeur.setInsee(rs.getString("insee"));
                employeur.setMnt_retour(rs.getInt("mnt_retour"));
                agents.add(employeur);
            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation des agents pour un employeur");
        return agents;
    }

    public boolean insertEmployeurAdd(String no_insee, int id_emp, int montant) throws SQLException, UAException {
        logger.info("Début de la requête d'insertion du retour");
        if (no_insee == null || id_emp == 0  || montant == 0 || no_insee.isEmpty()) {
            throw new UAException("Le montant et l'employeur ne peuvent pas être null");
        }
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Vérifier si le retour existe déjà
            String verificationQuery = "SELECT COUNT(insee) nb FROM harp_adm.rafp_retour WHERE  insee = ? AND id_emp = ?";
            PreparedStatement verificationStmt = maConnexion.prepareStatement(verificationQuery);
            verificationStmt.setString(1, no_insee);
            verificationStmt.setInt(2, id_emp);
            ResultSet resultSet = verificationStmt.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt("nb") > 0) {
                    logger.info("Le retour existe déjà");
                    throw new UAException("Le retour existe déjà.");
                }
            }
            Sql.close(resultSet);


            // Insertion de l'agent
            String requete = "insert into harp_adm.rafp_retour (annee, insee, id_emp, mnt_retour) VALUES (?, ?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            // Définir les valeurs des paramètres avant l'exécution
            cstmt.setString(1, "2023");
            cstmt.setString(2, no_insee);
            cstmt.setInt(3, id_emp);
            cstmt.setInt(4, montant);
            // Exécuter la requête d'insertion

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
        }
        finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requête d'insertion du retour");
        return result;
    }
}
