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
public class PDFController {
    private final Logger logger = LoggerFactory.getLogger(PDFController.class);
    private static final List<Person> persons = new ArrayList<>();

    private final PersonnelService personnelService;
    private final AppelExterne appelExterne;

    public PDFController(PersonnelService personnelService, AppelExterne appelExterne) {
        this.personnelService = personnelService;
        this.appelExterne = appelExterne;
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


}
