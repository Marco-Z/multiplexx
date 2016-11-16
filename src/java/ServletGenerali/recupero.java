/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletGenerali;

import dbAdministrator.dbManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cerca nel database l'utente identificato dall'indirizzo e-mail e 
 * manda una mail per recuperare la password
 * @author Marco Zugliani
 */
@WebServlet(name = "recupero", urlPatterns = {"/recupero"})
public class recupero extends HttpServlet {
    private dbManager manager;
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (dbManager)super.getServletContext().getAttribute("dbmanager");
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = new String();
        String email = req.getParameter("email");
        System.out.println("mi è arrivata una richiesta di recupero password:" + email);
        try {
            password = manager.recuperaPassword(email);
        } catch (SQLException ex) {
            Logger.getLogger(recupero.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (password==null){
                req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""
                        + "L'indirizzo e-mail inserito non è presente nel database\n");
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
        }
        else{
            SendMail.recupera(email, password);
            req.getSession().setAttribute("errore", req.getSession().getAttribute("errore")+""
                    + "È stata spedita una e-mail all'indirizzo specificato con la password\n");
            req.getRequestDispatcher("profile.jsp").forward(req, resp);
        }
    }
}
