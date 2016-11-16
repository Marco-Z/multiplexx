<%-- 
    Document   : index
    Created on : 19-mag-2015, 10.27.47
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

<%@page import="java.util.Iterator"%>
<%@page import="dbAdministrator.prezzo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dbAdministrator.dbManager"%>
<% 
    dbManager  dbs = (dbManager)super.getServletContext().getAttribute("dbmanager");
    ArrayList<prezzo> pz = dbs.getPrezzi();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="theme-color" content="#cc0000">
        <title>Info</title>
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
            <div>
                <p class="col-md-12 text-center">Prezzi per gli spettacoli:</p>
                <div id="listino" class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 well">
                    <table class="table table-striped table-hover ">
                        <thead>
                            <tr class="info">
                                <th>Tipo</th>
                                <th>2D</th>
                                <th>3D</th>
                            </tr>
                        </thead>
                        <tbody>
                          <%Iterator pi = pz.iterator();
                            while(pi.hasNext()){
                                prezzo p = (prezzo) pi.next();
                          %>
                            <tr>
                                <td><%=p.getTipo_biglietto()%></td>
                                <td>&euro; <%=p.getPrezzo()%>.00</td>
                          <% p = (prezzo) pi.next(); %>
                                <td>&euro; <%=p.getPrezzo()%>.00</td>
                            </tr>
                          <%}%>
                        </tbody>
                    </table>
                </div>
                <p class="col-md-12 text-center">Come trovarci:</p>
                <div id="google-maps" class="well col-md-12" >
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2768.2128590580264!2d11.123116500000016!3d46.066796700000005!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4782714a693250df%3A0x16faa4cc65ceea27!2sUniversit%C3%A0+degli+Studi+di+Trento!5e0!3m2!1sit!2sit!4v1438591231769" width="800" height="600" frameborder="0" style="border:0" allowfullscreen></iframe>
                </div>
                <p class="col-md-12 text-center">Contatti:</p>
                <div id="contatti" class="col-lg-6 col-lg-offset-3 col-md-8 col-md-offset-2 well" >
                    <table class="table table-striped table-hover ">
                            <tr>
                                <td>Telefono:</td>
                                <td>0123 456789</td>
                            </tr>
                            <tr>
                                <td>e-mail:</td>
                                <td>multiplexx@cinema.com</td>
                            </tr>
                            <tr>
                                <td>Indirizzo:</td>
                                <td>via fasulla, 123 - Trento</td>
                            </tr>
                    </table>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
