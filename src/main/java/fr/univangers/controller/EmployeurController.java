package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AppelExterne;
import fr.univangers.service.AutorisationService;
import fr.univangers.service.EmployeurService;
import fr.univangers.service.PersonnelService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@Controller
public class EmployeurController {
    private final Logger logger = LoggerFactory.getLogger(EmployeurController.class);

    private final EmployeurService employeurService;

    public EmployeurController(EmployeurService employeurService) {
        this.employeurService = employeurService;
    }


    @GetMapping("/gestionEmployeur")
    public  String viewGestionEmployeur() {
        return "gestionEmployeur";
    }

    @GetMapping("/gestionEmployeur/modifier")
    public  String viewGestionEmployeurModifier(Model model) {
        try{
            List<RafpEmployeur> employeurs = employeurService.getEmployeur();
            model.addAttribute("employeurs", employeurs);
            return "gestionEmployeurModifier";

        }catch (SQLException e){
            logger.error("Erreur BDD - viewGestionEmployeur  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (Exception e){
            logger.error("Erreur - viewHello -  Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";

        }
    }


    @PostMapping(value = "/gestionEmployeur/add", produces = "application/json")
    public ResponseEntity<String> addEmployeur(HttpServletRequest request, @RequestBody RafpEmployeur rafpEmployeur) {
        try {
            logger.info(" Nom Employeur " + rafpEmployeur);
            boolean vRetour = employeurService.insertEmployeur(rafpEmployeur);
            if (vRetour){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (UAException e) {
            logger.error("Erreur UA - addEmployeur - rafpEmployeur : {} - Erreur : {}", rafpEmployeur, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - addEmployeur - nomEmployeur : {} - mailEmployeur : {} - Erreur : {}", request.getParameter("nomEmployeur"), request.getParameter("mailEmployeur"), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - addEmployeur - nomEmployeur : {} - mailEmployeur : {} - Erreur : {}", request.getParameter("nomEmployeur"), request.getParameter("mailEmployeur"), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/gestionEmployeur/update", produces = "application/json")
    public ResponseEntity<String> updateEmployeur(HttpServletRequest request, @RequestBody RafpEmployeur rafpEmployeur) {
        try{
            logger.info(" Nom Employeur " + rafpEmployeur);
            boolean vRetour = employeurService.updateEmployeur(rafpEmployeur);
            if (vRetour){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (UAException e) {
            logger.error("Erreur UA - updateEmployeur - rafpEmployeur : {} - Erreur : {}", rafpEmployeur, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Erreur BDD - updateEmployeur - idEmployeurs : {} - nomEmployeurUpdate : {} - mailEmployeurUpdate : {} - Erreur : {}", rafpEmployeur.getId_emp(), rafpEmployeur.getLib_emp(), rafpEmployeur.getMail_emp(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - updateEmployeur - idEmployeurs : {} - nomEmployeurUpdate : {} - mailEmployeurUpdate : {} - Erreur : {}", rafpEmployeur.getId_emp(), rafpEmployeur.getLib_emp(), rafpEmployeur.getMail_emp(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
