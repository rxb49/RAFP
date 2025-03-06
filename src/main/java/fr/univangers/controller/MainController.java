package fr.univangers.controller;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpPrecedante;
import fr.univangers.classes.SihamIndividuPaye;
import fr.univangers.service.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Calendar;
import java.sql.SQLException;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final PersonnelService personnelService;

    public MainController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    /*@GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        logger.info("Page index ..");
        String message = "Bienvenue sur votre application Spring Boot + JSP";
        model.addAttribute("message", message);
        return "index";
    }*/

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) throws SQLException {
        try {
            RafpAgent anneeMax = personnelService.initialisation();

            int annee = Integer.parseInt(anneeMax.getAnnee());
            int anneeActuelle = Calendar.getInstance().get(Calendar.YEAR);
            int difference = anneeActuelle - annee;
            logger.info("Annee recuperer "+ anneeMax.getAnnee());

            if (difference == 1) {
                String message = "Bienvenue sur votre application Spring Boot + JSP";
                model.addAttribute("annee", anneeMax);
                return "index";
            } else {
                RafpPrecedante getRafpPrecdante = personnelService.getRafpPrecedante();
                logger.info("Agent recupérér "+ getRafpPrecdante.getNo_individu());
                SihamIndividuPaye noInsee = personnelService.getNoInsee();
                logger.info("No Insee recupérer "+ noInsee.getNo_insee());
                logger.info("No individu "+ noInsee.getNo_individu());

                RafpAgent ajoutAgent = new RafpAgent();
                ajoutAgent.setAnnee(String.valueOf(anneeActuelle - 1));
                ajoutAgent.setNo_dossier_pers(String.valueOf(noInsee.getNo_individu()));
                ajoutAgent.setNo_insee(String.valueOf(noInsee.getNo_insee()));
                ajoutAgent.setTbi(getRafpPrecdante.getTbi());
                ajoutAgent.setIndemn(getRafpPrecdante.getIndemn());
                ajoutAgent.setSeuil(getRafpPrecdante.getSeuil());
                ajoutAgent.setRafpp(getRafpPrecdante.getRafpp());
                ajoutAgent.setTotal_Retour(getRafpPrecdante.getRetour());
                ajoutAgent.setBase_Restante(getRafpPrecdante.getBase_Restante());
                ajoutAgent.setBase_retour_recalculee(getRafpPrecdante.getBase_Retour_Calculee());
                logger.info(" Ajout Agent Annee "+ ajoutAgent.getAnnee());
                logger.info(" Ajout Agent No dossier pers "+ ajoutAgent.getNo_dossier_pers());
                logger.info(" Ajout Agent No insee "+ ajoutAgent.getNo_insee());
                logger.info(" Ajout Agent Tbi "+ ajoutAgent.getTbi());
                logger.info(" Ajout Agent Indemn "+ ajoutAgent.getIndemn());
                logger.info(" Ajout Agent seuil "+ ajoutAgent.getSeuil());
                logger.info(" Ajout Agent rafpp "+ ajoutAgent.getRafpp());
                logger.info(" Ajout Agent total retour "+ ajoutAgent.getTotal_Retour());
                logger.info(" Ajout Agent base restante "+ ajoutAgent.getBase_Restante());
                logger.info(" Ajout Agent retour recalculee "+ ajoutAgent.getBase_Retour_Recalculee());

                RafpAgent insertAgent = personnelService.insertAgent(ajoutAgent);
                return "index";


            }
        }catch (SQLException e){
            throw new SQLException("Erreur lors de l'accès à la base de données", e);
        }
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
