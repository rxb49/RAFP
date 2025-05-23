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
public class AgentDAO {
    private final Logger logger = LoggerFactory.getLogger(AgentDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public AgentDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }


    /**
     * Ajoute un agent dans la table rafp_agent à l'initialisatiin '
     * @return : l'agent insérer
     * @throws SQLException : SQLException
     */
    public RafpAgent insertAgent(RafpAgent agent) throws SQLException {
        logger.info("Début de la requête d'insertion de l'agent");
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        RafpAgent ajouterAgent = new RafpAgent();
        try {
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();
            String requete = "INSERT INTO harp_adm.rafp_agent (annee, no_dossier_pers, no_insee, tbi, indemn, seuil, rafpp," +
                    " base_restante, total_retour, base_retour_recalculee, nom_usuel, prenom) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            cstmt.setString(1, agent.getAnnee());
            cstmt.setString(2, agent.getNo_dossier_pers());
            cstmt.setString(3, agent.getNo_insee());
            cstmt.setInt(4, agent.getTbi());
            cstmt.setInt(5, agent.getIndemn());
            cstmt.setInt(6, agent.getSeuil());
            cstmt.setInt(7, 0);
            cstmt.setInt(8, agent.getBase_Restante());
            cstmt.setInt(9, agent.getTotal_Retour());
            cstmt.setInt(10, agent.getBase_Retour_Recalculee());
            cstmt.setString(11, agent.getNom_usuel());
            cstmt.setString(12, agent.getPrenom());


            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                ajouterAgent = agent;
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            Sql.close(cstmt);
        }
        finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion de l'agents");
        return ajouterAgent;
    }

    /**
     * récupere tous les agents en fonction d'une année '
     * @return : la liste des agents
     * @throws SQLException : SQLException
     */
    public List<RafpLibAgent> getAgent() throws SQLException {
        logger.info("Début de la requête de récuperation des agents");

        List<RafpLibAgent> agents = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select distinct A.prenom, A.nom_usuel, A.no_insee from harp_adm.rafp_agent A where A.annee = '2023' ";
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


    /**
     * rechercher un agent avec son nom ou prenom '
     * @return : le ou les agents concernés
     * @throws SQLException : SQLException
     */
    public List<RafpLibAgent> getAgentBySearch(String recherche) throws SQLException {
        logger.info("Début de la requête de recherche des agents");
        List<RafpLibAgent> agents = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select distinct A.prenom, A.nom_usuel, A.no_insee from harp_adm.rafp_agent A " +
                    "where A.prenom LIKE ? OR  A.nom_usuel LIKE ? ";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, "%" + recherche + "%");
            cstmt.setString(2, "%" + recherche + "%");
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpLibAgent agent = new RafpLibAgent();
                agent.setPrenom(rs.getString("prenom"));
                agent.setNom_usuel(rs.getString("nom_usuel"));
                agent.setNo_insee(rs.getString("no_insee"));
                agents.add(agent);
            }
            logger.info(agents.toString());
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de recherche des agents");
        return agents;
    }

    /**
     * Recuperer un agent avec son noInsee '
     * @return : l'agent correspondant
     * @throws SQLException : SQLException
     */
    public RafpAgent getAgentByNoInsee(String no_insee) throws SQLException {
        logger.info("Début de la requête de recherche de l'agent avec no_insee");
        RafpAgent agent = new RafpAgent();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select A.no_insee, A.nom_usuel, A.prenom, A.tbi, A.indemn, A.base_restante, A.total_retour, A.base_retour_recalculee from harp_adm.rafp_agent A " +
                    "where A.no_insee = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1,   no_insee );
            rs = cstmt.executeQuery();
            while (rs.next()) {
                agent.setNo_insee(rs.getString("no_insee"));
                agent.setNom_usuel(rs.getString("nom_usuel"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setTbi(rs.getInt("tbi"));
                agent.setIndemn(rs.getInt("indemn"));
                agent.setBase_Restante(rs.getInt("base_restante"));
                agent.setTotal_Retour(rs.getInt("total_retour"));
                agent.setBase_retour_recalculee(rs.getInt("base_retour_recalculee"));
            }
            logger.info(agent.toString());
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de recherche de l'agent avec no_insee");
        return agent;
    }


    /**
     * Recupere les employeurs en lien avec l'agent (noInsee) dans la table rafp_retour '
     * @return : Liste des rafp_retour en fonction du no_insee de l'agent
     * @throws SQLException : SQLException
     */
    public List<RafpRetour> getEmployeurByAgent(String no_insee) throws SQLException {
        logger.info("Début de la requête de récuperation des employeurs pour l'agent");
        List<RafpRetour> employeurs = new ArrayList<>();
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try{
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select e.id_emp, e.lib_emp, R.mnt_retour from harp_adm.rafp_retour R " +
                    "inner join harp_adm.rafp_employeur E on r.id_emp = e.id_emp where R.insee = ?";
            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, no_insee);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                RafpRetour employeur = new RafpRetour();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setMnt_retour(rs.getInt("mnt_retour"));
                employeurs.add(employeur);
            }
            logger.info(employeurs.toString());
            rs.close();
            cstmt.close();
        }finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête de récuperation des employeurs pour l'agent");
        return employeurs;
    }

    /**
     * Met a jour le total_retour d'un agent en calculant la somme des retour concerner par cet agent dans la table rafp_retour '
     * @return : vrai ou faux si l'update a fonctionné
     * @throws SQLException : SQLException
     */
    public boolean updateTotalRetourByAgent(String no_insee) throws SQLException, UAException {
        logger.info("Début de la requête de calcul du total_retour");
        if (no_insee == null || no_insee.isEmpty()) {
            throw new UAException("Le numéro Insee de l'agent ne peut pas être null");
        }
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            // modification de l'agent
            String requete = "update harp_adm.rafp_agent A set A.total_retour = (SELECT SUM(R.mnt_retour) FROM harp_adm.rafp_retour R " +
                    "WHERE R.insee = A.no_insee ) WHERE  A.no_insee = ?";
            cstmt = maConnexion.prepareStatement(requete);

            // Définir les valeurs des paramètres avant l'exécution
            cstmt.setString(1, no_insee);
            cstmt.executeUpdate();

        }
        finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requête de calcul du total_retour");
        return result;
    }

    /**
     * Met a jour le tbi et indemn d'un agent en ajoutant le montant mis sur le formulaire '
     * @return : vrai ou faux si l'update a fonctionné
     * @throws SQLException : SQLException
     */
    public boolean updateIndemnTBIByAgent(String no_insee, int tbi, int indemn) throws SQLException, UAException {
        logger.info("Début de la requête de modification des indemnitées et du tbi");
        if (no_insee == null || no_insee.isEmpty()) {
            throw new UAException("Le numéro Insee de l'agent ne peut pas être null");
        }
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        boolean result = false;
        try {
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "update harp_adm.rafp_agent A set A.tbi = A.tbi + ?, A.indemn = A.indemn + ? " +
                    "WHERE  A.no_insee = ? AND A.annee = (select max(A.annee) from harp_adm.rafp_agent A)";
            cstmt = maConnexion.prepareStatement(requete);

            cstmt.setInt(1, tbi);
            cstmt.setInt(2, indemn);
            cstmt.setString(3, no_insee);
            int rowsAffected = cstmt.executeUpdate();

            if (rowsAffected > 0) {
                result = true;
            }

        }
        finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requête de calcul du total_retour");
        return result;
    }
}
