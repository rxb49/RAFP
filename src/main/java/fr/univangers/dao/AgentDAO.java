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
public class AgentDAO {
    private final Logger logger = LoggerFactory.getLogger(AgentDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public AgentDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }



    /**
     * Insert l'agent dans la table rafp agent'
     * @return : booléen
     * on fait pour le moment pour un seul agent matricule = 22445
     * @throws SQLException : SQLException
     */
    public RafpAgent insertAgent(RafpAgent agent) throws SQLException {
        logger.info("Début de la requête d'insertion de l'agent");
        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        RafpAgent ajouterAgent = new RafpAgent();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            // Insertion de l'agent
            String requete = "INSERT INTO harp_adm.rafp_agent (annee, no_dossier_pers, no_insee, tbi, indemn, seuil, rafpp," +
                    " base_restante, total_retour, base_retour_recalculee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            cstmt = maConnexion.prepareStatement(requete);

            // Définir les valeurs des paramètres avant l'exécution
            cstmt.setString(1, agent.getAnnee());
            cstmt.setString(2, agent.getNo_dossier_pers());
            cstmt.setString(3, agent.getNo_insee());
            cstmt.setInt(4, agent.getTbi());
            cstmt.setInt(5, agent.getIndemn());
            cstmt.setInt(6, agent.getSeuil());
            cstmt.setInt(7, agent.getRafpp());
            cstmt.setInt(8, agent.getBase_Restante());
            cstmt.setInt(9, agent.getTotal_Retour());
            cstmt.setInt(10, agent.getBase_Retour_Recalculee());

            // Exécuter la requête d'insertion
            int rowsInserted = cstmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Insertion en base de donnée réussie !");
                ajouterAgent = agent; // Assigner l'agent inséré à la variable de retour
            } else {
                logger.warn("Aucune ligne insérée en base de donnée.");
            }
            Sql.close(cstmt);
        } catch (SQLException e) {
            logger.error("message d'erreur insertAgent agent {}", agent ,e);
        } finally {
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion de l'agents");
        return ajouterAgent;
    }


}
