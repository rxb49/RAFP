package fr.univangers.controller;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpEmployeur;
import fr.univangers.classes.RafpLibAgent;
import fr.univangers.classes.RafpRetour;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AgentService;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.EmployeurService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import oracle.ucp.proxy.annotation.Post;
import org.apereo.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class AgentController {
    private final Logger logger = LoggerFactory.getLogger(AgentController.class);

    private final AgentService agentService;
    private final AutorisationService autorisationService;

    public AgentController(AgentService agentService, AutorisationService autorisationService) {
        this.agentService = agentService;
        this.autorisationService = autorisationService;
    }


    @GetMapping("/donneesAgent/{no_insee}")
    public String viewDonneesAgent(HttpServletRequest request, Model model, @PathVariable String no_insee) {
        System.out.println("Contrôleur appelé avec no_insee = " + no_insee);
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            if (no_insee == null || no_insee.isEmpty()) {
                return "redirect:/error"; // Redirection si no_insee est absent
            }

            // Récupérer les employeurs et l'agent en utilisant le no_insee
            List<RafpRetour> employeurs = agentService.getEmployeurByAgent(no_insee);
            RafpAgent agent = agentService.getAgentByNoInsee(no_insee);
            // Ajouter les données au modèle pour les utiliser dans la vue
            model.addAttribute("employeurs", employeurs);
            model.addAttribute("agent", agent);

            // Retourner la vue
            return "donneesAgent";

        } catch (SQLException e) {
            logger.error("Erreur BDD - viewDonneesAgent  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }catch (Exception e) {
            logger.error("Erreur - viewDonneesAgent -  Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";
        }
    }

    @GetMapping("/rechercheAgent")
    public  String viewRechercheAgent(HttpServletRequest request) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "rechercheAgent";
    }

    @GetMapping(value = "/rechercheAgent/search", produces = "application/json")
    public ResponseEntity<List<RafpLibAgent>> viewRechercheAgentSearch(HttpServletRequest request, @RequestParam String recherche) {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<RafpLibAgent> agents = agentService.getAgentBySearch(recherche);
            return ResponseEntity.ok(agents);

        } catch (SQLException e) {
            logger.error("Erreur BDD - viewRechercheAgentSearch  - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            logger.error("Erreur - viewRechercheAgentSearch - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/ajoutAgent/{id_emp}")
    public String viewAjoutAgent(HttpServletRequest request, @PathVariable int id_emp ) {
        try{
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            return "ajoutAgent";

        }catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }catch (Exception e){
            return "errorPage/errorBDD";
        }
    }



    @GetMapping("/modifierAgent/{id_emp}/{noInsee}")
    public String viewModifierAgent(HttpServletRequest request, @PathVariable String noInsee, @PathVariable int id_emp) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "modifierAgent";
    }

}

