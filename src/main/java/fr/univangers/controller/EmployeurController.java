package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeurController {
    private final Logger logger = LoggerFactory.getLogger(EmployeurController.class);

    private final EmployeurService employeurService;

    public EmployeurController(EmployeurService employeurService) {
        this.employeurService = employeurService;
    }

    @PostMapping("/donneesEmployeur")
    public String viewDonneesEmployeur(@RequestParam Integer id_emp, Model model, HttpSession session) {
        try {
            if (id_emp == null) {
                return "redirect:/error";
            }

            session.setAttribute("id_emp", id_emp);
            List<RafpAgentRetour> agents = employeurService.getAgentByEmployeurId(id_emp);
            RafpEmployeur employeur = employeurService.getEmployeurById(id_emp);
            double totalMontantRetour = agents.stream()
                    .mapToDouble(RafpAgentRetour::getMnt_retour)
                    .sum();
            model.addAttribute("agents", agents);
            model.addAttribute("employeur", employeur);
            model.addAttribute("totalMontantRetour", totalMontantRetour);


            return "donneesEmployeur";
        } catch (SQLException e) {
            logger.error("ErreurBDD - viewDonneesEmployeur - Erreur : {}", e.getMessage(), e);
            return "redirect:/error";
        } catch (Exception e) {
            logger.error("Erreur - viewDonneesEmployeur - Erreur : {}", e.getMessage(), e);
            return "redirect:/error";
        }
    }


    @GetMapping("/rechercheEmployeur")
    public  String viewRechercheEmployeur() {
        return "rechercheEmployeur";
    }

    @GetMapping(value = "/rechercheEmployeur/search", produces = "application/json")
    public ResponseEntity<List<RafpEmployeur>> viewRechercheEmployeurSearch(@RequestParam String recherche) {
        try {
            List<RafpEmployeur> employeurs = employeurService.getEmployeurBySearch(recherche);
            return ResponseEntity.ok(employeurs);

        } catch (SQLException e) {
            logger.error("Erreur BDD - viewRechercheEmployeurSearch  - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            logger.error("Erreur - viewRechercheEmployeurSearch - Erreur : {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
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
            logger.error("Erreur BDD - viewGestionEmployeurModifier  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (Exception e){
            logger.error("Erreur - viewGestionEmployeurModifier -  Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";

        }
    }



    @PostMapping(value = "/gestionEmployeur/add", produces = "application/json")
    public ResponseEntity<String> addEmployeur(HttpServletRequest request, @RequestBody RafpEmployeur rafpEmployeur) {
        try {
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
        try {
            boolean vRetour = employeurService.updateEmployeur(rafpEmployeur);
            if (vRetour) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        } catch (UAException e) {
            logger.error("Erreur UA - updateEmployeur - rafpEmployeur : {} - Erreur : {}", rafpEmployeur, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.error("Erreur BDD - updateEmployeur - idEmployeurs : {} - nomEmployeurUpdate : {} - mailEmployeurUpdate : {} - Erreur : {}", rafpEmployeur.getId_emp(), rafpEmployeur.getLib_emp(), rafpEmployeur.getMail_emp(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Erreur - updateEmployeur - idEmployeurs : {} - nomEmployeurUpdate : {} - mailEmployeurUpdate : {} - Erreur : {}", rafpEmployeur.getId_emp(), rafpEmployeur.getLib_emp(), rafpEmployeur.getMail_emp(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
