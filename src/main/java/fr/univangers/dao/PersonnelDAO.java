package fr.univangers.dao;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.sql.OracleConfiguration;
import fr.univangers.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
//            Sql.close(rs);
//            Sql.close(cstmt);
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
        }
        finally {
//            Sql.close(rs);
//            Sql.close(cstmt);
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de récupération des informations de l'année");

        return vRetour;
    }
}
