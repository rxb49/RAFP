package fr.univangers.controller;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.RafpRetour;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AgentService;
import fr.univangers.service.CalculService;
import fr.univangers.service.PersonnelService;
import fr.univangers.service.RetourService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class RetourController {
    private static final Logger logger = LoggerFactory.getLogger(RetourController.class);
    private final PersonnelService personnelService;
    private final AgentService agentService;
    private final CalculService calculService;
    private final RetourService retourService;


    public RetourController(PersonnelService personnelService, AgentService agentService, CalculService calculService, RetourService retourService) {
        this.personnelService = personnelService;
        this.agentService = agentService;
        this.calculService = calculService;
        this.retourService = retourService;
    }




    @GetMapping("/importTotal")
    public String viewImportTotal(HttpServletRequest request, Model model) {

        return "importTotal";
    }

    @PostMapping("/importTotal/insert")
    public ResponseEntity<String> insertImportTotalDataTemp(@RequestBody List<Map<String, Object>> data) {
        boolean vRetour = false;
        try {
            for (Map<String, Object> item : data) {
                String noInsee = (String) item.get("noInsee");
                double montant = Double.parseDouble((item.get("montant").toString()));
                Object idEmpObj = item.get("idEmployeur");

                if (idEmpObj == null) {
                    throw new IllegalArgumentException("L'ID employeur est manquant dans la ligne du CSV !");
                }

                int idEmp;
                try {
                    idEmp = Integer.parseInt(idEmpObj.toString());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Format invalide pour l'ID employeur : " + idEmpObj);
                }
                vRetour = retourService.insertImportTotalDataTemp(idEmp, noInsee, montant);
            }
            if (vRetour) {
                return new ResponseEntity<>("Import du fichier CSV effectué avec succès ",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Erreur dans l'import du fichier CSV",HttpStatus.BAD_REQUEST);
            }
        } catch (UAException e) {
            logger.error("Erreur UA - insertImportTotalData - data : {} - Erreur : {}", data, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - insertImportTotalData - data : {} - Erreur : {}", data, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - insertImportTotalData - data : {} - Erreur : {}", data, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/importTotal/tempData")
    public ResponseEntity<List<Map<String, Object>>> getTempImportData() {
        System.out.println("🔍 Requête reçue : GET /importTotal/tempData");
        try {
            List<Map<String, Object>> tempData = retourService.getTempImportData();
            System.out.println(tempData);
            return ResponseEntity.ok(tempData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/importTotal/clearTempData")
    public ResponseEntity<String> clearTempData() {
        try {
            retourService.clearTempData(); // Suppression des données
            return ResponseEntity.ok("Données temporaires supprimées avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression des données.");
        }
    }

    @PostMapping("/importTotal/validate")
    public ResponseEntity<String> InsertFinalData() {
        boolean vRetour = false;
        try {
            vRetour = retourService.validateImportTotalData();
            if(vRetour){
                return new ResponseEntity<>("Inseretion finale effectué ",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Erreur dans l'insertion des données",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur dans l'insertion des données",HttpStatus.BAD_REQUEST);
        }
    }




}
