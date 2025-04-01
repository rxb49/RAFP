package fr.univangers.controller;

import fr.univangers.classes.DonneesCSV;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.CalculService;
import jakarta.servlet.http.HttpServletRequest;
import org.apereo.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CalculController {
    private static final Logger logger = LoggerFactory.getLogger(CalculController.class);
    private final AutorisationService autorisationService;
    private final CalculService calculService;



    public CalculController(AutorisationService autorisationService, CalculService calculService) {
        this.autorisationService = autorisationService;
        this.calculService = calculService;
    }


    @GetMapping("/calculRafp")
    public String viewCalculRafp(HttpServletRequest request, Model model) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "calculRafp";
    }

    @GetMapping("/calculRafp/calcul")
    public ResponseEntity<String> CalculRafp(HttpServletRequest request) throws Exception {
        try{
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            System.out.println("Passage dans calculRafp/calcul");
            boolean vRetour = calculService.calculBaseRetourRecalculeeEmp();
            if (vRetour) {
                return new ResponseEntity<>("le calcul de la RAFP à été effectué ",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Problème dans le calcul de la RAFP ", HttpStatus.BAD_REQUEST);
            }
        }catch (UAException e) {
        logger.error("Erreur UA - CalculRafp - Erreur : {}", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Erreur BDD - CalculRafp - Erreur : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Erreur - CalculRafp - Erreur : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/calculRafp/generateCSV")
    public ResponseEntity<String> GenerateCSV(HttpServletRequest request) throws Exception {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<DonneesCSV> vRetour = calculService.getDataEmployeurCSV();
            System.out.println(vRetour.toString());
            if (vRetour.isEmpty()){
                return new ResponseEntity<>("Problème lors de la génération des CSV ", HttpStatus.BAD_REQUEST);
            }
            // Regrouper les données par id_emp
            Map<Integer, List<DonneesCSV>> donneesParIdEmp = vRetour.stream()
                    .collect(Collectors.groupingBy(DonneesCSV::getId_emp));

            // Dossier où enregistrer les fichiers CSV
            String projectDir = System.getProperty("user.dir");
            File tempCsvDir = Paths.get(projectDir, "..", "..", "RAFP", "tempCSV").toFile();
            if (!tempCsvDir.exists()) {
                tempCsvDir.mkdirs();
            }

            // Générer les fichiers CSV
            List<File> csvFiles = new ArrayList<>();
            for (Map.Entry<Integer, List<DonneesCSV>> entry : donneesParIdEmp.entrySet()) {
                int idEmp = entry.getKey();
                String filePath = new File(tempCsvDir, "donnees_" + idEmp + ".csv").getAbsolutePath();
                File csvFile = new File(filePath);
                csvFiles.add(csvFile);

                try (FileWriter writer = new FileWriter(csvFile)) {
                    // Écriture de l'en-tête
                    writer.append("Nom Usuel;ID Emp;Prénom;No INSEE;Montant Retour;Base Retour Recalculée;Salarial RAFP;Patronal RAFP;Total RAFP\n");

                    // Écriture des données
                    for (DonneesCSV d : entry.getValue()) {
                        writer.append(String.join(";",
                                        d.getNom_usuel(), String.valueOf(d.getId_emp()), d.getPrenom(), d.getNo_insee(),
                                        String.valueOf(d.getMnt_retour()),
                                        String.valueOf(d.getBase_retour_recalculee_emp()),
                                        String.valueOf(d.getSalaraialRafp()),
                                        String.valueOf(d.getPatronalRafp()),
                                        String.valueOf(d.getTotalRafp())))
                                .append("\n");
                    }
                }
            }

            // Créer le fichier ZIP
            File zipFile = new File(tempCsvDir, "donnees_csv.zip");
            System.out.println("Fichiers dans tempCSV : " + Arrays.toString(tempCsvDir.listFiles()));
            zipFile.setReadable(true, false);
            zipFile.setWritable(true, false);
            zipFile.setExecutable(true, false);
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

            // Supprimer les fichiers CSV après la création du ZIP
            for (File csv : csvFiles) {
                if (csv.exists()) {
                    csv.delete();
                }
            }

            // Préparer la réponse HTTP pour télécharger le fichier ZIP
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + zipFile.getName());
            headers.add("Content-Type", "application/zip");

            return new ResponseEntity<>("Fichier CSV générer avec succès",HttpStatus.OK);


        }catch (Exception e) {
            logger.error("Erreur - CalculRafp - Erreur : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
