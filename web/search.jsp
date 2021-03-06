
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.util.Iterator"%>
<%@page import="dbAdministrator.spettacolo"%>
<%@page import="dbAdministrator.dbManager"%>
<%@page import="java.util.ArrayList"%>

<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Ricerca: <%=request.getParameter("q")%></title>
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
        <div class="container jumbotron outer">
<% 
    String query = request.getParameter("q");
    ArrayList<spettacolo> results = db.cercaSpettacoli(query);
    Iterator it = results.iterator();
    if(!it.hasNext()){
%>
        <p>Nessun film trovato per: <%=query%></p>
<%
    }
    while (it.hasNext()){
        spettacolo spett = (spettacolo)it.next();
        %>
        <div id="film" class="inner">
            <div id="immagineFilm">
                <form method="GET" action="film.jsp">
                    <button type="submit" class="locandina">
                        <img class="shadow loc_img" src="<%=spett.getLocandina() %>" width="200px" height="300px">
                    </button>
                    <input name="id" type="hidden" value="<%=spett.getId() %>"><br>
                </form>
            </div>
        </div>
<%
    }
%>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>