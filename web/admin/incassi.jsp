<%-- 
    Document   : cronologia
    Created on : 7-ago-2015, 9.38.08
    Author     : Marco Zugliani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="dbAdministrator.filmAdmin"%>
<%@page import="dbAdministrator.dbManager"%>

<% 
    if (session.getAttribute("autenticated") == null){ //non loggato
        String redirectURL = "../profile.jsp";
        response.sendRedirect(redirectURL);
    }
    if(session.getAttribute("autenticated")!=null){
        if(!session.getAttribute("tipo").equals("ADMIN")){ //non amministratore
            String redirectURL = "../profile.jsp";
            response.sendRedirect(redirectURL);
        }
    }
%>

<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
    ArrayList<filmAdmin> fv = db.incassiFilm();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Incassi</title>
        <link rel="icon" type="image/png" href="../img/favicon.ico">
        
        <script type="text/javascript" src="../script/jquery-2.1.0.min.js"></script>
        
        <link href="../css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="../script/bootstrap.min.js"></script>
        
        <link rel="stylesheet" type="text/css" href="../css/material.min.css">
        <script type="text/javascript" src="../script/material.min.js"></script>
                
        <link href="../css/styles.css" rel="stylesheet">
        <script type="text/javascript" src="../script/script.js"></script>
    </head>
    
    <body>
        <%@include file="../header-in.html" %>
        <div class="top">
            <%@include file="../errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron">
            <h2 class="text-center">Lista di incassi per film</h2>
            <div id="incassi" class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 well">
                <table class="table table-striped table-hover ">
                    <thead>
                        <tr class="info">
                            <th>Film</th>
                            <th>Incasso totale</th>
                        </tr>
                    </thead>
                    <tbody>
                          <%Iterator fi = fv.iterator();
                            while(fi.hasNext()){
                                filmAdmin f = (filmAdmin) fi.next();
                          %>
                            <tr>
                                <td><%=f.getTitolo()%></td>
                                <td>&euro; <%=f.getIncasso()%>.00</td>
                            </tr>
                          <%}%>
                    </tbody>
                </table>
            </div>
        </div>
        <%@include file="../footer.jsp" %>    
    </body>
</html>
