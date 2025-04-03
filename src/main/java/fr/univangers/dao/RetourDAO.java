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
public class RetourDAO {
    private final Logger logger = LoggerFactory.getLogger(RetourDAO.class);
    private final AgentDAO agentdao;
    private final OracleConfiguration oracleConfiguration;

    public RetourDAO(AgentDAO agentdao, OracleConfiguration oracleConfiguration) {
        this.agentdao = agentdao;
        this.oracleConfiguration = oracleConfiguration;
    }

    /**
     * Insere les retours d'un fichier csv dans la table rafp_retour et met  à jour le total retour des agents concerné dans la table rafp_agent '
     * @return : vrai ou faux si les lignes sont modifié
     * @throws SQLException : SQLException
     */
    public boolean validateImportTotalDataFinal() throws SQLException, UAException {
        List<String> insertedNoInsee = getInsertedNoInsee();
        boolean insertionReussie = validateImportTotalData();
        logger.info("Données: " + insertionReussie);

        if (insertionReussie) {
            for (String noInsee : insertedNoInsee) {
                agentdao.updateTotalRetourByAgent(noInsee);
            }
        }
        return insertionReussie;
    }

    /**
     * recupere les retour de la table rafp_retour en fonction d'un noInsee et d'un employeur '
     * @return : le retour concerné
     * @throws SQLException : SQLException
     */
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

    /**
     * Modifie un retour en fonction d'un insee et d'un employeur '
     * @return : vrai ou faux si la ligne à été modifié
     * @throws SQLException : SQLException
     */

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


    /**
     * Insere dans rafp_temp les données du fichier csv '
     * @return : vrai ou faux si la ligne à été insérée
     * @throws SQLException : SQLException
     */
    public boolean insertImportTotalDataTemp(int id_emp, String no_insee, double montant) throws SQLException, UAException {
        logger.info("Début de la requête d'insertion des retours totaux temporaires");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean result = false;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();
            logger.info("Verification si rafp_retour");
            // Vérification de l'existence des données dans l'autre table
            String checkQuery = "SELECT COUNT(R.id_emp) nb FROM harp_adm.rafp_retour R WHERE R.id_emp = ? AND R.insee = ? " +
                    "AND R.annee = (select max(A.annee) from harp_adm.rafp_agent A)";
            cstmt = maConnexion.prepareStatement(checkQuery);
            cstmt.setInt(1, id_emp);
            cstmt.setString(2, no_insee);
            rs = cstmt.executeQuery();
            if (rs.next() && rs.getInt("nb") > 0) {
                logger.warn("Les données existent déjà dans l'autre table, insertion annulée.");
                String deleteQuery = "DELETE FROM harp_adm.rafp_temp";
                cstmt = maConnexion.prepareStatement(deleteQuery);
                cstmt.executeUpdate();
            }
            else{

                String requete = "INSERT INTO harp_adm.rafp_temp (id_emp, insee, retour) VALUES ( ?, ?, ?)";
                cstmt = maConnexion.prepareStatement(requete);

                cstmt.setInt(1, id_emp);
                cstmt.setString(2, no_insee);
                cstmt.setDouble(3, montant);

                int rowsInserted = cstmt.executeUpdate();
                if (rowsInserted > 0) {
                    logger.info("Insertion en base de donnée réussie !");
                    result = true;
                }else {
                    logger.warn("Aucune ligne insérée en base de donnée.");
                }
            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion des retours totaux temporaires");
        return result;
    }

    /**
     * Recupere les noInsee qui on été insérée dans la table rafp_temp de la methode insertImportTotalDataTemp '
     * @return : Liste des noInsee de la table rapf_temp
     * @throws SQLException : SQLException
     */
    private List<String> getInsertedNoInsee() throws SQLException {
        List<String> noInseeList = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "SELECT DISTINCT insee FROM harp_adm.rafp_temp";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                noInseeList.add(rs.getString("insee"));
            }
            cstmt.close();
            rs.close();
        } finally {
            Sql.close(maConnexion);
        }
        return noInseeList;
    }

    /**
     * Recalcule la base_retour_recalculee de la table rafp_agent si le total_retour est inférieur à la base_restante  '
     * @return : vrai ou faux si la ligne à été modifie
     * @throws SQLException : SQLException
     */
    public boolean updateBaseRetour1() throws SQLException {
        logger.info("Début de la requete de calcul de la base retour recalculee1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "update harp_adm.rafp_agent set base_retour_recalculee = total_retour where total_retour < base_restante";
            logger.info(requete);
            cstmt = maConnexion.prepareStatement(requete);
            int rowsAffected = cstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }else{
                logger.warn("Aucune ligne modifier en base de donnée.");
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de modification du retour");
        return result;
    }

    /**
     * Recalcule la base_retour_recalculee de la table rafp_agent si le total_retour est suprieur ou égal à la base_restant '
     * @return : vrai ou faux si la ligne à été modifie
     * @throws SQLException : SQLException
     */
    public boolean updateBaseRetour2() throws SQLException {
        logger.info("Début de la requete de calcul de la base retour recalculee2");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "update harp_adm.rafp_agent set base_retour_recalculee = base_restante where total_retour >=base_restante";
            logger.info(requete);
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            int rowsAffected = cstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }else{
                logger.warn("Aucune ligne modifier en base de donnée.");
            }
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de modification du retour");
        return result;
    }


    /**
     * Insère dans rafp_retour les données de rafp_temp et supprime si l'insertion est effectué les données de rafp_temp '
     * @return : vrai ou faux si les lignes on été insérées/supprimées
     * @throws SQLException : SQLException
     */
    private boolean validateImportTotalData() throws SQLException {
        logger.info("Validation des données et insertion en base définitive");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean result = false;
        String annee = "";
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            //Récuperation de l'année n-1
            String requete = "select max(annee) as annee from harp_adm.rafp_agent";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            if (rs.next()) {
                annee = rs.getString(1);
            }
            // Vérifier si une des données est déjà présente
            String checkQuery = """
            SELECT COUNT(*) FROM harp_adm.rafp_temp t
            INNER JOIN harp_adm.rafp_retour r ON t.id_emp = r.id_emp AND t.insee = r.insee AND r.annee = ? """;
            cstmt = maConnexion.prepareStatement(checkQuery);
            cstmt.setString(1, annee);
            rs = cstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                logger.warn("Erreur : Des données existent déjà dans la table définitive !");
                String deleteQuery = "DELETE FROM harp_adm.rafp_temp";
                cstmt = maConnexion.prepareStatement(deleteQuery);
                cstmt.executeUpdate();

                maConnexion.commit();
                return result;
            }
            // update le montant total de la table rafp_agent a faire avec les données de rrafp_retour
            // Insérer les données de la table temporaire vers la table définitive
            String insertQuery = "INSERT INTO harp_adm.rafp_retour (annee, insee, id_emp, mnt_retour) SELECT ?, insee, id_emp, retour FROM harp_adm.rafp_temp";
            cstmt = maConnexion.prepareStatement(insertQuery);
            cstmt.setString(1, annee);

            int rowsInserted = cstmt.executeUpdate();
            String insertQueryTable3 = "UPDATE harp_adm.rafp_agent A set A.total_retour = (select sum(R.mnt_retour) from harp_adm.rafp_retour R where R.insee=A.no_insee) where A.annee = ?";
            cstmt = maConnexion.prepareStatement(insertQueryTable3);
            cstmt.setString(1, annee);
            cstmt.executeUpdate();

            if (rowsInserted > 0) {
                logger.info(rowsInserted + " lignes insérées dans rafp_retour");

                String deleteQuery = "DELETE FROM harp_adm.rafp_temp";
                cstmt = maConnexion.prepareStatement(deleteQuery);
                cstmt.executeUpdate();

                maConnexion.commit();
                updateBaseRetour1();
                updateBaseRetour2();
                result = true;
            } else {
                logger.warn("Aucune donnée à insérer.");
            }
            cstmt.close();
        } catch (Exception e) {
            logger.error("Erreur lors de la validation des données", e);
        } finally {
            Sql.close(maConnexion);
        }
        return result;
    }


    /**
     * Recupere les données de la table rafp_temp '
     * @return : la liste des données de rafp_temp
     * @throws SQLException : SQLException
     */
    public List<RafpImport> getTempImportData() throws SQLException {
        logger.info("Récupération des données en attente de validation");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        List<RafpImport> tempData = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            String requete = "SELECT T.id_emp, E.lib_emp, T.insee, A.nom_usuel, A.prenom, T.retour FROM harp_adm.rafp_temp T " +
                    "INNER JOIN harp_adm.rafp_agent A ON A.no_insee = T.insee " +
                    "INNER JOIN harp_adm.rafp_employeur E ON E.id_emp = T.id_emp WHERE T.insee = A.no_insee";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                RafpImport importData = new RafpImport();
                importData.setId_emp(rs.getInt("id_emp"));
                importData.setLib_emp(rs.getString("lib_emp"));
                importData.setInsee(rs.getString("insee"));
                importData.setNom_usuel(rs.getString("nom_usuel"));
                importData.setPrenom(rs.getString("prenom"));
                importData.setRetour(rs.getDouble("retour"));
                tempData.add(importData);
            }
            logger.info(tempData.toString());
            cstmt.close();
            rs.close();
        } finally {
            Sql.close(maConnexion);
        }
        return tempData;
    }

    /**
     * Supprime toutes les données de rafp_temp '
     * @return : vrai ou faux si les lignes on été supprimé
     * @throws SQLException : SQLException
     */
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
            cstmt.close();
        } finally {
            Sql.close(maConnexion);
        }
        return result;
    }

}
