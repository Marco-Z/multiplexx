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
 * processa la richiesta di registrazione inserendo l'utente nel database
 * @author Lorenzo
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {
    private dbManager manager;
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (dbManager)super.getServletContext().getAttribute("dbmanager");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //se è possibile creo un nuovo utente. poi assegno i dati necessari alla sessione e metto tra le
        //altre cose il parametro autenticated della sessione = true.
        //se i dati sono sbagliati mando messaggio di errore.
        String nome = req.getParameter("nome");
        String cognome = req.getParameter("cognome");
        
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");
        System.out.println("mi è arrivata una richiesta di registrazione:"+nome+"  "+cognome+"   "+email+"  "+password+"  "+password2);
        boolean riuscito = true;
        if (nome==null) riuscito = false;
        if (nome.length()<3) riuscito = false;
        if (cognome==null) riuscito = false;
        if (cognome.length()<3) riuscito = false;
        if (email==null) riuscito = false;
        if (email.length()<5) riuscito = false;
        if (password==null) riuscito = false;
        if (password.length()<3) riuscito = false;
        if (password2==null) riuscito = false;
        if (password2.length()<3) riuscito = false;
        if (!password.equals(password2)) riuscito = false;
        if (riuscito){
            user u;
            try {
                u = manager.registerUser(email, nome, cognome, password);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
            /*Se non riesco ad autenticare l'utente metto un messaggio di errore nella
            //richiesta in modo da poterlo recuperare. altrimenti segno nella sessione un
            //flag "autenticated" = true, cosìcche le jsp sappiano che l'utente è autenticato correttamente
            */
            if (u==null) {
                req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""
                        + "L'indirizzo email è già registrato al sito\n");
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            }
            else {
                req.getSession().setAttribute("autenticated", true);
                req.getSession().setAttribute("id_utente", u.getId());
                req.getSession().setAttribute("email", u.getEmail());
                req.getSession().setAttribute("nome", u.getNome());
                req.getSession().setAttribute("cognome", u.getCognome());
                req.getSession().setAttribute("credito", u.getCredito());
                req.getSession().setAttribute("tipo", u.getTipo());
                req.getSession().setAttribute("messaggio", req.getSession().getAttribute("messaggio")+""
                        + "La registrazione è andata a buon fine\n");
                req.getRequestDispatcher("profile.jsp").forward(req, resp);            }        
        }
        else{
            req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""
                    + "alcuni degli elementi inseriti non sono validi\n");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        }
    }
}
