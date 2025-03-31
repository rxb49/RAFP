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
public class CalculDAO {
    private final Logger logger = LoggerFactory.getLogger(CalculDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public CalculDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }


    /**
     * Calcul e TBI pour les agents ayant travailler en 2024 en ajoutant tous leurs paye de l'année n-1'
     * @return : les dans la table rafp_agent la valeur du tbi pour chaque agent à l'année n-1
     * @throws SQLException : SQLException
     */
    public boolean setTBI() throws SQLException {

        logger.info("Début de la requete d'ajout du TBI pour les agents n-1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent R set TBI=(" +
                    "(select sum(to_number(decode(sens,'A',MONTANT,'-'||MONTANT))) from siham_adm.siham_individu_paye H" +
                    "   where H.periode_paie like '2024%'" +
                    "     and R.no_dossier_pers=H.no_individu" +
                    "     and H.type_element='BR'" +
                    "     and H.montant <>0" +
                    "     and H.tem_rafp is null" +
                    "     and R.annee like (select max(annee) from harp_adm.rafp_agent) ) " +
                    ")";

            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete d'ajout du TBI pour les agents n-1");

        return vRetour;
    }

    /**
     * Calcul l'indemnité pour les agents ayant travailler en 2024 en ajoutant tous leurs paye de l'année n-1'
     * @return : les dans la table rafp_agent la valeur de indemn pour chaque agent à l'année n-1
     * @throws SQLException : SQLException
     */
    public boolean setIndemn() throws SQLException {

        logger.info("Début de la requete d'ajout des Indemn pour les agents n-1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent R set Indemn=(" +
                    "(select sum(to_number(decode(sens,'A',MONTANT,'-'||MONTANT))) from siham_adm.siham_individu_paye H" +
                    "   where periode_paie like '2024%'" +
                    "     and R.no_dossier_pers=H.no_individu" +
                    "     and type_element='BR'" +
                    "     and montant <>0" +
                    "     and tem_rafp='O'" +
                    "     and R.annee like (select max(annee) from harp_adm.rafp_agent)) " +
                    ")";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete d'ajout des Indemn pour les agents n-1");

        return vRetour;
    }

    /**
     * Calcul la RAFPP pour les agents ayant travailler en 2024 en ajoutant tous leurs paye de l'année n-1'
     * @return : les dans la table rafp_agent la valeur de RAFPP pour chaque agent à l'année n-1
     * @throws SQLException : SQLException
     */

    public boolean setRafpp() throws SQLException {

        logger.info("Début de la requete d'ajout de la RAFPP pour les agents n-1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent R set RAFPP=(" +
                    "(select sum(to_number(decode(sens,'A',MONTANT,'-'||MONTANT))) from siham_adm.siham_individu_paye H" +
                    "  where periode_paie like '2024%'" +
                    "     and R.no_dossier_pers=H.no_individu" +
                    "     and type_element='CO'" +
                    "     and compte_comptable='64535100'" +
                    "     and r.annee like (select max(annee) from harp_adm.rafp_agent)" +
                    "      )  " +
                    ")";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete d'ajout de la RAFPP pour les agents n-1");

        return vRetour;
    }

    /**
     * Calcul le Seuil pour les agents ayant travailler en 2024 en ajoutant tous leurs paye de l'année n-1'
     * @return : les dans la table rafp_agent la valeur du Seuil pour chaque agent à l'année n-1
     * @throws SQLException : SQLException
     */
    public boolean setSeuil() throws SQLException {

        logger.info("Début de la requete d'ajout du Seuil pour les agents n-1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent set seuil=nvl(tbi,0)*0.2 where annee like (select max(annee) from harp_adm.rafp_agent)";

            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete d'ajout du Seuil pour les agents n-1");

        return vRetour;
    }



    /**
     * Calcul la base retour recalculer a envoyé aux employeur pour les agents ayant travailler en 2024 en ajoutant tous leurs paye de l'année n-1'
     * @return : les dans la table rafp_retour la base retour recalcule pour chaque agent
     * @throws SQLException : SQLException
     */
    public boolean calculBaseRetourRecalculeeEmp() throws SQLException {

        logger.info("Début de la requete de calcul de la base retour recalculee employeur");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "update harp_adm.rafp_retour I set base_retour_recalculee_emp=ROUND(mnt_retour* " +
                    " (select MAX(base_retour_recalculee) from harp_adm.rafp_agent R " +
                    " where I.insee=R.no_insee)/ " +
                    " (select MAX(total_retour) from harp_adm.rafp_agent RR" +
                    " inner join harp_adm.rafp_retour I ON i.insee = rr.no_insee" +
                    " where i.insee=rr.no_insee), 2)";
            logger.info(requete);
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            rs.close();
            cstmt.close();

            vRetour = true;

        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de calcul de la base retour recalculee employeur");

        return vRetour;
    }


    /**
     * Récupere les données à implémenter dans le fichier CSV employeur pour tous les employeurs'
     * @return : les données des employeurs qui on des retours
     * @throws SQLException : SQLException
     */
    public boolean getDataEmployeurCSV() throws SQLException {

        logger.info("Début de la requete de generation des CSV pour les employeurs");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;
        List<EmployeurCSV> employeurCSVList = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select A.nom_usuel, A.prenom, a.no_insee, R.mnt_retour, R.base_retour_recalculee_emp, " +
                    "R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Salarial_RAFP\", R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Patronal_RAFP\"," +
                    "R.base_retour_recalculee_emp* 0.1 AS \"Total_Cotisation_RAFP\"" +
                    "from harp_adm.rafp_retour R inner join harp_adm.rafp_agent A ON a.no_insee = r.insee " +
                    "WHERE R.annee = '2024' AND A.annee = '2024'  ORDER BY R.id_emp ";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                EmployeurCSV employeur = new EmployeurCSV();
                employeur.setNom_usuel(rs.getString("nom_usuel"));
                employeur.setPrenom(rs.getString("prenom"));
                employeur.setNo_insee(rs.getString("no_insee"));
                employeur.setMnt_retour(rs.getDouble("mnt_retour"));
                employeur.setBase_retour_recalculee_emp(rs.getDouble("base_retour_recalculee_emp"));
                employeur.setSalaraialRafp(rs.getDouble("Cotisation_Salarial_RAFP"));
                employeur.setPatronalRafp(rs.getDouble("Cotisation_Patronal_RAFP"));
                employeur.setTotalRafp(rs.getDouble("Total_Cotisation_RAFP"));

                employeurCSVList.add(employeur);

            }

            logger.info(employeurCSVList.toString());
            rs.close();
            cstmt.close();
            vRetour = true;

        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de generation des CSV pour les employeurs");

        return vRetour;
    }



}
