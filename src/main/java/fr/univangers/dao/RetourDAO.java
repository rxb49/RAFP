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
import java.util.List;

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


}
