package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.service.AppelExterne;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.EmployeurService;
import fr.univangers.service.PersonnelService;
import fr.univangers.utils.ExportPDF;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apereo.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class EmployeurController {
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

    private final EmployeurService employeurService;

    public EmployeurController(PersonnelService personnelService, AppelExterne appelExterne, AutorisationService autorisationService, EmployeurService employeurService) {
        this.personnelService = personnelService;
        this.appelExterne = appelExterne;
        this.autorisationService = autorisationService;
        this.employeurService = employeurService;
    }


    @GetMapping("/gestionEmployeur")
    public String viewGestionEmployeur(Model model) {
        model.addAttribute("persons", persons);
        return "gestionEmployeur";
    }

    @GetMapping("/gestionEmployeur/add")
    public String addEmployeur(HttpServletRequest request, Model model) {
        RafpEmployeur employeur = new RafpEmployeur();
        try {
            String nomEmployeur = request.getParameter("nomEmployeur");
            String mailEmployeur = request.getParameter("mailEmployeur");
            logger.info(" Nom Employeur " + nomEmployeur);
            employeur = employeurService.insertEmployeur(nomEmployeur, mailEmployeur);
            model.addAttribute("employeur", employeur);
            model.addAttribute("message", "L'employeur a été ajouté avec succès !");
            return "gestionEmployeur";
        } catch (Exception e) {
            if ("L'employeur existe déjà.".equals(e.getMessage())) {
                model.addAttribute("errorMessage", "L'employeur existe déjà.");
                return "gestionEmployeur";
            }
            if ("Le nom et l'email de l'employeur ne peuvent pas être null".equals(e.getMessage())) {
                model.addAttribute("errorMessage", "Le nom et l'email de l'employeur ne peuvent pas être null");

            } else {
                logger.error("Erreur - insertEmployeur - employeur : {} ", employeur, e.getMessage(), e);
                model.addAttribute("errorMessage", "Une erreur est survenue lors de l'ajout de l'employeur. Veuillez réessayer.");
                return "error.errorBDD";
            }
        }
        return "gestionEmployeur";
    }
}
