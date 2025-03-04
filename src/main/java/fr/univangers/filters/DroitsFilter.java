package fr.univangers.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


//pour un filtre sur toute l'appli mettre @Component, sinon mettre @Webfilter
//@Component
//@Order(1)
@WebFilter("/ficheEtud")
public class DroitsFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(DroitsFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.info("DoFilter ..");

        String accessPage = "";
        HttpSession session = ((HttpServletRequest)request).getSession(true);

        //Récupération du Login
        String login = ((HttpServletRequest)request).getRemoteUser().toLowerCase();

        logger.info("login {}", login);

        if (session.getAttribute("statut")==null) {
            //test de session expirée ou non
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/sessionExpired");
        }
        else {
            if (session.getAttribute("statut").toString().equals("perso")) {
                /*try {
                   //j'appel ma fonction qui me défini
                   //si je vais avoir ou non accès à l'application
                } catch (SQLException | NamingException e) {
                    logger.info("Erreur BDD, login : " + login);
                    e.printStackTrace();
                    ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/erreurBDD");
                } catch (Exception e) {
                    e.printStackTrace();
                    ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/errorload");
                }*/
            }
            if (accessPage.equals("X")) {
                //j'ai accès je continue
                chain.doFilter(request, response);
            } else {
                //pas accès, page d'erreur
                ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/accessRefused");
            }
        }
    }
}
