package fr.univangers.dao;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.SihamIndividuPaye;
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
     * Récuperation de la derniere année initialisé'
     * @return : l'année max de la table rafp_agent
     * @throws SQLException : SQLException
     */
    public String getAnnee() throws SQLException {

        logger.info("Début de la requete de récupération de l'année");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        String annee = "";
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            //Récuperation de l'année n-1
            String requeteAnnee = "select max(annee) as annee from harp_adm.rafp_agent";
            cstmt = maConnexion.prepareStatement(requeteAnnee);
            rs = cstmt.executeQuery();
            if (rs.next()) {
                annee = rs.getString(1);
            }
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de récuperation de l'année");

        return annee;
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
        String annee = getAnnee();
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
                    "     and R.annee = ? ) " +
                    ")";

            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, annee);
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
        String annee = getAnnee();

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
                    "     and R.annee = ?) " +
                    ")";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, annee);
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
        String annee = getAnnee();

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent R set RAFPP=(" +
                    "(select sum(to_number(decode(sens,'A',MONTANT,'-'||MONTANT))) from siham_adm.siham_individu_paye H" +
                    "  where periode_paie like '2024%'" +
                    "     and R.no_dossier_pers=H.no_individu" +
                    "     and type_element='CO'" +
                    "     and compte_comptable='64535100'" +
                    "     and r.annee = ?" +
                    "      )  " +
                    ")";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, annee);
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
        String annee = getAnnee();

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent set seuil=nvl(tbi,0)*0.2 where annee = ?";

            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, annee);
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

            String requete = "update harp_adm.rafp_retour I set base_retour_recalculee_emp=mnt_retour* " +
                    "(select MAX(base_retour_recalculee) from harp_adm.rafp_agent R " +
                    "where I.insee=R.no_insee)/ " +
                    "(select MAX(total_retour) from harp_adm.rafp_agent RR " +
                    "where i.insee=rr.no_insee)";
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
}
