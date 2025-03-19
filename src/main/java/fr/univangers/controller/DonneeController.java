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
import java.util.*;

public class DonneeController {
    private final Logger logger = LoggerFactory.getLogger(DonneeController.class);

    private final DonneeService donneeService;

    public DonneeController(DonneeService donneeService) {
        this.donneeService = donneeService;
    }



    @GetMapping("/ajoutAgent")
    public String viewAjoutAgent() {
        return "ajoutAgent";
    }
    @GetMapping("/modifierAgent")
    public String viewModifierAgent() {
        return "modifierAgent";
    }

    @GetMapping("/ajoutEmployeur")
    public String viewAjoutEmployeur() {
        return "ajoutEmployeur";
    }

    @GetMapping("/modifierEmployeur")
    public String viewModifierEmployeur() {
        return "modifierEmployeur";
    }
}
