package fr.univangers.controller;

import fr.univangers.classes.DonneesCSV;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.CalculService;
import fr.univangers.service.HistoriqueService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private final CalculService calculService;
    private final HistoriqueService historiqueService;


    public CalculController(CalculService calculService, HistoriqueService historiqueService) {
        this.calculService = calculService;
        this.historiqueService = historiqueService;
    }


    @GetMapping("/calculRafp")
    public String viewCalculRafp(HttpServletRequest request, Model model) throws Exception {
        boolean isEtatTExist = historiqueService.checkEtat("T");
        String lastDateGeneration = historiqueService.getDernierEtat("T");
        boolean isEtatCTExist = historiqueService.checkEtat("C");
        String lastDateCalcul = historiqueService.getDernierEtat("C");
        model.addAttribute("isEtatTExist", isEtatTExist);
        model.addAttribute("lastDateGeneration", (lastDateGeneration != null) ? lastDateGeneration : "Aucune génération faite");
        model.addAttribute("isEtatCTExist", isEtatCTExist);
        model.addAttribute("lastDateCalcul", (lastDateCalcul != null) ? lastDateCalcul : "Aucun calcul faite");
        return "calculRafp";
    }

    @GetMapping("/calculRafp/calcul")
    public ResponseEntity<String> CalculRafp(HttpServletRequest request) throws Exception {
        try{
            System.out.println("Passage dans calculRafp/calcul");
            boolean vRetour = calculService.calculBaseRetourRecalculeeEmp();
            if (vRetour) {
                historiqueService.insertHistoriqueCalcul();
                return new ResponseEntity<>("le calcul de la RAFP à été effectué ",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Problème dans le calcul de la RAFP ", HttpStatus.BAD_REQUEST);
            }
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
            List<DonneesCSV> vRetourEmployeur = calculService.getDataEmployeurCSV();
            List<DonneesCSV> vRetourAgent = calculService.getDataAgentCSV();
            if (vRetourEmployeur.isEmpty() || vRetourAgent.isEmpty()) {
                return new ResponseEntity<>("Problème lors de la génération des CSV ", HttpStatus.BAD_REQUEST);
            }else{
                 boolean successEmployeur = calculService.generateCSVEmployeur(vRetourEmployeur);
                boolean successAgent = calculService.generateCSVagent(vRetourAgent);
                if(successEmployeur || successAgent){
                     historiqueService.insertHistoriqueExport();
                     return new ResponseEntity<>("Fichier CSV générer avec succès",HttpStatus.OK);
                 }
            }
            return new ResponseEntity<>("Problème lors de la génération des CSV ", HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            logger.error("Erreur - CalculRafp - Erreur : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/calculRafp/downloadAll")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        calculService.downloadAllFiles(response);
    }


}
