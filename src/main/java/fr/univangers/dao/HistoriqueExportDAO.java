package fr.univangers.dao;

import fr.univangers.sql.OracleConfiguration;
import fr.univangers.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class HistoriqueExportDAO {
    private final Logger logger = LoggerFactory.getLogger(HistoriqueExportDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public HistoriqueExportDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }

    /**
     * Ajoute dans rafp_historique une ligne avec la date de la generation des csv et son etat '
     * @return : vrai ou faux si la ligne est bien insérer
     * @throws SQLException : SQLException
     */
    public boolean insertHistoriqueExport() throws SQLException {

        logger.info("Début de la requête d'insertion de l'hitorique d'export");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        String etat = "T";
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "INSERT INTO harp_adm.rafp_his_export (date_export, etat) VALUES ( sysdate, ?)";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, etat);

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion d'historique d'export");
        return result;
    }

    /**
     * regarde dans la table rafp_his_export si une ligne est a T pour afficher ou non le boutton de téléchargement du zip '
     * @return : vrai ou faux si la ligne est présente
     * @throws SQLException : SQLException
     */
    public boolean checkEtat(String etat) throws SQLException {
        logger.info("Début de la requête de vérification si un ligne a l'état T");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean result = false;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
                String requete = "SELECT COUNT(etat) FROM harp_adm.rafp_his_export WHERE etat = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, etat);
            rs = cstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                result = count > 0;
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de vérification si une ligne à l'étét T" + result);
        return result;
    }


    /**
     * recupere le dernièr historique en fonction de l'etat '
     * @return : la date recuperer
     * @throws SQLException : SQLException
     */
    public String getDernierEtat(String etat) throws SQLException {
        logger.info("Début de la requête de récuperation du dernier etat");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        String lastDate = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "SELECT TO_CHAR(MAX(date_export), 'DD-MM-YYYY HH24:MI:SS') AS derniere_date FROM harp_adm.rafp_his_export WHERE etat = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, etat);
            rs = cstmt.executeQuery();
            if (rs.next()) {
                lastDate = rs.getString(1);
            }
            if (lastDate == null) {
                logger.warn("Aucune génération trouvée");
            } else {
                logger.info("Dernière génération trouvée : " + lastDate);
            }            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation du dernier etat");
        return lastDate;
    }



    /**
     * Ajoute dans rafp_historique une ligne avec la date de calcul de la RAFP  pour les agents et employeurs et son etat '
     * @return : vrai ou faux si la ligne est bien insérer
     * @throws SQLException : SQLException
     */
    public boolean insertHistoriqueCalcul() throws SQLException {

        logger.info("Début de la requête d'insertion de l'hitorique de calcul");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        String etat = "C";
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "INSERT INTO harp_adm.rafp_his_export (date_export, etat) VALUES ( sysdate, ?)";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, etat);

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion d'historique de calcul");
        return result;
    }


    /**
     * Ajoute dans rafp_historique une ligne avec la date d'import d'un fichier CSV  '
     * @return : vrai ou faux si la ligne est bien insérer
     * @throws SQLException : SQLException
     */
    public boolean insertHistoriqueImport() throws SQLException {

        logger.info("Début de la requête d'insertion de l'historique d'import CSV'");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        String etat = "I";
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "INSERT INTO harp_adm.rafp_his_export (date_export, etat) VALUES ( sysdate, ?)";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, etat);

            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                result = true;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion d'historique d'import CSV");
        return result;
    }
}
