<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html" %>

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

<%@page import="dbAdministrator.dbManager"%>
<% 
    dbManager  db = (dbManager)super.getServletContext().getAttribute("dbmanager");
    db.resetFilm();
    response.sendRedirect("../profile.jsp");
%>