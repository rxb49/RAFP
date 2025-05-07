package fr.univangers.dao;

import fr.univangers.classes.*;
import fr.univangers.sql.OracleConfiguration;
import fr.univangers.sql.Sql;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.text.SimpleDateFormat;
import java.util.*;
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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();
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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();
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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();
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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "UPDATE harp_adm.rafp_retour I  " +
                    "                         JOIN harp_adm.rafp_agent R ON I.insee = R.no_insee  " +
                    "                         JOIN (SELECT MAX(base_retour_recalculee) AS max_base_retour_recalculee  " +
                    "                               FROM harp_adm.rafp_agent) AS max_base  " +
                    "                         JOIN (SELECT MAX(total_retour) AS max_total_retour  " +
                    "                               FROM harp_adm.rafp_agent RR  " +
                    "                               JOIN harp_adm.rafp_retour IR ON IR.insee = RR.no_insee) AS max_total  " +
                    "                         SET I.base_retour_recalculee_emp = ROUND(I.mnt_retour *  " +
                    "                            (max_base.max_base_retour_recalculee / max_total.max_total_retour), 2)";
            logger.info(requete);
            cstmt = maConnexion.prepareStatement(requete);
            int rowsUpdated = cstmt.executeUpdate();

            if (rowsUpdated > 0) {
                vRetour = true;
            }

            cstmt.close();

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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select R.id_emp, E.lib_emp, A.nom_usuel, A.prenom, A.no_insee, R.mnt_retour, R.base_retour_recalculee_emp, " +
                    "R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Salarial_RAFP\", R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Patronal_RAFP\"," +
                    "R.base_retour_recalculee_emp* 0.1 AS \"Total_Cotisation_RAFP\"" +
                    "from harp_adm.rafp_retour R inner join harp_adm.rafp_agent A ON A.no_insee = R.insee " +
                    "inner join harp_adm.rafp_employeur E ON E.id_emp = R.id_emp " +
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
            maConnexion = oracleConfiguration.dataSourceSympa().getConnection();

            String requete = "select A.nom_usuel, A.prenom, A.no_insee, E.lib_emp, R.mnt_retour, R.base_retour_recalculee_emp, " +
                    "R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Salarial_RAFP\", R.base_retour_recalculee_emp* 0.05 AS \"Cotisation_Patronal_RAFP\"," +
                    "R.base_retour_recalculee_emp* 0.1 AS \"Total_Cotisation_RAFP\"" +
                    "from harp_adm.rafp_retour R inner join harp_adm.rafp_agent A ON A.no_insee = R.insee " +
                    "inner join harp_adm.rafp_employeur E ON E.id_emp = R.id_emp  " +
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

        // Utiliser un répertoire dédié dans /var/tmp pour les fichiers CSV
        String tempDir = "/var/lib/tomcat10/webapps/tempCSV";
        File tempCsvDir = new File(tempDir);

        // Créer le répertoire si nécessaire
        if (!tempCsvDir.exists()) {
            tempCsvDir.mkdirs();
        }
        logger.info("Répertoire de génération des fichiers CSV : " + tempCsvDir.getAbsolutePath());


        // Création des fichiers CSV
        List<File> csvFiles = new ArrayList<>();
        for (Map.Entry<Integer, List<DonneesCSV>> entry : donneesParIdEmp.entrySet()) {
            int idEmp = entry.getKey();
            List<DonneesCSV> donneesList = entry.getValue();
            String libEmp = (donneesList.get(0).getLib_emp() != null) ? donneesList.get(0).getLib_emp() : "Inconnu";
            File csvFile = new File(tempCsvDir, libEmp + "_" + idEmp + ".csv");
            csvFiles.add(csvFile);

            // Insertion des données dans le fichier CSV
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

                // Affichage du total sur la dernière ligne
                writer.append(String.format("Nombre de dossier:%d;;;;", nbDossier))
                        .append(String.format("%.2f", totalMntRetour)).append(";")
                        .append(String.format("%.2f", totalBaseRetourRecalculee)).append(";")
                        .append(String.format("%.2f", totalSalarialRafp)).append(";")
                        .append(String.format("%.2f", totalPatronalRafp)).append(";")
                        .append(String.format("%.2f", totalTotalRafp)).append("\n");

            }
        }

        // Mettre en zip tous les fichiers CSV générés
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

        // Suppression des fichiers CSV après avoir été mis dans le zip
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

        //Initialisation du répertoire ou les fichiers CSV seront créer
        Map<String, List<DonneesCSV>> donneesParNoInsee = donnees.stream()
                .collect(Collectors.groupingBy(DonneesCSV::getNo_insee));

        // Utiliser un répertoire dédié dans /var/tmp pour les fichiers CSV
        String tempDir = "/var/lib/tomcat10/webapps/tempCSV";
        File tempCsvDir = new File(tempDir);

        // Créer le répertoire si nécessaire
        if (!tempCsvDir.exists()) {
            tempCsvDir.mkdirs();
        }
        //creation des fichier CSV
        List<File> csvFiles = new ArrayList<>();
        for (Map.Entry<String, List<DonneesCSV>> entry : donneesParNoInsee.entrySet()) {
            //Définit la différentiation sur le no_insee pour chaque fichier et nommage du fichier
            String noInsee = entry.getKey();
            List<DonneesCSV> donneesList = entry.getValue();
            String nom = (donneesList.get(0).getNom_usuel() != null) ? donneesList.get(0).getNom_usuel() : "Inconnu";
            String prenom = (donneesList.get(0).getPrenom() != null) ? donneesList.get(0).getPrenom() : "Inconnu";
            File csvFile = new File(tempCsvDir, nom + "_" + prenom + "_" + noInsee + ".csv");
            csvFiles.add(csvFile);
            //insertion des données dans le fichier CSV

            try (FileWriter writer = new FileWriter(csvFile)) {
                writer.append("Nom Employeur;Montant Retour;Base Retour Recalculée;Salarial RAFP;Patronal RAFP;Total RAFP\n");

                //Calcul du total de chaque colonne
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
                //affichage du total sur la dernière ligne

                writer.append(String.format("Nombre de dossier:%d;",
                                nbDossier))
                        .append(String.format("%.2f", totalMntRetour)).append(";")
                        .append(String.format("%.2f", totalBaseRetourRecalculee)).append(";")
                        .append(String.format("%.2f", totalSalarialRafp)).append(";")
                        .append(String.format("%.2f", totalPatronalRafp)).append(";")
                        .append(String.format("%.2f", totalTotalRafp)).append("\n");

            }
        }

        //Mettre en zip tous les fichiers CSV généré
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
        //suppression des fichier csv quand ils on été mit en zip
        for (File csv : csvFiles) {
            csv.delete();
        }

        boolean success = zipFile.exists() && zipFile.length() > 0;
        logger.info("Fin de la requête de génération des CSV. Succès: " + success);
        return success;
    }


    /**
     * Crée un nouveau zip avec les deux autre zip contenant les fichiers CSV '
     * @return : Retourne le zip contenant les deux autres zip
     * @throws IOException : IOException
     */
    public File createZipFile() throws IOException {
        // Utilisation du chemin absolu vers tempCSV sur le serveur
        String tempCsvDirPath = "/var/lib/tomcat10/webapps/tempCSV";
        File tempCsvDir = new File(tempCsvDirPath);

        // Récupérer tous les fichiers ZIP
        File[] zipFiles = tempCsvDir.listFiles((dir, name) -> name.endsWith(".zip"));

        if (zipFiles == null || zipFiles.length == 0) {
            return null;
        }

        // Créer un fichier ZIP contenant tous les fichiers ZIP individuels
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        String dateStr = dateFormat.format(new Date());
        String zipFileName = "Tous_Les_Fichiers_" + dateStr + ".zip";

        File allZipFile = new File(tempCsvDir, zipFileName);

        // Créer le fichier ZIP contenant tous les autres fichiers ZIP
        try (FileOutputStream fos = new FileOutputStream(allZipFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (File zipFile : zipFiles) {
                try (FileInputStream fis = new FileInputStream(zipFile)) {
                    ZipEntry zipEntry = new ZipEntry(zipFile.getName());
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

        return allZipFile;
    }


    /**
     * Supprime le zip contenant les autres zip qui à été crée dans le dossier tempCSV '
     */
    public void deleteZipFiles() {
        // Utilisation du chemin absolu vers tempCSV sur le serveur
        String tempCsvDirPath = "/var/lib/tomcat10/webapps/tempCSV";
        File tempCsvDir = new File(tempCsvDirPath);

        // Récupérer tous les fichiers ZIP correspondant au format "Tous_Les_Fichiers_*.zip"
        File[] zipFiles = tempCsvDir.listFiles((dir, name) -> name.startsWith("Tous_Les_Fichiers_") && name.endsWith(".zip"));

        // Supprimer les fichiers
        if (zipFiles != null && zipFiles.length > 0) {
            for (File zipFile : zipFiles) {
                if (zipFile.delete()) {
                    System.out.println("Fichier supprimé : " + zipFile.getName());
                } else {
                    System.err.println("Erreur lors de la suppression de : " + zipFile.getName());
                }
            }
        } else {
            System.out.println("Aucun fichier à supprimer.");
        }
    }





}
