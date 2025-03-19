package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;

public class DonneeController {
    private final Logger logger = LoggerFactory.getLogger(DonneeController.class);

    private final DonneeService donneeService;

    public DonneeController(DonneeService donneeService) {
        this.donneeService = donneeService;
    }




    @GetMapping("/donneesAgent")
    public  String viewDonneesAgent(Model model) {
        try{
            List<RafpLibAgent> agents = donneeService.getAgent();
            model.addAttribute("agents", agents);
            return "donneesAgent";

        }catch (SQLException e){
            logger.error("Erreur BDD - viewDonneesEmployeur  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (Exception e){
            logger.error("Erreur - viewDonneesEmployeur -  Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";

        }
    }

    @PostMapping("/donneesAgent/getInfo")
    public ResponseEntity<RafpAgent> viewDonneesAgentInfo(@RequestBody Map<String, String> params) {
        String idAgent = params.get("idAgent");

        try {
            RafpAgent agent = donneeService.getInfoAgentById(idAgent);
            if (agent != null) {
                return ResponseEntity.ok(agent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (SQLException e) {
            logger.error("Erreur BDD - viewDonneesAgentInfo  - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            logger.error("Erreur - viewDonneesAgentInfo - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    @PostMapping("/donneesAgent/getEmployeur")
    public ResponseEntity<List<RafpRetour>> viewDonneesAgentRetourEmployeur(@RequestBody Map<String, String> params) {
        String idAgent = params.get("idAgent");

        try {
            List<RafpRetour> agents = donneeService.getEmployeurByAgent(idAgent);
            if (agents != null) {
                logger.info(agents.toString());
                return ResponseEntity.ok(agents);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (SQLException e) {
            logger.error("Erreur BDD - viewDonneesAgentRetourEmployeur  - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            logger.error("Erreur - viewDonneesAgentRetourEmployeur - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/ajoutAgent")
    public String viewAjoutAgent() {
        return "ajoutAgent";
    }
    @GetMapping("/modifierAgent")
    public String viewModifierAgent() {
        return "modifierAgent";
    }

    @GetMapping("/ajoutEmployeur")
    public String viewAjoutEmployeur() {
        return "ajoutEmployeur";
    }

    @GetMapping("/modifierEmployeur")
    public String viewModifierEmployeur() {
        return "modifierEmployeur";
    }
}
