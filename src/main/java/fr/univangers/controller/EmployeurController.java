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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @PostMapping("/gestionEmployeur/add")
    public String addEmployeur(HttpServletRequest request, RedirectAttributes redirectAttributes) throws SQLException {
        RafpEmployeur employeur = new RafpEmployeur();
        try {
            String nomEmployeur = request.getParameter("nomEmployeur");
            String mailEmployeur = request.getParameter("mailEmployeur");
            logger.info(" Nom Employeur " + nomEmployeur);
            employeur = employeurService.insertEmployeur(nomEmployeur, mailEmployeur);
            redirectAttributes.addFlashAttribute("employeur", employeur);
            redirectAttributes.addFlashAttribute("message", "L'employeur a été ajouté avec succès !");
            return "redirect:/gestionEmployeur";
        } catch (Exception e) {
            if ("L'employeur existe déjà.".equals(e.getMessage())) {
                redirectAttributes.addFlashAttribute("errorMessage", "L'employeur existe déjà.");
            } else if ("Le nom et l'email de l'employeur ne peuvent pas être null".equals(e.getMessage())) {
                redirectAttributes.addFlashAttribute("errorMessage", "Le nom et l'email de l'employeur ne peuvent pas être null");
            } else {
                logger.error("Erreur - insertEmployeur - employeur : {} ", employeur, e.getMessage(), e);
                redirectAttributes.addFlashAttribute("errorMessage", "Une erreur est survenue lors de l'ajout de l'employeur. Veuillez réessayer.");
            }
            return "redirect:/gestionEmployeur";
        }
    }

    @PostMapping("/gestionEmployeur/update")
    public String updateEmployeur(HttpServletRequest request, RedirectAttributes redirectAttributes) throws SQLException {
        List<RafpEmployeur> employeurs = employeurService.getEmployeur();
        int idEmployeur = Integer.parseInt(request.getParameter("employeurs"));
        String nomEmployeur = request.getParameter("nomEmployeurUpdate");
        String mailEmployeur = request.getParameter("mailEmployeurUpdate");

        // Vérifiez les valeurs récupérées pour débogage
        logger.info("ID Employeur : " + idEmployeur);
        logger.info("Nom Employeur : " + nomEmployeur);
        logger.info("Mail Employeur : " + mailEmployeur);

        // Ajoutez une validation simple
        if (nomEmployeur == null || nomEmployeur.isEmpty() || mailEmployeur == null || mailEmployeur.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessageUpdate", "Le nom et l'email de l'employeur ne peuvent pas être null");
            redirectAttributes.addFlashAttribute("employeurs", employeurs);
            return "redirect:/gestionEmployeur";
        }

        try {
            employeurService.updateEmployeur(idEmployeur, nomEmployeur, mailEmployeur);
            redirectAttributes.addFlashAttribute("employeurs", employeurs);
            redirectAttributes.addFlashAttribute("messageUpdate", "L'employeur a été modifié avec succès !");
            return "redirect:/gestionEmployeur";
        } catch (Exception e) {
            if ("L'employeur existe déjà.".equals(e.getMessage())) {
                redirectAttributes.addFlashAttribute("errorMessageUpdate", "L'employeur existe déjà.");
                return "redirect:/gestionEmployeur";
            } else if ("Le nom et l'email de l'employeur ne peuvent pas être null".equals(e.getMessage())) {
                redirectAttributes.addFlashAttribute("errorMessageUpdate", "Le nom et l'email de l'employeur ne peuvent pas être null");
            } else {
                logger.error("Erreur - updateEmployeur - employeur : {} ", e.getMessage(), e);
                redirectAttributes.addFlashAttribute("errorMessageUpdate", "Une erreur est survenue lors de la modification de l'employeur. Veuillez réessayer.");
                return "error.errorBDD";
            }
        }
        return "redirect:/gestionEmployeur";
    }
}
