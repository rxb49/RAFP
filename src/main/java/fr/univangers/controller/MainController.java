package fr.univangers.controller;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.RafpAgent;
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
                logger.info("Page index ..");
                String message = "Bienvenue sur votre application Spring Boot + JSP";
                model.addAttribute("annee", anneeMax);
                return "index";
            } else {
                return "initialisation";
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
