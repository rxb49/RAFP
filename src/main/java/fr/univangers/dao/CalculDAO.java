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
                    "   where periode_paie like '2024%'" +
                    "     and R.no_dossier_pers=H.no_individu" +
                    "     and type_element='BR'" +
                    "     and montant <>0" +
                    "     and tem_rafp is null" +
                    "     and r.annee = '2024' ) " +
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
                    "     and r.annee = '2024')" +
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
                    "     and r.annee = '2024'" +
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

    public boolean setSeuil() throws SQLException {

        logger.info("Début de la requete d'ajout du Seuil pour les agents n-1");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        boolean vRetour = false;
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " update harp_adm.rafp_agent set seuil=nvl(tbi,0)*0.2 where annee = '2024'";

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
}
