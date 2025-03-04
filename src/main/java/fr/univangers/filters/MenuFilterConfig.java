package fr.univangers.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuFilterConfig {

    private final MessageSource messageSource;

    public MenuFilterConfig(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public FilterRegistrationBean<MenuFiltre> menuFiltre() {
        FilterRegistrationBean<MenuFiltre> bean = new FilterRegistrationBean<>();
        bean.setFilter(new MenuFiltre(messageSource));
        bean.addUrlPatterns("/*"); // Appliquer le filtre à toutes les requêtes
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MenuAdminFiltre> menuAdminFiltre() {
        FilterRegistrationBean<MenuAdminFiltre> bean = new FilterRegistrationBean<>();
        bean.setFilter(new MenuAdminFiltre(messageSource));
        bean.addUrlPatterns("/appelExterne");
        return bean;
    }

}