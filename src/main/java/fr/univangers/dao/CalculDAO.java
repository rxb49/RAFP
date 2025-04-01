package fr.univangers.dao;

import fr.univangers.classes.*;
import fr.univangers.sql.OracleConfiguration;
import fr.univangers.sql.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
     * Récupere les données à implémenter dans le fichier CSV employeur'
     * @return : les données a implémenter dans les fichier CSV employeur
     * @throws SQLException : SQLException
     */
    public List<DonneesCSV> getDataEmployeurCSV() throws SQLException {

        logger.info("Début de la requete de recuperation des données CSV");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        List<DonneesCSV> donneesCSVList = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select R.id_emp, E.lib_emp, A.nom_usuel, A.prenom, a.no_insee, R.mnt_retour, R.base_retour_recalculee_emp, " +
                    "R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Salarial_RAFP\", R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Patronal_RAFP\"," +
                    "R.base_retour_recalculee_emp* 0.1 AS \"Total_Cotisation_RAFP\"" +
                    "from harp_adm.rafp_retour R inner join harp_adm.rafp_agent A ON a.no_insee = r.insee " +
                    "inner join harp_adm.rafp_employeur E ON e.id_emp = r.id_emp " +
                    "WHERE R.annee = (select MAX(annee) from harp_adm.rafp_agent) AND A.annee = (select MAX(annee) from harp_adm.rafp_agent)  ORDER BY R.id_emp ";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                DonneesCSV employeur = new DonneesCSV();
                employeur.setId_emp(rs.getInt("id_emp"));
                employeur.setLib_emp(rs.getString("lib_emp"));
                employeur.setNom_usuel(rs.getString("nom_usuel"));
                employeur.setPrenom(rs.getString("prenom"));
                employeur.setNo_insee(rs.getString("no_insee"));
                employeur.setMnt_retour(rs.getDouble("mnt_retour"));
                employeur.setBase_retour_recalculee_emp(rs.getDouble("base_retour_recalculee_emp"));
                employeur.setSalaraialRafp(rs.getDouble("Cotisation_Salarial_RAFP"));
                employeur.setPatronalRafp(rs.getDouble("Cotisation_Patronal_RAFP"));
                employeur.setTotalRafp(rs.getDouble("Total_Cotisation_RAFP"));

                donneesCSVList.add(employeur);
            }

            logger.info("Données Employeur : " + donneesCSVList.toString());
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de recuperation des données CSV");

        return donneesCSVList;
    }

    /**
     * Récupere les données à implémenter dans le fichier CSV agent'
     * @return : les données a implémenter dans les fichier CSV agent
     * @throws SQLException : SQLException
     */
    public List<DonneesCSV> getDataAgentCSV() throws SQLException {

        logger.info("Début de la requete de recuperation des données CSV");

        Connection maConnexion = null;
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        List<DonneesCSV> donneesCSVList = new ArrayList<>();
        try {
            maConnexion = oracleConfiguration.dataSource().getConnection();

            String requete = "select A.nom_usuel, A.prenom, a.no_insee, e.lib_emp, R.mnt_retour, R.base_retour_recalculee_emp, " +
                    "R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Salarial_RAFP\", R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Patronal_RAFP\"," +
                    "R.base_retour_recalculee_emp* 0.1 AS \"Total_Cotisation_RAFP\"" +
                    "from harp_adm.rafp_retour R inner join harp_adm.rafp_agent A ON a.no_insee = r.insee " +
                    "inner join harp_adm.rafp_employeur E ON e.id_emp = r.id_emp  " +
                    "WHERE R.annee = (select MAX(annee) from harp_adm.rafp_agent) AND A.annee = (select MAX(annee) from harp_adm.rafp_agent) ORDER BY A.no_insee ";
            cstmt = maConnexion.prepareStatement(requete);
            rs = cstmt.executeQuery();
            while (rs.next()) {
                DonneesCSV agent = new DonneesCSV();
                agent.setNom_usuel(rs.getString("nom_usuel"));
                agent.setPrenom(rs.getString("prenom"));
                agent.setNo_insee(rs.getString("no_insee"));
                agent.setLib_emp(rs.getString("lib_emp"));
                agent.setMnt_retour(rs.getDouble("mnt_retour"));
                agent.setBase_retour_recalculee_emp(rs.getDouble("base_retour_recalculee_emp"));
                agent.setSalaraialRafp(rs.getDouble("Cotisation_Salarial_RAFP"));
                agent.setPatronalRafp(rs.getDouble("Cotisation_Patronal_RAFP"));
                agent.setTotalRafp(rs.getDouble("Total_Cotisation_RAFP"));

                donneesCSVList.add(agent);
            }

            logger.info("Données Agents: " + donneesCSVList.toString());
            rs.close();
            cstmt.close();
        }
        finally {
            Sql.close(maConnexion);
        }

        logger.info("Fin de la requete de recuperation des données CSV");

        return donneesCSVList;
    }

    /**
     * Génère les fichiers CSV des employeurs et les met dans un zip '
     * @return : vrai ou faux si les fichiers sont générer
     * @throws SQLException : SQLException
     */
    public boolean generateCSVEmployeur(List<DonneesCSV> donnees) throws SQLException, IOException {
        logger.info("Début de la requête de génération des CSV employeur");

        Map<Integer, List<DonneesCSV>> donneesParIdEmp = donnees.stream()
                .collect(Collectors.groupingBy(DonneesCSV::getId_emp));

        String projectDir = System.getProperty("user.dir");
        File tempCsvDir = Paths.get(projectDir, "..", "..", "RAFP", "tempCSV").toFile();
        if (!tempCsvDir.exists()) {
            tempCsvDir.mkdirs();
        }

        List<File> csvFiles = new ArrayList<>();
        for (Map.Entry<Integer, List<DonneesCSV>> entry : donneesParIdEmp.entrySet()) {
            int idEmp = entry.getKey();
            List<DonneesCSV> donneesList = entry.getValue();
            String libEmp = (donneesList.get(0).getLib_emp() != null) ? donneesList.get(0).getLib_emp() : "Inconnu";
            File csvFile = new File(tempCsvDir, "donnees_" + idEmp + "_" + libEmp + ".csv");
            csvFiles.add(csvFile);

            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.append("Nom Usuel;ID Emp;Prénom;No INSEE;Montant Retour;Base Retour Recalculée;Salarial RAFP;Patronal RAFP;Total RAFP\n");

                int nbDossier = 0;
                double totalMntRetour = 0;
                double totalBaseRetourRecalculee = 0;
                double totalSalarialRafp = 0;
                double totalPatronalRafp = 0;
                double totalTotalRafp = 0;

                for (DonneesCSV d : entry.getValue()) {
                    writer.append(String.join(";",
                                    d.getNom_usuel(), String.valueOf(d.getId_emp()), d.getPrenom(), d.getNo_insee(),
                                    String.valueOf(d.getMnt_retour()),
                                    String.valueOf(d.getBase_retour_recalculee_emp()),
                                    String.valueOf(d.getSalaraialRafp()),
                                    String.valueOf(d.getPatronalRafp()),
                                    String.valueOf(d.getTotalRafp())))
                            .append("\n");

                    nbDossier++;
                    totalMntRetour += d.getMnt_retour();
                    totalBaseRetourRecalculee += d.getBase_retour_recalculee_emp();
                    totalSalarialRafp += d.getSalaraialRafp();
                    totalPatronalRafp += d.getPatronalRafp();
                    totalTotalRafp += d.getTotalRafp();
                }
                writer.append(String.format("Nombre de dossier:%d;;;;",
                                 nbDossier))
                        .append(String.format("%.2f", totalMntRetour)).append(";")
                        .append(String.format("%.2f", totalBaseRetourRecalculee)).append(";")
                        .append(String.format("%.2f", totalSalarialRafp)).append(";")
                        .append(String.format("%.2f", totalPatronalRafp)).append(";")
                        .append(String.format("%.2f", totalTotalRafp)).append("\n");

            }
        }

        File zipFile = new File(tempCsvDir, "donnees_employeur_csv.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File csv : csvFiles) {
                try (FileInputStream fis = new FileInputStream(csv)) {
                    ZipEntry zipEntry = new ZipEntry(csv.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }

        for (File csv : csvFiles) {
            csv.delete();
        }

        boolean success = zipFile.exists() && zipFile.length() > 0;
        logger.info("Fin de la requête de génération des CSV. Succès: " + success);
        return success;
    }

    /**
     * Génère les fichiers CSV des agents et les met dans un zip '
     * @return : vrai ou faux si les fichiers sont générer
     * @throws SQLException : SQLException
     */
    public boolean generateCSVagent(List<DonneesCSV> donnees) throws SQLException, IOException {
        logger.info("Début de la requête de génération des CSV agents");

        Map<String, List<DonneesCSV>> donneesParNoInsee = donnees.stream()
                .collect(Collectors.groupingBy(DonneesCSV::getNo_insee));

        String projectDir = System.getProperty("user.dir");
        File tempCsvDir = Paths.get(projectDir, "..", "..", "RAFP", "tempCSV").toFile();
        if (!tempCsvDir.exists()) {
            tempCsvDir.mkdirs();
        }

        List<File> csvFiles = new ArrayList<>();
        for (Map.Entry<String, List<DonneesCSV>> entry : donneesParNoInsee.entrySet()) {
            String noInsee = entry.getKey();
            List<DonneesCSV> donneesList = entry.getValue();
            String nom = (donneesList.get(0).getNom_usuel() != null) ? donneesList.get(0).getNom_usuel() : "Inconnu";
            String prenom = (donneesList.get(0).getPrenom() != null) ? donneesList.get(0).getPrenom() : "Inconnu";
            File csvFile = new File(tempCsvDir, "donnees_" + noInsee + "_" + nom + "_" + prenom + ".csv");
            csvFiles.add(csvFile);

            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.append("Nom Employeur;Montant Retour;Base Retour Recalculée;Salarial RAFP;Patronal RAFP;Total RAFP\n");

                int nbDossier = 0;
                double totalMntRetour = 0;
                double totalBaseRetourRecalculee = 0;
                double totalSalarialRafp = 0;
                double totalPatronalRafp = 0;
                double totalTotalRafp = 0;

                for (DonneesCSV d : entry.getValue()) {
                    writer.append(String.join(";",
                                    d.getLib_emp(),
                                    String.valueOf(d.getMnt_retour()),
                                    String.valueOf(d.getBase_retour_recalculee_emp()),
                                    String.valueOf(d.getSalaraialRafp()),
                                    String.valueOf(d.getPatronalRafp()),
                                    String.valueOf(d.getTotalRafp())))
                            .append("\n");

                    nbDossier++;
                    totalMntRetour += d.getMnt_retour();
                    totalBaseRetourRecalculee += d.getBase_retour_recalculee_emp();
                    totalSalarialRafp += d.getSalaraialRafp();
                    totalPatronalRafp += d.getPatronalRafp();
                    totalTotalRafp += d.getTotalRafp();
                }
                writer.append(String.format("Nombre de dossier:%d;",
                                nbDossier))
                        .append(String.format("%.2f", totalMntRetour)).append(";")
                        .append(String.format("%.2f", totalBaseRetourRecalculee)).append(";")
                        .append(String.format("%.2f", totalSalarialRafp)).append(";")
                        .append(String.format("%.2f", totalPatronalRafp)).append(";")
                        .append(String.format("%.2f", totalTotalRafp)).append("\n");

            }
        }

        File zipFile = new File(tempCsvDir, "donnees_agents_csv.zip");
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (File csv : csvFiles) {
                try (FileInputStream fis = new FileInputStream(csv)) {
                    ZipEntry zipEntry = new ZipEntry(csv.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }

        for (File csv : csvFiles) {
            csv.delete();
        }

        boolean success = zipFile.exists() && zipFile.length() > 0;
        logger.info("Fin de la requête de génération des CSV. Succès: " + success);
        return success;
    }



}
