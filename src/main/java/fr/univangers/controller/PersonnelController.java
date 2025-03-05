package fr.univangers.controller;

import fr.univangers.classes.Person;
import fr.univangers.classes.Personnel;
import fr.univangers.classes.User;
import fr.univangers.exceptions.NonAutorisationException;
import fr.univangers.service.AppelExterne;
import fr.univangers.service.AutorisationService;
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

    public PersonnelController(PersonnelService personnelService, AppelExterne appelExterne, AutorisationService autorisationService) {
        this.personnelService = personnelService;
        this.appelExterne = appelExterne;
        this.autorisationService = autorisationService;
    }

    @GetMapping("/personList")
    public String viewPersonList(Model model) {
        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/gestionEmployeur")
    public String viewGestionEmployeur(Model model) {
        model.addAttribute("persons", persons);
        return "gestionEmployeur";
    }

    /**
     * Exemple d'appel de controller
     * @param request : Sert à récupérer le login
     * @param model : Model pour afficher la page
     * @return : Une page
     */
    @GetMapping("/listeRafp")
    public String viewRafp(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "listeRafp";
    }

    @GetMapping("/saisieEmployeur")
    public String viewSaisieEmployeur(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "saisieEmployeur";
    }

    @GetMapping("/saisieAgent")
    public String viewSaisieAgent(HttpServletRequest request, Model model) {
        model.addAttribute("persons", persons);
        return "saisieAgent";
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


    /**
     * Export un PDF
     * @param response : Sert pour le PDF
     */
    //Exemple pris ici : https://www.codejava.net/frameworks/spring-boot/pdf-export-example
    @GetMapping(value = "/PDF", produces = "application/pdf")
    public void exportPDF(HttpServletResponse response) {
        logger.info("Controller qui retourne un PDF.");
        try {
            List<Person> lesPersonnels = persons;
            if (lesPersonnels != null){
                //Si on souhaite que le PDF soit téléchargé :
//                String headerKey = "Content-Disposition";
//                String headerValue = "attachment; filename=export.pdf";
//                response.setHeader(headerKey, headerValue);

                ExportPDF exporter = new ExportPDF(lesPersonnels);
                exporter.export(response);
            }
        }
        catch (Exception e) {
            logger.error("Erreur - exportPDF - Erreur : {}", e.getMessage(), e);
        }
    }


    /**
     * Export un PDF avec des paramètres
     * @param response : Sert pour le PDF
     */
    @PostMapping(value = "/personList/pdf", produces = "application/pdf")
    public void exportPersonelListPDF(HttpServletRequest request,HttpServletResponse response) {
        logger.info("Controller qui retourne un PDF avec paramètre.");
        try {
            String numero = request.getParameter("numero");
            if (numero != null && !numero.isEmpty()) {
                List<Person> lesPersonnels = new ArrayList<>();
                persons.stream().filter(person -> person.getNumero() == Integer.parseInt(numero)).forEach(lesPersonnels::add);
                ExportPDF exporter = new ExportPDF(lesPersonnels);
                exporter.export(response);
            }
        }
        catch (Exception e) {
            logger.error("Erreur - exportPDF - Erreur : {}", e.getMessage(), e);
        }
    }


    /**
     * Exemple d'API externe - Qui récupère des informations
     * @param model : Model pour afficher la page
     * @return : ...
     */
    @GetMapping("/appelExterne")
    public String viewAppelExterne(Model model) {
        try {
            String login = "s.bernard";
            User user = appelExterne.info(login);
            model.addAttribute("user", user);
            return "appelExterne";
        }
        catch (Exception e) {
            logger.error("Erreur - appelExterne - Erreur : {}", e.getMessage(), e);
            return "errorPage/errorLoad";
        }
    }


    @GetMapping(value = "/lesPersonnels", produces = "application/json")
    public ResponseEntity<List<Person>> lesPersonnels() {
        logger.info("Controller qui retourne une liste de personnels.");
        try {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Erreur - lesPersonnels - Erreur : {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT pour ajouter une nouvelle information (exemple projet MonProfil sur Git)
     * @param alias : Nom de l'alias à ajouter
     * @return : Retourne une chaine de caractère ....
     */
    @PutMapping(value = "/{alias}", produces = "application/json")
    public ResponseEntity<String> updateAlias(@PathVariable String alias) {
        logger.info("Controller qui ajout un alias.");
        try {
            String vRetour = personnelService.insert(alias);
            return new ResponseEntity<>(vRetour, HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - updateAlias - alias : {} - Erreur : {}", alias, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - updateAlias - alias : {} - Erreur : {}", alias, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST pour modifier une donnée (exemple projet MonProfil sur Git)
     * @param aliasOld : Ancien alias
     * @param aliasNew : Nouveau alias
     * @return : Retourne une chaine de caractère ....
     */
    @PostMapping(value = "/updateAlias/{aliasOld}/{aliasNew}", produces = "application/json")
    public ResponseEntity<String> insertAlias(@PathVariable String aliasOld, @PathVariable String aliasNew) {
        logger.info("Controller qui met à jour un alias.");
        try {
            String vRetour = personnelService.update(aliasOld, aliasNew);
            return new ResponseEntity<>(vRetour, HttpStatus.OK);
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - insertAlias - aliasOld : {} - aliasNew : {} - Erreur : {}", aliasOld, aliasNew, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - insertAlias - aliasOld : {} - aliasNew : {} - Erreur : {}", aliasOld, aliasNew, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE pour supprimer une donnée (exemple projet MonProfil sur Git)
     * @param alias : Alias à supprimer
     * @return : Status OK si ok ; Status BAD_REQUEST si erreur
     */
    @DeleteMapping(value = "/deleteUser/{alias}", produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable String alias) {
        logger.info("Controller qui supprime un alias.");
        try {
            boolean vRetour = personnelService.delete(alias);
            if (vRetour){
                //Update OK
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                //Erreur dans le delete
                logger.error("Erreur lors de la suppression de l'alias : {}",alias);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (SQLException e) {
            logger.error("Erreur BDD - deleteUser - alias : {} - Erreur : {}", alias, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            logger.error("Erreur - deleteUser - alias : {} - Erreur : {}", alias, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
