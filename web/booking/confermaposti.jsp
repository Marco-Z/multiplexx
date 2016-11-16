<%-- 
    Document   : confermaposti
    Created on : 8-ago-2015, 16.03.29
    Author     : Admin
--%>

<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
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
    
    ArrayList<String> parameterNames = new ArrayList();
 Enumeration enumeration = request.getParameterNames();
    while (enumeration.hasMoreElements()) {
        String parameterName = (String) enumeration.nextElement();
        parameterNames.add(parameterName);
    }
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
                
        <link href="../css/styles.css" rel="stylesheet">
        <script type="text/javascript" src="../script/script.js"></script>
    </head>
    
    <body>
        
        <%@include file="../header-in.html" %>
        <div class="top">
            <%@include file="../errori.jsp" %>
        </div>
        <div class="container nav-header jumbotron text-center">
            <h3>Hai richiesto i posti:</h3>
            Scelga adesso quali posti sono interi oppure ridotti.
            <form action="completaposti.jsp" id="posti">
                <input type="submit" value="Prosegui" class="btn btn-raised red-button checkout-button text-center" >
            </form>
             <table class='center'>
                
                
            <% 
            
            
            for (int i =0; i<parameterNames.size();i++){
                if(!parameterNames.get(i).equals("spetid")){
                   out.print("<tr><td>");
                   out.print("<td> <input type='checkbox' class='place' id='" + parameterNames.get(i) +"'"+"name='"+ parameterNames.get(i) +"' >");
                   out.print("<label> " +"Posto  "+ parameterNames.get(i) + "</label>");
                   out.print("<select name='" + parameterNames.get(i)+ "' form='posti' class=\"formcontrol form-control-material-indigo\">" +
                             "<option value='intero'> Intero </option>"+
                             "<option value='ridotto'> Ridotto </option>"+
                           "</select>");
                   out.print("</tr></td>");
                }
            }
            out.print(" <input type='hidden'  form='posti' name='spetid' value='" + request.getParameter("spetid").toString() + "'>");
            %>
               
                </table>
             

            
                          
                                   
        </div>
            
        <%@include file="../footer.jsp" %>        
    </body>
</html>
