package fr.univangers.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        logger.info("Page index ..");
        String message = "Bienvenue sur votre application Spring Boot + JSP";
        model.addAttribute("message", message);
        return "index";
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
