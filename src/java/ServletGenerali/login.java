/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletGenerali;

import dbAdministrator.dbManager;
import dbAdministrator.user;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *si occupa del log-in dell'utente nel sito
 * @author Lorenzo
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {    
    private dbManager manager;
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (dbManager)super.getServletContext().getAttribute("dbmanager");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //realizzare la connessione al server e chiedere se l'utente è presente nel database. nel caso non sia presente
        //mandare messaggio di errore in qualche modo. altrimenti aggiungere alla sessione il parametro autenticate=true.
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println("mi è arrivata una richiesta di login:"+email+"  "+password);
        user User;
        try {
            User = manager.authenticate(email, password);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        /*Se non riesco ad autenticare l'utente metto un messaggio di errore nella
        //richiesta in modo da poterlo recuperare. altrimenti segno nella sessione un
        //flag "autenticated" = true, cosìcche le jsp sappiano che l'utente è autenticato correttamente
        */
        if (User==null) {
            req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""
                + "e-mail o password non corretti\n");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        }
        else {
            req.getSession().setAttribute("autenticated", true);
            req.getSession().setAttribute("id_utente", User.getId());
            req.getSession().setAttribute("email", User.getEmail());
            req.getSession().setAttribute("nome", User.getNome());
            req.getSession().setAttribute("cognome", User.getCognome());
            req.getSession().setAttribute("credito", User.getCredito());
            req.getSession().setAttribute("tipo", User.getTipo());
            resp.sendRedirect("profile.jsp");
        }
    }
    
}
