package fr.univangers.controller;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.RafpRetour;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apereo.cas.client.authentication.AttributePrincipal;
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
    private final RetourService retourService;
    private final AutorisationService autorisationService;


    public RetourController(RetourService retourService, AutorisationService autorisationService) {
        this.retourService = retourService;
        this.autorisationService = autorisationService;
    }




    @GetMapping("/importTotal")
    public String viewImportTotal(HttpServletRequest request, Model model) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "importTotal";
    }

    @PostMapping("/importTotal/insert")
    public ResponseEntity<String> insertImportTotalDataTemp(HttpServletRequest request, @RequestBody List<Map<String, Object>> data) {
        boolean vRetour = false;
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            for (Map<String, Object> item : data) {
                String noInsee = (String) item.get("noInsee");
                double montant = Double.parseDouble((item.get("montant").toString()));
                Object idEmpObj = item.get("idEmployeur");
                int idEmp = Integer.parseInt(idEmpObj.toString());
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
            return new ResponseEntity<>("Erreur d'insertion dans la table temporaire", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - insertImportTotalData - data : {} - Erreur : {}", data, e.getMessage());
            return new ResponseEntity<>("Erreur d'insertion dans la table temporaire", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/importTotal/tempData")
    public ResponseEntity<List<Map<String, Object>>> getTempImportData(HttpServletRequest request) throws Exception {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<Map<String, Object>> tempData = retourService.getTempImportData();
            return ResponseEntity.ok(tempData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/importTotal/clearTempData")
    public ResponseEntity<String> clearTempData(HttpServletRequest request) throws Exception {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            retourService.clearTempData(); // Suppression des données
            return new ResponseEntity<>("Suppression des données temporaires effectué ",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur dans la suppression des données temporaires ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/importTotal/validate")
    public ResponseEntity<String> InsertFinalData(HttpServletRequest request) {
        boolean vRetour = false;
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            vRetour = retourService.validateImportTotalDataFinal();
            if(vRetour){
                return new ResponseEntity<>("Inseretion finale effectué ",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Erreur dans l'insertion des données final",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur dans l'insertion des données final",HttpStatus.BAD_REQUEST);
        }
    }

}
