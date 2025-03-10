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
    public String viewGestionEmployeur(Model model) throws SQLException {
        List<RafpEmployeur> employeurs = employeurService.getEmployeur();
        model.addAttribute("persons", persons);
        model.addAttribute("employeurs", employeurs);
        return "gestionEmployeur";
    }

    @GetMapping("/gestionEmployeur/add")
    public String addEmployeur(HttpServletRequest request, Model model) throws SQLException {
        RafpEmployeur employeur = new RafpEmployeur();
        try {
            String nomEmployeur = request.getParameter("nomEmployeur");
            String mailEmployeur = request.getParameter("mailEmployeur");
            logger.info(" Nom Employeur " + nomEmployeur);
            employeur = employeurService.insertEmployeur(nomEmployeur, mailEmployeur);
            model.addAttribute("employeur", employeur);
            model.addAttribute("message", "L'employeur a été ajouté avec succès !");
            return "redirect:/gestionEmployeur";
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

    @PostMapping("/gestionEmployeur/update")
    public String updateEmployeur(HttpServletRequest request, Model model) throws SQLException {
        List<RafpEmployeur> employeurs = employeurService.getEmployeur();
        try {
            int idEmployeur = Integer.parseInt(request.getParameter("employeurs"));
            String nomEmployeur = request.getParameter("nomEmployeurUpdate");
            String mailEmployeur = request.getParameter("mailEmployeurUpdate");

            // Vérifiez les valeurs récupérées pour débogage
            logger.info("ID Employeur : " + idEmployeur);
            logger.info("Nom Employeur : " + nomEmployeur);
            logger.info("Mail Employeur : " + mailEmployeur);

            // Appel du service pour mettre à jour l'employeur
            employeurService.updateEmployeur(idEmployeur, nomEmployeur, mailEmployeur);
            model.addAttribute("employeurs", employeurs);
            model.addAttribute("message", "L'employeur a été modifié avec succès !");
            return "redirect:/gestionEmployeur";
        } catch (Exception e) {
            if ("L'employeur existe déjà.".equals(e.getMessage())) {
                model.addAttribute("errorMessage", "L'employeur existe déjà.");
            } else if ("Le nom et l'email de l'employeur ne peuvent pas être null".equals(e.getMessage())) {
                model.addAttribute("errorMessage", "Le nom et l'email de l'employeur ne peuvent pas être null");
            } else {
                logger.error("Erreur - updateEmployeur - employeur : {} ", e.getMessage(), e);
                model.addAttribute("errorMessage", "Une erreur est survenue lors de la modification de l'employeur. Veuillez réessayer.");
                return "error.errorBDD";
            }
        }
        return "gestionEmployeur";
    }
}
