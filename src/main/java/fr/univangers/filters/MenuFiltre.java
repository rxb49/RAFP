package fr.univangers.filters;

import fr.univangers.classes.Menu;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MenuFiltre implements Filter {

    private final MessageSource messageSource;

    public MenuFiltre(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //Url en cours
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String currentUrl = httpRequest.getRequestURI().replace(((HttpServletRequest) servletRequest).getContextPath(), "");

        //Menu de l'application
        Locale locale = LocaleContextHolder.getLocale();
        Menu m1 = new Menu(messageSource.getMessage("titre.accueil", null, locale), "/", "bi-clock-fill");
        Menu m2 = new Menu(messageSource.getMessage("titre.dev", null, locale), "/gestionEmployeur", "bi-person-fill");
        Menu m3 = new Menu(messageSource.getMessage("titre.3", null, locale), "/hello", "bi-briefcase-fill");

        List<Menu> leMenu = List.of(m1, m2, m3);
        servletRequest.setAttribute("leMenu", leMenu);

        //On récupère l'object Menu en fonction de la page où on se trouve. Cela permet de récupérer le titreLong de la page s'il y en a un et l'icone.
        Menu titrePage =
                leMenu.stream()
                .filter(menu -> menu.getLien().equals(currentUrl) || currentUrl.startsWith(menu.getLien()+";"))
                .findFirst()
                .map(menu -> { menu.setActif(true); return menu; }) // Active l'objet trouvé
                .orElse(null);

        servletRequest.setAttribute("currentUrl", currentUrl);
        servletRequest.setAttribute("titrePage", titrePage);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
