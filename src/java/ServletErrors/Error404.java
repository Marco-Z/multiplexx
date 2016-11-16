/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServletErrors;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marco Zugliani
 */
public class Error404 extends HttpServlet {

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
            String html =   "<!DOCTYPE html>" +
                            "<html>" +
                            "	<head>" +
                            "  	<meta charset='utf-8'>" +
                            "  	<title> 404 </title>" +
                            "      <meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'>" +
                            "      <link rel='stylesheet' type='text/css' href='css/styles.css'>" +
                            "      <style type='text/css'>" +
                            "        * {" +
                            "          font-family: 'Courier New' !important;" +
                            "            font-weight: 100;" +
                            "        }" +
                            "" +
                            "        body, div {" +
                            "            margin: 0;" +
                            "        }" +
                            "" +
                            "        .top, .full {" +
                            "            position: absolute;" +
                            "            overflow: hidden;" +
                            "            top: 0;" +
                            "        }" +
                            "" +
                            "        .padding {" +
                            "            padding: 0 1em;" +
                            "        }" +
                            "" +
                            "        .center {" +
                            "          text-align: center;" +
                            "          margin: auto;" +
                            "        }" +
                            "" +
                            "        .border {" +
                            "          color: beige;" +
                            "            text-shadow: 1px 0 1px black, 0 1px 1px black;" +
                            "        }" +
                            "" +
                            "        .no-margin {" +
                            "            margin: 0;" +
                            "        }" +
                            "" +
                            "        .full {" +
                            "            object-fit: cover;" +
                            "            width: 100%;" +
                            "            padding: 0;" +
                            "            margin: 0;" +
                            "        }" +
                            "" +
                            "        h1.big {" +
                            "          font-size: 8em;" +
                            "        }" +
                            "" +
                            "        .image {" +
                            "          height: 100vh;" +
                            "          background-size: cover;" +
                            "          background-repeat: no-repeat;" +
                            "          background-position: top;" +
                            "        }" +
                            "" +
                            "      </style>" +
                            "	</head>" +
                            "	" +
                            "	<body>" +
                            "      <img class='image padding full' src='sfondo'>" +
                            "      <div class='top padding'>" +
                            "        <h1 class='border'>commento</h1>" +
                            "        <h1 class='big border no-margin'>404!</h1>" +
                            "      </div>" +
                            "  </body>" +
                            "</html>";

            String[] sfondi = {  "http://data.whicdn.com/images/27950923/original.gif",
                                "http://photos.imageevent.com/afap/wallpapers/movies/starwarsanewhope/These%20are%20not%20the%20droids....jpg",
                                "http://i1.wp.com/www.foreverwriters.com/wp-content/uploads/2014/01/hobbits-hiding-from-nazgul.jpg"

            };
            String[] commenti = {  "We can not find the page you want most!",
                                    "This is not the page you were looking for",
                                    "The Nazgûl are wandering through Middle Earth to bring back the One Page"        
            };

        response.setContentType("text/html;charset=UTF-8");
        Random p = new Random();
        int n = sfondi.length;
        int i = p.nextInt(n);
        html = html.replace("sfondo", sfondi[i]);
        html = html.replace("commento", commenti[i]);
        try (PrintWriter out = response.getWriter()) {
            out.println(html);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
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
