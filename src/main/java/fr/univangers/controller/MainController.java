package fr.univangers.controller;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.SihamIndividuPaye;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.service.AgentService;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.CalculService;
import fr.univangers.service.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apereo.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final PersonnelService personnelService;
    private final AgentService agentService;
    private final CalculService calculService;
    private final AutorisationService autorisationService;


    public MainController(PersonnelService personnelService, AgentService agentService, CalculService calculService, AutorisationService autorisationService) {
        this.personnelService = personnelService;
        this.agentService = agentService;
        this.calculService = calculService;
        this.autorisationService = autorisationService;
    }


    @GetMapping(value = {"/", "/index"})
    public String index(HttpServletRequest request, Model model) throws SQLException {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);

            RafpAgent anneeMax = personnelService.initialisation();

            int annee = Integer.parseInt(anneeMax.getAnnee());
            int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);
            int difference = anneeActuelle - annee;
            logger.info("Annee recuperer " + anneeMax.getAnnee());

            if (difference == 1) {
                logger.info("Annee difference " + difference);
                String message = "Bienvenue sur votre application Spring Boot + JSP";
                model.addAttribute("anneeActuelle", anneeActuelle - 1);
                return "index";
            } else {
                List<RafpPrecedante> rafpPrecedantes = personnelService.getRafpPrecedante();
                logger.info("Liste des agents Rafp_2023 " + rafpPrecedantes.toString());
                for (RafpPrecedante rafp : rafpPrecedantes) {

                    RafpAgent ajoutAgent = new RafpAgent();
                    ajoutAgent.setAnnee(String.valueOf(anneeActuelle - 1));
                    ajoutAgent.setNo_dossier_pers(String.valueOf(rafp.getNo_individu()));
                    ajoutAgent.setNo_insee(String.valueOf(rafp.getNo_insee()));
                    ajoutAgent.setTbi(rafp.getTbi());
                    ajoutAgent.setIndemn(rafp.getIndemn());
                    ajoutAgent.setSeuil(rafp.getSeuil());
                    ajoutAgent.setRafpp(rafp.getRafpp());
                    ajoutAgent.setTotal_Retour(rafp.getRetour());
                    ajoutAgent.setBase_Restante(rafp.getBase_Restante());
                    ajoutAgent.setBase_retour_recalculee(rafp.getBase_Retour_Calculee());
                    RafpAgent insertAgent = agentService.insertAgent(ajoutAgent);
                }
                calculService.setTBI();
                calculService.setIndemn();
                calculService.setRafpp();
                calculService.setSeuil();


                model.addAttribute("anneeActuelle", anneeActuelle - 1);
                return "index";


            }
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - index - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }
        catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }
        catch (Exception e) {
            logger.error("Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";
        }
    }

    @GetMapping("/calculRafp")
    public String viewCalculRafp(HttpServletRequest request, Model model) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "calculRafp";
    }





    @GetMapping("/health")
    public ResponseEntity<String> health() {
        //page de test de l'appli pour centreon
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @GetMapping("/accessRefused")
    public String errorRender() {
        return "errorPage/accessRefused";
    }

    @GetMapping("/sessionExpired")
    public String sessionRender() {
        return "errorPage/sessionExpired";
    }

    @GetMapping("/errorBDD")
    public String errorBDD() {
        return "errorPage/errorBDD";
    }

    @GetMapping("/errorLoad")
    public String errorLoad() {
        return "errorPage/errorLoad";
    }
}
