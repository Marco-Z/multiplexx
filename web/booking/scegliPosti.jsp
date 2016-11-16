<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@page language="java" contentType="text/html" %>

<% 
    if (session.getAttribute("autenticated") == null){ //non loggato
    String redirectURL = "../profile.jsp";
    response.sendRedirect(redirectURL);
    }
%>

<%@page import="java.sql.Timestamp"%>
<%@page import="dbAdministrator.sala"%>
<%@page import="dbAdministrator.spettacolo"%>
<%@page import="java.util.Iterator"%>
<%@page import="dbAdministrator.dbManager"%>
<%@page import="java.util.List"%>
<%@page import="dbAdministrator.film"%>
<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
    int id_s = Integer.parseInt(request.getParameter("id_s"));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Scegli i posti</title>
        <link rel="icon" type="image/png" href="../img/favicon.ico">
        
        <script type="text/javascript" src="../script/jquery-2.1.0.min.js"></script>
        
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="../script/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="../css/material.min.css">
        <script type="text/javascript" src="../script/material.min.js"></script>
        
        <script type="text/javascript" src="../script/script.js"></script>
        <link href="../css/styles.css" rel="stylesheet">
    </head>
    
    <body>
        
        <%@include file="../header-in.html" %>
        <div class="top">
            <%@include file="../errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron">
            <div id="posti">
                <form action="confermaposti.jsp">
                <table class="center">
                    <tr>
                      <td colspan="100%">
                        <div class="screen"></div> 
                      </td>
                    </tr>
                    <% out.print(db.griglia(id_s));%>
                </table>
                    <%--<div class="booking-details text-center padding-top">--%>
                    <input type="hidden" name="spetid" value='<% out.print(id_s); %>'>
                <input type="submit" value="Prosegui" class="btn btn-raised red-button checkout-button text-center" >
                <%--</div> --%>
                </form>
                <!--
                <div class="booking-details text-center padding-top">
                    <h2>Dettagli prenotazione:</h2>
                    <h3> Posti selezionati (<span id="counter">0</span>):</h3>
                    <table id="selected-seats" class="table booking center"></table>        

                    <p>Totale: <b>&euro; <span id="total">0</span></b></p>

                    <div class="togglebutton"></div>


                    <button class="btn btn-raised red-button checkout-button">Conferma e prosegui</button>

                    <div id="legend" class="seatCharts-legend"></div>
                </div>
                --%>
            </div>
          <%if(session.getAttribute("autenticated")!=null){  //se l'utente è autenticato guarda se è admin
            if(session.getAttribute("tipo").equals("ADMIN")){   //se è admin mostra l'incasso %> 
            <div class="text-center well col-md-6 col-md-offset-3">
                <p>Incasso totale per questa proiezione: € <%=db.getIncasso(id_s) %></p>
            </div>
          <%}}%>
            <div class="padding-top">
            </div>
                
        </div>
        <%@include file="../footer.jsp" %>        
    </body>
</html>
