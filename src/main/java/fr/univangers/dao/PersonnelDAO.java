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
public class PersonnelDAO {
    private final Logger logger = LoggerFactory.getLogger(PersonnelDAO.class);

    private final OracleConfiguration oracleConfiguration;

    public PersonnelDAO(OracleConfiguration oracleConfiguration) {
        this.oracleConfiguration = oracleConfiguration;
    }

    /**
     * Récupère le nom et prénom d'un personnel en fonction de son login
     * @param login : Login du personnel
     * @return : Un Personnel qui contient le nom et prénom
     * @throws SQLException : SQLException
     */
    public Personnel info(String login) throws SQLException {

        logger.info("Début de la requete de récupération des informations personnel");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        Personnel vRetour = new Personnel();

        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations du personnel
            String requete = " select A.nom_usuel, A.prenom " +
                    " from annuang.aua_personnel A          " +
                    " where A.login = ?                     ";

            cstmt = maConnexion.prepareStatement(requete);
            cstmt.setString(1, login);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                vRetour.setNom(rs.getString("nom_usuel"));
                vRetour.setPrenom(rs.getString("prenom"));
            }
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(rs);
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de récupération des informations personnel");

        return vRetour;
    }

    /**
     * Récupère l' année max et affiche index.jsp si l'année est égale a n-1'
     * @return : l'année la plus haute de la table rafp_agent
     * @throws SQLException : SQLException
     */
    public RafpAgent initialisation() throws SQLException {

        logger.info("Début de la requete de récupération des informations de l'année");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        RafpAgent vRetour = new RafpAgent();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " select distinct MAX(A.annee) AS annee_max from harp_adm.rafp_agent A";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                vRetour.setAnnee(rs.getString("annee_max"));
            }
            rs.close();
            cstmt.close();
        } catch (SQLException e) {
            logger.error("message d'erreur initiallisation",e);
        }
        finally {
            Sql.close(rs);
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de récupération des informations de l'année");

        return vRetour;
    }

    /**
     * Récupère les montant et le no_individu de l'annee precedante de la table rafp_2023'
     * @return : les montant et le no_individu des agents présent dans rafp_2023
     * on fait pour le moment pour un seul agent matricule = 22445
     * @throws SQLException : SQLException
     */
    public List<RafpPrecedante> getRafpPrecedante() throws SQLException {

        logger.info("Début de la requete de récupération des informations des agents rafp_2023");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        List<RafpPrecedante> resultList = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();
            //Récupération des informations de l'année
            String requete = " select distinct R.no_individu, S.no_insee, R.tbi, R.indemn, R.seuil, R.rafpp, R.retour, R.base_restante, R.base_retour_calculee " +
                    "from harp_adm.rafp_2023 R INNER JOIN siham_adm.siham_individu_paye S " +
                    "ON S.no_individu = R.no_individu where rownum <= 200";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();

            while (rs.next()) {
                RafpPrecedante vRetour = new RafpPrecedante();
                vRetour.setNo_individu(rs.getInt("no_individu"));
                vRetour.setNo_insee(rs.getString("no_insee"));
                vRetour.setTbi(rs.getInt("tbi"));
                vRetour.setIndemn(rs.getInt("indemn"));
                vRetour.setSeuil(rs.getInt("seuil"));
                vRetour.setRafpp(rs.getInt("rafpp"));
                vRetour.setRetour(rs.getInt("retour"));
                vRetour.setBase_Restante(rs.getInt("base_restante"));
                vRetour.setBase_Retour_Calculee(rs.getInt("base_retour_calculee"));
                resultList.add(vRetour);
            }
            rs.close();
            cstmt.close();
        } catch (SQLException e) {
            logger.error("message d'erreur getRafpPrecedante",e);
        }
        finally {
            Sql.close(rs);
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de récupération informations des agents rafp_2023");

        return resultList;
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
        } catch (SQLException e) {
            logger.error("message d'erreur insertAgent agent {}", agent ,e);
        } finally {
            Sql.close(cstmt);
            Sql.close(maConnexion);
        }
        logger.info("Fin de la requête d'insertion de l'agents");
        return ajouterAgent;
    }


}
