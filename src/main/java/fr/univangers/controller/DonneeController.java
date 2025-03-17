package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.*;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DonneeController {
    private final Logger logger = LoggerFactory.getLogger(DonneeController.class);

    private final DonneeService donneeService;

    public DonneeController(DonneeService donneeService) {
        this.donneeService = donneeService;
    }

    @GetMapping("/donneesEmployeur")
    public  String viewDonneesEmployeur(Model model) {
        try{
            List<RafpEmployeur> employeurs = donneeService.getEmployeur();
            logger.info(employeurs.toString());
            model.addAttribute("employeurs", employeurs);
            return "donneesEmployeur";

        }catch (SQLException e){
            logger.error("Erreur BDD - viewDonneesEmployeur  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (Exception e){
            logger.error("Erreur - viewDonneesEmployeur -  Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";

        }
    }

    @PostMapping("/donneesEmployeur/getAgents")
    public ResponseEntity<List<RafpAgentRetour>> getAgentsByEmployeurId(@RequestBody Map<String, Integer> params) {
        int idEmployeur = params.get("idEmployeur");

        try {
            List<RafpAgentRetour> agents = donneeService.getAgentByEmployeurId(idEmployeur);
            return ResponseEntity.ok(agents);
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des agents", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
            // Récupération de l'agent avec l'ID
            RafpAgent agent = donneeService.getInfoAgentById(idAgent);
            // Si l'agent est trouvé, renvoyer une réponse avec l'agent
            if (agent != null) {
                return ResponseEntity.ok(agent);  // Retourne l'agent dans la réponse
            } else {
                // Si l'agent n'est pas trouvé, retourner une erreur 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (SQLException e) {
            logger.error("Erreur BDD - viewDonneesAgentInfo  - Erreur : {}", e.getMessage(), e);
            // En cas d'erreur BDD, renvoyer une erreur 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            logger.error("Erreur - viewDonneesAgentInfo - Erreur : {}", e.getMessage(), e);
            // En cas d'autre erreur, renvoyer une erreur générique 500
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
