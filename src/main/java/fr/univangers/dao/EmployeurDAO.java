package fr.univangers.dao;

import fr.univangers.classes.*;
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

    public RafpEmployeur insertEmployeur(String nomEmployeur, String mailEmployeur) throws SQLException {
        logger.info("Début de la requête d'insertion de l'employeur");
        if (nomEmployeur == null || mailEmployeur == null || nomEmployeur == "" || mailEmployeur == "") {
            throw new SQLException("Le nom et l'email de l'employeur ne peuvent pas être null");
        }
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        RafpEmployeur ajouterEmployeur = new RafpEmployeur();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Vérifier si l'employeur existe déjà
            String verificationQuery = "SELECT COUNT(*) FROM harp_adm.rafp_employeur WHERE lib_emp = ? AND mail_emp = ?";
            PreparedStatement verificationStmt = maConnexion.prepareStatement(verificationQuery);
            verificationStmt.setString(1, nomEmployeur);
            verificationStmt.setString(2, mailEmployeur);
            ResultSet resultSet = verificationStmt.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                throw new SQLException("L'employeur existe déjà.");
            }

            // Obtenir l'id maximum existant
            String maxIdQuery = "SELECT MAX(id_emp) FROM harp_adm.rafp_employeur";
            PreparedStatement maxIdStmt = maConnexion.prepareStatement(maxIdQuery);
            ResultSet maxIdResultSet = maxIdStmt.executeQuery();
            maxIdResultSet.next();
            int maxId = maxIdResultSet.getInt(1);
            int newId = maxId + 1;

            // Insertion de l'agent
            String requete = "INSERT INTO harp_adm.rafp_employeur (id_emp, lib_emp, mail_emp) VALUES (?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            // Définir les valeurs des paramètres avant l'exécution
            cstmt.setInt(1, newId);
            cstmt.setString(2, ajouterEmployeur.setLib_emp(nomEmployeur));
            cstmt.setString(3, ajouterEmployeur.setMail_emp(mailEmployeur));
            // Exécuter la requête d'insertion

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                ajouterEmployeur.setLib_emp(nomEmployeur);
                ajouterEmployeur.setMail_emp(mailEmployeur);

            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            Sql.close(cstmt);
        }
        finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }
        Sql.close(cstmt);
        Sql.close(maConnexion);
        logger.info("Fin de la requête d'insertion de l'employeur");
        return ajouterEmployeur;
    }

    public List<RafpEmployeur> getEmployeur() throws SQLException {
        logger.info("Début de la requête d'insertion de l'employeur");

        List<RafpEmployeur> employeurs = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select * from harp_adm.rafp_employeur";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            // Exécuter la requête d'insertion

            while (rs.next()) {
                RafpEmployeur employeur = new RafpEmployeur();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setMail_emp(rs.getString("mail_emp"));
                employeurs.add(employeur);
                logger.info(employeur.getLib_emp());
            }
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();
        }finally {
        Sql.close(maConnexion);
        }
        return employeurs;
    }
}
