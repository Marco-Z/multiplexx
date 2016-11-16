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
 *
 * @author Marco Zugliani
 */
@WebServlet(name = "cancella", urlPatterns = {"/admin/cancella"})
public class cancella extends HttpServlet { 
    private dbManager manager;
    @Override
    public void init() throws ServletException {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (dbManager)super.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            String email = manager.getMail(Integer.parseInt(request.getParameter("id_u")));
            System.out.println(email);
            try {
                int id_p = Integer.parseInt(request.getParameter("id_p"));
                System.out.println(id_p);
                int id_u = Integer.parseInt(request.getParameter("id_u"));
                System.out.println(id_u);
                if(!manager.iniziato(id_p)){
                    double accredito = manager.getPrezzo(id_p) * 0.8;
                    System.out.println("calcolo 80%");
                    manager.deleteBooking(id_p);
                    System.out.println("cancello prenotazione");
                    int credito = manager.accredita(id_u, accredito);
                    request.getSession().setAttribute("credito", credito);
                    System.out.println("accredito");
                }
                else {
                    request.getSession().setAttribute("errore", request.getSession().getAttribute("errore")+""
                            + "<p>Lo spettacolo è già iniziato</p>"
                            + "<p>Impossibile annullare la prenotazione</p>");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(cancella.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("http://localhost:8084/WebProgProject/admin/cancellaPrenotazione.jsp?cancella=" + email);
            
        } catch (SQLException ex) {
            Logger.getLogger(cancella.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
