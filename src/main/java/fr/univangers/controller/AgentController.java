package fr.univangers.controller;

import fr.univangers.classes.RafpAgent;
import fr.univangers.classes.RafpEmployeur;
import fr.univangers.exceptions.UAException;
import fr.univangers.service.AgentService;
import fr.univangers.service.EmployeurService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

@Controller
public class AgentController {
    private final Logger logger = LoggerFactory.getLogger(AgentController.class);

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }


    @GetMapping("/listeRafp/agents")
    public ResponseEntity<List<RafpAgent>> viewListEmployeurs() {
        try {
            List<RafpAgent> agents = agentService.getAgent();
            if (agents == null || agents.isEmpty()) {
                return ResponseEntity.ok(agents);
            }
            return ResponseEntity.ok(agents);
        } catch (SQLException e) {
            logger.error("Erreur BDD - viewListEmployeurs - Erreur : {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erreur - viewListEmployeurs - Erreur : {}", e.getMessage(), e);
        }
        return ResponseEntity.ok(List.of());
    }

}
