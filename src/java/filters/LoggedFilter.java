package filters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lorenzo
 */
@WebFilter(filterName = "LoggedFilter", urlPatterns = {"/ServletLogged/*"})
public class LoggedFilter implements Filter {
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req =(HttpServletRequest)request;
        HttpServletResponse res =(HttpServletResponse)response;
        System.out.println("un utente vuole fare accesso alla zona protetta. l'utente è:"+req.getSession().getAttribute("nome"));
        if (req.getSession().getAttribute("autenticated")== null){
            req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""+ "non sei loggato al sito\n");
            res.sendRedirect("/Project/index.jsp");
        }
        else chain.doFilter(request, response);
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter has been initialized");
    }

    @Override
    public void destroy() {
        System.out.println("filter has been destroyed");
    }
}
