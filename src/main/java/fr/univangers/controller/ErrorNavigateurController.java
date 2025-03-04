package fr.univangers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorNavigateurController {

    @GetMapping("/errorNavigateur")
    public String errorNavigateur(){
        return "errorPage/errorNavigateur";
    }

}
