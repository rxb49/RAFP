package fr.univangers;

import fr.univangers.classes.Personnel;
import fr.univangers.classes.User;
import fr.univangers.controller.MainController;
import fr.univangers.controller.PersonnelController;
import fr.univangers.service.AppelExterne;
import fr.univangers.service.PersonnelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("qual")
@SpringBootTest
@AutoConfigureMockMvc// (addFilters = false) : Permet de désactiver les filtres, authentification
class ApplicationmodeleApplicationTests {

    @Autowired
    private MainController mainController;
    @Autowired
    private PersonnelController personnelController;

    @Autowired
    private MockMvc mockMvc; //Permet de tester les appels aux Controller, et du coup on ajoute @AutoConfigureMockMvc

    @Autowired
    private PersonnelService personnelService;
    @Autowired
    private AppelExterne appelExterne;

    @Test
    @DisplayName("Controller")
    void testController(){
        assertNotNull(mainController);
        assertNotNull(personnelController);
    }

    @Test
    @DisplayName("Test des routes de PersonnelController")
    void personnelController() {
        try {
            mockMvc.perform(get("/lesPersonnels")
                    .accept(MediaType.APPLICATION_JSON)) //Appel en json
                    .andExpect(status().isOk()) // Status de la page OK
                    //On vérifie le retour de l'API
                    .andExpect(jsonPath("$[0].firstName").value("Laureline"))
                    .andExpect(jsonPath("$[0].lastName").value("Bernard"))
                    .andExpect(jsonPath("$[0].lastName").exists())
            ;
            //Quelque exemple de MockMvc : https://howtodoinjava.com/spring-boot2/testing/spring-boot-mockmvc-example/
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Test du service PersonnelService")
    void personnelService() {
        try {

            Personnel p = personnelService.info("l.bernard");
            assertEquals("laureline", p.getPrenom());

            Personnel p2 = personnelService.info("l.BERNARD");
            assertNotNull(p2);

            String p3 = personnelService.insert(null);
            assertEquals("ok", p3);

            String p4 = personnelService.update(null, null);
            assertEquals("ok", p4);

            boolean p5 = personnelService.delete(null);
            assertTrue(p5);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    @DisplayName("Test du service AppelExterne")
    void appelExterne() {
        try {

            User a1 = appelExterne.info("L.BERNard");
            assertEquals("BERNARD", a1.getLastname());

            User a2 = appelExterne.info("j.allo");
            assertNotNull(a2);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
