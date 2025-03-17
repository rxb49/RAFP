package fr.univangers.dao;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpAgentEmployeur;
import fr.univangers.classes.RafpAgentRetour;
import fr.univangers.classes.RafpEmployeur;
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
public class DonneeDAO {
    private final Logger logger = LoggerFactory.getLogger(DonneeDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public DonneeDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
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


    public List<RafpAgentRetour> getAgentByEmployeurId(int idEmployeur) throws SQLException {
        logger.info("Début de la requête de récuperation des agents pour un employeur");

        List<RafpAgentRetour> agents = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select id_emp, insee, mnt_retour from harp_adm.rafp_retour where id_emp = ?";
            // Exécuter la requête de récuperation
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setInt(1, idEmployeur);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpAgentRetour employeur = new RafpAgentRetour();
                employeur.setId_emp(rs.getInt("id_emp"));
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

}
