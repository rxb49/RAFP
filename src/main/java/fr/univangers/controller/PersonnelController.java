package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.service.AgentService;
import fr.univangers.service.AppelExterne;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import org.apereo.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PersonnelController {
    private final Logger logger = LoggerFactory.getLogger(PersonnelController.class);
    private static final List<Person> persons = new ArrayList<>();

    private final PersonnelService personnelService;
    private final AppelExterne appelExterne;
    private final AutorisationService autorisationService;

    //Seulement pour l'application model, ne pas faire sur un vrai projet
    static {
        persons.add(new Person(7014, "Bernard", "Laureline"));
        persons.add(new Person(193, "Onillon", "Jocelyn"));
        persons.add(new Person(20637, "Depauw", "Yann"));
    }

    private final AgentService agentService;

    public PersonnelController(PersonnelService personnelService, AppelExterne appelExterne, AutorisationService autorisationService, AgentService agentService) {
        this.personnelService = personnelService;
        this.appelExterne = appelExterne;
        this.autorisationService = autorisationService;
        this.agentService = agentService;
    }


    @GetMapping("/personList")
    public String viewPersonList(Model model) {
        model.addAttribute("persons", persons);
        return "personList";
    }



    /**
     * Exemple d'appel de controller
     * @param request : Sert à récupérer le login
     * @param model : Model pour afficher la page
     * @return : Une page
     */

    @GetMapping("/donneesEmployeur")
    public String viewDonneesEmployeur(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "donneesEmployeur";
    }

    @GetMapping("/donneesAgent")
    public String viewDonneesAgent(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "donneesAgent";
    }

    @GetMapping("/ajoutAgent")
    public String viewAjoutAgent(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "ajoutAgent";
    }
    @GetMapping("/modifierAgent")
    public String viewModifierAgent(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "modifierAgent";
    }

    @GetMapping("/ajoutEmployeur")
    public String viewAjoutEmployeur(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "ajoutEmployeur";
    }

    @GetMapping("/modifierEmployeur")
    public String viewModifierEmployeur(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "modifierEmployeur";
    }

    @GetMapping("/calculRafp")
    public String viewCalculRafp(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "calculRafp";
    }


    /**
     * Exemple d'appel de controller avec vérification de droit d'accès
     * @param request : Sert à récupérer le login et l'idEncrypt
     * @param model : Model pour afficher la page
     * @return : Une page
     */
    @GetMapping("/hello2")
    public String viewHello2(HttpServletRequest request, Model model) {
        logger.info("Controller qui retourne les informations pour le Personnel connecté via supannRefId.");
        String login = request.getRemoteUser().toLowerCase();
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            Personnel lePersonnel = personnelService.info(login);
            model.addAttribute("lePersonnel", lePersonnel);
            return "personName";
        }
        catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - viewHello - login : {} - Erreur : {}", login, e.getMessage(), e);
            return "errorPage/errorBDD";
        }
        catch (Exception e) {
            logger.error("Erreur - viewHello - login : {} - Erreur : {}", login, e.getMessage(), e);
            return "errorPage/errorLoad";
        }
    }

}
