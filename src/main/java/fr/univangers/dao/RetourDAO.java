package fr.univangers.dao;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpEmployeur;
import fr.univangers.classes.RafpLibAgent;
import fr.univangers.classes.RafpRetour;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RetourDAO {
    private final Logger logger = LoggerFactory.getLogger(RetourDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public RetourDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }

    public RafpRetour getRetourByInseeEmployeur(int idEmployeur, String no_insee) throws SQLException {
        logger.info("Début de la requête de récuperation du retour");
        RafpRetour retour = new RafpRetour();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select R.annee, R.insee, R.id_emp, R.mnt_retour, R.base_retour_recalculee_emp from harp_adm.rafp_retour R where insee = ? AND id_emp = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, no_insee);
            cstmt.setInt(2, idEmployeur);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                retour.setAnnee(rs.getInt("annee"));
                retour.setInsee(rs.getString("insee"));
                retour.setId_emp(rs.getInt("id_emp"));
                retour.setMnt_retour(rs.getInt("mnt_retour"));
                retour.setBase_retour_recalculee_emp(rs.getInt("base_retour_recalculee_emp"));

            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation du retour");
        return retour;
    }

    public boolean updateRetourByInseeEmployeur(int idEmployeur, String no_insee, int montant) throws SQLException {
        logger.info("Début de la requête de modification du retour");
        logger.info("Exécution de updateRetourByInseeEmployeur avec idEmp={}, noInsee={}, montant={}", idEmployeur, no_insee, montant);

        RafpRetour retour = new RafpRetour();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;

        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "update harp_adm.rafp_retour set mnt_retour = ? where id_emp = ? AND insee = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setInt(1, montant);
            cstmt.setInt(2, idEmployeur);
            cstmt.setString(3, no_insee);


            int rowsAffected = cstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }else{
                logger.warn("Aucune ligne modifier en base de donnée.");
            }
            logger.info(retour.toString());
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de modification du retour");
        return result;
    }


    public boolean insertImportTotalDataTemp(int id_emp, String no_insee, double montant) throws SQLException, UAException {
        logger.info("Début de la requête d'insertion des retours totaux");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "INSERT INTO harp_adm.rafp_temp (id_emp, insee, retour) VALUES ( ?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            cstmt.setInt(1, id_emp);
            cstmt.setString(2, no_insee);
            cstmt.setDouble(3, montant);

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion des retours totaux");
        return result;
    }

    public boolean validateImportTotalData() throws SQLException {
        logger.info("Validation des données et insertion en base définitive");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            // Insérer les données de la table temporaire vers la table définitive
            String insertQuery = "INSERT INTO harp_adm.rafp_retour (annee, insee, id_emp, mnt_retour) SELECT ?, insee, id_emp, retour FROM harp_adm.rafp_temp";
            cstmt = maConnexion.prepareStatement(insertQuery);
            cstmt.setString(1, "2024");

            int rowsInserted = cstmt.executeUpdate();

            if (rowsInserted > 0) {
                logger.info(rowsInserted + " lignes insérées dans rafp_retour");

                String deleteQuery = "DELETE FROM harp_adm.rafp_temp";
                cstmt = maConnexion.prepareStatement(deleteQuery);
                cstmt.executeUpdate();

                maConnexion.commit();
                result = true;
            } else {
                logger.warn("Aucune donnée à insérer.");
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la validation des données", e);
        } finally {
            Sql.close(maConnexion);
        }
        return result;
    }

    public List<Map<String, Object>> getTempImportData() throws SQLException {
        logger.info("Récupération des données en attente de validation");

        Connection maConnexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> tempData = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "SELECT T.id_emp, E.lib_emp, T.insee, A.nom_usuel, A.prenom, T.retour FROM harp_adm.rafp_temp T " +
                    "INNER JOIN harp_adm.rafp_agent A ON A.no_insee = T.insee " +
                    "INNER JOIN harp_adm.rafp_employeur E ON E.id_emp = T.id_emp WHERE T.insee = A.no_insee";
            stmt = maConnexion.prepareStatement(requete);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_emp", rs.getInt("id_emp"));
                row.put("lib_emp", rs.getString("lib_emp"));
                row.put("insee", rs.getString("insee"));
                row.put("nom_usuel", rs.getString("nom_usuel"));
                row.put("prenom", rs.getString("prenom"));
                row.put("retour", rs.getDouble("retour"));
                tempData.add(row);
            }
            logger.info(tempData.toString());
        } finally {
            Sql.close(maConnexion);
        }
        return tempData;
    }

    public boolean clearTempData() throws SQLException {
        logger.info("Suppression des données temporaires");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "DELETE FROM harp_adm.rafp_temp";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.executeUpdate();
            int rowsDeleted = cstmt.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info(rowsDeleted + " lignes supprime dans rafp_temp");
                result = true;
            }
            logger.info("Données temporaires supprimées avec succès.");
        } finally {
            Sql.close(maConnexion);
        }
        return result;
    }

}
