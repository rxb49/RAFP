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
    public ResponseEntity<String> insertImportTotalData(@RequestBody List<Map<String, Object>> data) {
        boolean vRetour = false;
        try {
            for (Map<String, Object> item : data) {
                String noInsee = (String) item.get("noInsee");
                double montant = Double.parseDouble((item.get("montant").toString()));
                String employeur = (String) item.get("employeur");
                vRetour = retourService.insertImportTotalData(employeur, noInsee, montant);
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




}
