package fr.univangers.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter({"/valident/*",
        "/moncompte/*",
        "/compteFonctionnel/*",
        "/listeDiffusion/*",
        "/index.jsp",
        "/"
})
public class IEFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String userAgent = ((HttpServletRequest) servletRequest).getHeader("user-agent");
        if (userAgent == null ||
                userAgent.equals("Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko") ||
                userAgent.equals("Mozilla/5.0 (Windows NT 10.0; Trident/7.0; rv:11.0) like Gecko") ||
                userAgent.equals("Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko") ||
                userAgent.equals("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko") ||
                userAgent.equals("Mozilla/5.0 (Windows NT 6.3; ARM; Trident/7.0; Touch; rv:11.0) like Gecko") ||
                userAgent.equals("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.0; Trident/5.0)") ||
                userAgent.equals("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; .NET4.0C; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)") ||
                userAgent.equals("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)")) {
            //ie donc erreur
            ((HttpServletResponse) servletResponse).sendRedirect(((HttpServletRequest) servletRequest).getContextPath() + "/errorNavigateur");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
