package fr.univangers.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;

import java.io.IOException;

@WebFilter("/*")
public class LoginFiltre implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //Récupère le login de la personne connectée pour les logs.
        String login = "";
        try{
            login = ((HttpServletRequest)servletRequest).getRemoteUser().toLowerCase();
        }
        catch(Exception e){}

        ((HttpServletRequest)servletRequest).getSession().setAttribute("login", login);
        ThreadContext.put("login", login);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
