package fr.univangers.dao;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpAgentRetour;
import fr.univangers.classes.RafpEmployeur;
import fr.univangers.classes.RafpLibAgent;
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

            String requete = "select E.id_emp, E.lib_emp, E.mail_emp from harp_adm.rafp_employer E order by lib_emp ASC";
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

            String requete = "select R.id_emp, R.insee, R.mnt_retour from harp_adm.rafp_retour R where id_emp = ?";
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

    public List<RafpLibAgent> getAgent() throws SQLException {
        logger.info("Début de la requête de récuperation des agents");

        List<RafpLibAgent> agents = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select distinct S.prenom, S.nom_usuel, A.no_insee from harp_adm.rafp_agent A" +
                    "    inner join siham_adm.siham_individu_paye S ON S.no_insee = A.no_insee where A.annee = '2024' ";
            // Exécuter la requête de récuperation
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpLibAgent agent = new RafpLibAgent();
                agent.setNo_insee(rs.getString("no_insee"));
                agent.setNom_usuel(rs.getString("nom_usuel"));
                agent.setPrenom(rs.getString("prenom"));
                agents.add(agent);
            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation des agents");
        return agents;
    }

    public RafpAgent getInfoAgentById(String no_insee) throws SQLException {
        logger.info("Début de la requête de récuperation de l'agent");
        RafpAgent agent = new RafpAgent();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select A.annee, A.no_dossier_pers, A.no_insee, A.tbi, A.indemn, A.seuil, A.rafpp, A.total_retour," +
                    " A.base_restante, A.base_retour_recalculee from harp_adm.rafp_agent A where A.no_insee = ? and annee = '2024'";
            // Exécuter la requête de récuperation
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, no_insee);
            rs = cstmt.executeQuery();
            if (rs.next()) {

                agent.setNo_insee(rs.getString("annee"));
                agent.setNo_dossier_pers(rs.getString("no_dossier_pers"));
                agent.setNo_insee(rs.getString("no_insee"));
                agent.setTbi(rs.getInt("tbi"));
                agent.setIndemn(rs.getInt("indemn"));
                agent.setSeuil(rs.getInt("seuil"));
                agent.setRafpp(rs.getInt("rafpp"));
                agent.setBase_Restante(rs.getInt("base_restante"));
                agent.setTotal_Retour(rs.getInt("total_retour"));
                agent.setBase_retour_recalculee(rs.getInt("base_retour_recalculee"));
                logger.info(agent.toString());
            }else{
                logger.warn("Aucun agent trouvé avec le no_insee: " + no_insee);
            }
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation de l'agent");
        return agent;
    }

}
