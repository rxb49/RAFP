package fr.univangers.controller;

import fr.univangers.classes.*;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apereo.cas.client.authentication.AttributePrincipal;
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
    private final AgentService agentService;
    private final RetourService retourService;
    private final AutorisationService autorisationService;


    public EmployeurController(EmployeurService employeurService, AgentService agentService, RetourService retourService, AutorisationService autorisationService) {
        this.employeurService = employeurService;
        this.agentService = agentService;
        this.retourService = retourService;
        this.autorisationService = autorisationService;
    }

    @GetMapping("/donneesEmployeur/{id_emp}")
    public String viewDonneesEmployeur(HttpServletRequest request, Model model, @PathVariable int id_emp) {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            if (id_emp == 0 ) {
                return "redirect:/error";
            }

            List<RafpAgentRetour> agents = employeurService.getAgentByEmployeurId(id_emp);
            RafpEmployeur employeur = employeurService.getEmployeurById(id_emp);
            double totalMontantRetour = agents.stream()
                    .mapToDouble(RafpAgentRetour::getMnt_retour)
                    .sum();
            model.addAttribute("agents", agents);
            model.addAttribute("employeur", employeur);
            model.addAttribute("totalMontantRetour", totalMontantRetour);


            return "donneesEmployeur";
        }catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }
        catch (SQLException e) {
            logger.error("ErreurBDD - viewDonneesEmployeur - Erreur : {}", e.getMessage(), e);
            return "redirect:/error";
        } catch (Exception e) {
            logger.error("Erreur - viewDonneesEmployeur - Erreur : {}", e.getMessage(), e);
            return "redirect:/error";
        }
    }


    @GetMapping("/rechercheEmployeur")
    public  String viewRechercheEmployeur(HttpServletRequest request) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "rechercheEmployeur";
    }

    @GetMapping(value = "/rechercheEmployeur/search", produces = "application/json")
    public ResponseEntity<List<RafpEmployeur>> viewRechercheEmployeurSearch(HttpServletRequest request, @RequestParam String recherche) {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<RafpEmployeur> employeurs = employeurService.getEmployeurBySearch(recherche);
            return ResponseEntity.ok(employeurs);

        }catch (SQLException e) {
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
    public  String viewGestionEmployeur(HttpServletRequest request) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        return "gestionEmployeur";
    }

    @GetMapping("/gestionEmployeur/modifier")
    public  String viewGestionEmployeurModifier(HttpServletRequest request, Model model) {
        try{
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<RafpEmployeur> employeurs = employeurService.getEmployeur();
            model.addAttribute("employeurs", employeurs);
            return "gestionEmployeurModifier";

        }catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
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
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
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
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
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

    @GetMapping("/ajoutEmployeur/{noInsee}")
    public String viewAjoutEmployeur(HttpServletRequest request, Model model, @PathVariable String noInsee) {
        try{
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            List<RafpEmployeur> employeurs =  employeurService.getEmployeur();
            RafpAgent agent = agentService.getAgentByNoInsee(noInsee);
            logger.info(employeurs.toString());
            model.addAttribute("employeurs", employeurs);
            model.addAttribute("agent", agent);
            return "ajoutEmployeur";

        }catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }
        catch (Exception e){
            return "errorPage/errorBDD";
        }
    }

    @PostMapping("/ajoutEmployeur/add")
    public ResponseEntity<String> viewAjoutEmployeurAdd(HttpServletRequest request, @RequestBody Map<String, String> requestData, Model model) throws Exception {
        String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
        autorisationService.verifAutorisation(idEncrypt);
        String no_insee = requestData.get("noInsee");
        String idEmpStr = requestData.get("idEmployeur");
        String montantStr = requestData.get("montant");
        if (no_insee == null || no_insee.isEmpty()) {
            return new ResponseEntity<>("Le numéro INSEE ne peut pas être vide", HttpStatus.BAD_REQUEST);
        }
        if (idEmpStr == null || idEmpStr.isEmpty()) {
            return new ResponseEntity<>("L'identifiant employeur ne peut pas être vide", HttpStatus.BAD_REQUEST);
        }
        if (montantStr == null || montantStr.isEmpty()) {
            return new ResponseEntity<>("Le montant ne peut pas être vide", HttpStatus.BAD_REQUEST);
        }
        int id_emp = Integer.parseInt(requestData.get("idEmployeur"));
        int montant = Integer.parseInt(requestData.get("montant"));

        try {
            logger.info(requestData.toString());

            boolean vRetour = employeurService.insertEmployeurAdd(no_insee, id_emp , montant);
            if (vRetour) {
                agentService.updateTotalRetourByAgent(no_insee);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (UAException e) {
            logger.error("Erreur UA - viewAjoutEmployeurAdd - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - viewAjoutEmployeurAdd - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - viewAjoutEmployeurAdd - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/ajoutEmployeur/delete/{noInsee}/{id_emp}")
    public ResponseEntity<String> viewAjoutEmployeurDelete(HttpServletRequest request, @PathVariable String noInsee, @PathVariable int id_emp) {

        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            logger.info("Suppression employeur - noInsee: {} - idEmployeur: {}", noInsee, id_emp);
            boolean vRetour = employeurService.deleteDonneeEmployeur(noInsee, id_emp);
            if (vRetour) {
                agentService.updateTotalRetourByAgent(noInsee);
                return new ResponseEntity<>("La suppression du retour est effectuée", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Erreur dans la suppression du retour", HttpStatus.BAD_REQUEST);
            }
        } catch (UAException e) {
            logger.error("Erreur UA - viewAjoutEmployeurDelete - noInsee: {} - noInsee: {} - Erreur : {}", noInsee, id_emp, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - viewAjoutEmployeurDelete - noInsee: {} - noInsee: {} - Erreur : {}", noInsee, id_emp, e.getMessage());
            return new ResponseEntity<>("Erreur base de données", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - viewAjoutEmployeurDelete - noInsee: {} - idEmp: {} - Erreur : {}", noInsee, id_emp, e.getMessage());
            return new ResponseEntity<>("Erreur inconnue",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/modifierEmployeur/{no_insee}/{id_emp}")
    public String viewModifierEmployeur(HttpServletRequest request, Model model, @PathVariable String no_insee, @PathVariable int id_emp) {
        try{
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            RafpEmployeur employeur =  employeurService.getEmployeurById(id_emp);
            RafpAgent agent = agentService.getAgentByNoInsee(no_insee);
            RafpRetour retour = retourService.getRetourByInseeEmployeur(id_emp, no_insee);
            logger.info(employeur.toString());
            model.addAttribute("employeurs", employeur);
            model.addAttribute("agent", agent);
            model.addAttribute("retour", retour);
            return "modifierEmployeur";

        }
        catch (NonAutorisationException e) {
            return "errorPage/accessRefused";
        }
        catch (SQLException e){
            logger.error("Erreur BDD - ViewModifierEmployeur  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorBDD";
        }catch (Exception e){
            logger.info("Erreur Load - ViewModifierEmployeur  - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";
        }
    }

    @PostMapping("/ajoutEmployeur/modifier")
    public ResponseEntity<String> viewModifierEmployeurModif(HttpServletRequest request, @RequestBody Map<String, String> requestData, Model model) {
        try {
            String idEncrypt = ((AttributePrincipal) request.getUserPrincipal()).getAttributes().get("supannRefId").toString();
            autorisationService.verifAutorisation(idEncrypt);
            String no_insee = requestData.get("noInsee");
            String idEmpStr = requestData.get("idEmployeur");
            String montantStr = requestData.get("montant");
            // Vérification des entrées vides ou nulles
            if (no_insee == null || no_insee.isEmpty()) {
                return new ResponseEntity<>("Le numéro INSEE ne peut pas être vide", HttpStatus.BAD_REQUEST);
            }
            if (idEmpStr == null || idEmpStr.isEmpty()) {
                return new ResponseEntity<>("L'identifiant employeur ne peut pas être vide", HttpStatus.BAD_REQUEST);
            }
            if (montantStr == null || montantStr.isEmpty()) {
                return new ResponseEntity<>("Le montant ne peut pas être vide", HttpStatus.BAD_REQUEST);
            }
            int id_emp = Integer.parseInt(requestData.get("idEmployeur"));
            int montant = Integer.parseInt(requestData.get("montant"));
            logger.info(requestData.toString());
            boolean vRetour = retourService.updateRetourByInseeEmployeur(id_emp, no_insee, montant);
            if (vRetour) {
                agentService.updateTotalRetourByAgent(no_insee);
                return new ResponseEntity<>("Modification du retour effectuée",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Erreur dans la modification du retour",HttpStatus.BAD_REQUEST);
            }
        } catch (UAException e) {
            logger.error("Erreur UA - viewModifierEmployeurModif - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - viewModifierEmployeurModif - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - viewModifierEmployeurModif - requestData : {} - Erreur : {}", requestData, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
