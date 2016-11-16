<%-- 
    Document   : cronologia
    Created on : 7-ago-2015, 9.38.08
    Author     : Marco Zugliani
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Iterator"%>
<%@page import="dbAdministrator.prenotazione"%>
<%@page import="dbAdministrator.dbManager"%>

<% 
    if (session.getAttribute("autenticated") == null){ //non loggato
    String redirectURL = "profile.jsp";
    response.sendRedirect(redirectURL);
    }
%>

<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
%>
<% 
    int id = Integer.parseInt(request.getParameter("id_utente"));
    ArrayList<prenotazione> pv = db.getPrenotazioni(id);
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Cronologia acquisti</title>
        <link rel="icon" type="image/png" href="img/favicon.ico">
        
        <script type="text/javascript" src="script/jquery-2.1.0.min.js"></script>
        
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="script/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="css/material.min.css">
        <script type="text/javascript" src="script/material.min.js"></script>
               
        <link href="css/styles.css" rel="stylesheet">
        <script type="text/javascript" src="script/script.js"></script>
    </head>
    
    <body>
        <%@include file="header.html" %>
        <div class="top">
            <%@include file="errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron">
            
          <%if(pv.isEmpty()){ %>
            <p class="text-center">Non hai acuistato nessun biglietto</p>    
          <%} else {%>
            
            <p>Biglietti acqistati:</p>
            <table class="table table-hover">
                <tr class="head">
                    <th>Numero prenotazione</th>
                    <th>Data prenotazione</th>
                    <th>Film</th>
                    <th>Tipo film</th>
                    <th>Data proiezione</th>
                    <th>Sala</th>
                    <th>Posto</th>
                    <th>Tipo biglietto</th>
                </tr>
             <% Iterator pi = pv.iterator();
                while(pi.hasNext()){
                    prenotazione p = (prenotazione) pi.next(); 
                    Timestamp op = p.getDataPrenotazione();
                    Timestamp pr = p.getDataProiezione();
                    SimpleDateFormat sdfdata= new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat sdfora = new SimpleDateFormat("HH:mm");
                    String data_op = sdfdata.format(op);
                    String ora_op = sdfora.format(op);
                    String data_pr = sdfdata.format(pr);
                    String ora_pr = sdfora.format(pr);
                    %>
                    <tr>
                        <td><%=p.getPrenotazione() %></td>
                        <td><%=data_op%> <%=ora_op%></td>
                        <td><%=p.getFilm() %></td>
                        <td><%=p.getTipoFilm() %></td>
                        <td><%=data_pr%> <%=ora_pr%></td>

                        <td>Sala <%=p.getSala() %></td>
                        <td><%=p.getPosto() %></td>
                        <td><%=p.getTipoBiglietto() %></td>
                    </tr>
                    
              <%}%>
            </table>
          <%}%>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>

